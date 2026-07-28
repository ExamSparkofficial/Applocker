package com.antigravity.applocker.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.antigravity.applocker.data.security.SecurityPreferences
import com.antigravity.applocker.presentation.lock.components.PinDots
import com.antigravity.applocker.presentation.lock.components.PinPad
import com.antigravity.applocker.util.HashUtil
import kotlinx.coroutines.launch

@Composable
fun SetupPinScreen(
    onPinSet: () -> Unit,
    securityPreferences: SecurityPreferences,
    hashUtil: HashUtil
) {
    var step by remember { mutableStateOf(1) } // 1: Enter, 2: Confirm
    var firstPin by remember { mutableStateOf("") }
    var currentPin by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }
    val requiredLength = 4

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(currentPin) {
        if (currentPin.length == requiredLength) {
            if (step == 1) {
                firstPin = currentPin
                currentPin = ""
                step = 2
            } else if (step == 2) {
                if (currentPin == firstPin) {
                    coroutineScope.launch {
                        // Generate a salt and hash
                        val salt = System.currentTimeMillis().toString()
                        val hash = hashUtil.hashSHA256(currentPin, salt)
                        securityPreferences.saveSalt(salt)
                        securityPreferences.saveHashedPin(hash)
                        onPinSet()
                    }
                } else {
                    errorMsg = "PINs do not match. Try again."
                    currentPin = ""
                    firstPin = ""
                    step = 1
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = if (step == 1) "Set a new PIN" else "Confirm your PIN",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        if (errorMsg.isNotEmpty()) {
            Text(
                text = errorMsg,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        PinDots(pinLength = requiredLength, currentLength = currentPin.length)

        Spacer(modifier = Modifier.weight(1f))

        PinPad(
            onNumberClick = { num ->
                if (currentPin.length < requiredLength) {
                    errorMsg = ""
                    currentPin += num
                }
            },
            onDeleteClick = {
                if (currentPin.isNotEmpty()) {
                    currentPin = currentPin.dropLast(1)
                }
            },
            showBiometricIcon = false
        )
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
