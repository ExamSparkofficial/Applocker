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

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Locked",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Enter PIN",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "App Locked: $packageName",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        PinDots(
            pinLength = requiredPinLength,
            currentLength = enteredPin.length
        )

        Spacer(modifier = Modifier.weight(1f))

        PinPad(
            onNumberClick = { num ->
                if (enteredPin.length < requiredPinLength) {
                    enteredPin += num
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
