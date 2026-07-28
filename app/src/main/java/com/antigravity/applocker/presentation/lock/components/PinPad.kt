package com.antigravity.applocker.presentation.lock.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PinPad(
    modifier: Modifier = Modifier,
    onNumberClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onBiometricClick: (() -> Unit)? = null,
    showBiometricIcon: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val rows = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9)
        )

        for (row in rows) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                for (number in row) {
                    PinButton(text = number.toString(), onClick = { onNumberClick(number) })
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            if (showBiometricIcon && onBiometricClick != null) {
                IconButton(
                    onClick = onBiometricClick,
                    modifier = Modifier.size(72.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Biometric Unlock",
                        modifier = Modifier.size(36.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(72.dp))
            }

            PinButton(text = "0", onClick = { onNumberClick(0) })

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(72.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Backspace,
                    contentDescription = "Delete",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun PinButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun PinDots(
    pinLength: Int,
    currentLength: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 32.dp)
    ) {
        for (i in 0 until pinLength) {
            val isFilled = i < currentLength
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(
                        if (isFilled) MaterialTheme.colorScheme.primary 
                        else MaterialTheme.colorScheme.surfaceVariant
                    )
            )
        }
    }
}
