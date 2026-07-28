package com.antigravity.applocker.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = AccentTeal,
    tertiary = PrimaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = TextDark,
    onSecondary = TextDark,
    onTertiary = TextDark,
    onBackground = TextDark,
    onSurface = TextDark,
)

private val AmoledColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = AccentTeal,
    tertiary = PrimaryDark,
    background = AmoledBackground,
    surface = AmoledSurface,
    onPrimary = TextDark,
    onSecondary = TextDark,
    onTertiary = TextDark,
    onBackground = TextDark,
    onSurface = TextDark,
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = AccentTeal,
    tertiary = PrimaryDark,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = TextLight,
    onSecondary = TextLight,
    onTertiary = TextLight,
    onBackground = TextLight,
    onSurface = TextLight,
)

@Composable
fun AppLockerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    amoledMode: Boolean = false,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        amoledMode && darkTheme -> AmoledColorScheme
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
