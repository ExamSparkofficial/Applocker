package com.antigravity.applocker.presentation.lock

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.Fingerprint
import com.antigravity.applocker.R
import com.antigravity.applocker.data.security.SecurityPreferences
import com.antigravity.applocker.lockengine.AppLockService
import com.antigravity.applocker.presentation.lock.components.PinDots
import com.antigravity.applocker.presentation.lock.components.PinPad
import com.antigravity.applocker.presentation.theme.AppLockerTheme
import com.antigravity.applocker.util.BiometricHelper
import com.antigravity.applocker.util.HashUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LockActivity : FragmentActivity() {

    @Inject
    lateinit var biometricHelper: BiometricHelper

    @Inject
    lateinit var securityPreferences: SecurityPreferences
    
    @Inject
    lateinit var hashUtil: HashUtil

    companion object {
        const val EXTRA_PACKAGE_NAME = "extra_package_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val packageName = intent.getStringExtra(EXTRA_PACKAGE_NAME) ?: return finish()

        // Attempt Biometric Prompt if available
        if (biometricHelper.isBiometricAvailable(this)) {
            biometricHelper.showBiometricPrompt(
                activity = this,
                onSuccess = { unlockSuccess(packageName) },
                onFailed = { /* Fallback to PIN */ }
            )
        }

        setContent {
            AppLockerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LockScreenUI(
                        packageName = packageName,
                        onUnlockSuccess = { unlockSuccess(packageName) },
                        onBiometricClick = {
                            biometricHelper.showBiometricPrompt(
                                activity = this@LockActivity,
                                onSuccess = { unlockSuccess(packageName) },
                                onFailed = { }
                            )
                        },
                        isBiometricAvailable = biometricHelper.isBiometricAvailable(this),
                        securityPreferences = securityPreferences,
                        hashUtil = hashUtil
                    )
                }
            }
        }
    }

    private fun unlockSuccess(packageName: String) {
        val serviceIntent = Intent(this, AppLockService::class.java).apply {
            putExtra("UNLOCKED_PACKAGE", packageName)
        }
        startService(serviceIntent)
        finish()
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}

@Composable
fun LockScreenUI(
    packageName: String,
    onUnlockSuccess: () -> Unit,
    onBiometricClick: () -> Unit,
    isBiometricAvailable: Boolean,
    securityPreferences: SecurityPreferences,
    hashUtil: HashUtil
) {
    var enteredPin by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }
    var showPinPad by remember { mutableStateOf(false) }
    val requiredPinLength = 4 
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(enteredPin) {
        if (enteredPin.length == requiredPinLength) {
            coroutineScope.launch {
                val salt = securityPreferences.getSalt() ?: ""
                val savedHash = securityPreferences.getHashedPin()
                
                val currentHash = hashUtil.hashSHA256(enteredPin, salt)
                if (currentHash == savedHash) {
                    onUnlockSuccess()
                } else {
                    errorMsg = "Incorrect PIN"
                    enteredPin = ""
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val wallpaperUri = securityPreferences.getWallpaperUri()
        if (wallpaperUri != null) {
            coil.compose.AsyncImage(
                model = wallpaperUri,
                contentDescription = "Wallpaper",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher),
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "App Locked",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Text(
                text = packageName,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f)
            )

            if (!showPinPad) {
                Spacer(modifier = Modifier.weight(1f))
                if (isBiometricAvailable) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Fingerprint",
                        modifier = Modifier
                            .size(80.dp)
                            .clickable { onBiometricClick() },
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Touch the fingerprint sensor",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(48.dp))
                OutlinedButton(
                    onClick = { showPinPad = true },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text("Use PIN")
                }
                Spacer(modifier = Modifier.height(32.dp))
            } else {
                Spacer(modifier = Modifier.height(32.dp))
                PinDots(
                    pinLength = requiredPinLength,
                    currentLength = enteredPin.length
                )
                if (errorMsg.isNotEmpty()) {
                    Text(
                        text = errorMsg,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                PinPad(
                    onNumberClick = { num ->
                        if (enteredPin.length < requiredPinLength) {
                            enteredPin += num
                            errorMsg = ""
                        }
                    },
                    onDeleteClick = {
                        if (enteredPin.isNotEmpty()) {
                            enteredPin = enteredPin.dropLast(1)
                        }
                    },
                    onBiometricClick = onBiometricClick,
                    showBiometricIcon = isBiometricAvailable
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
