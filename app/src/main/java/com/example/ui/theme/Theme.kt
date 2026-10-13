package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AgroGreenDarkPrimary,
    onPrimary = AgroGreenDarkOnPrimary,
    primaryContainer = AgroGreenDarkPrimaryContainer,
    onPrimaryContainer = Color(0xFFA5D6A7),
    secondary = AgroGreenDarkSecondary,
    onSecondary = Color(0xFF00390B),
    secondaryContainer = Color(0xFF1E4620),
    onSecondaryContainer = Color(0xFFC8E6C9),
    tertiary = AgroGoldDarkTertiary,
    background = AgroBackgroundDark,
    surface = AgroSurfaceDark,
    surfaceVariant = AgroSurfaceVariantDark,
    onBackground = Color(0xFFE8F5E9),
    onSurface = Color(0xFFE8F5E9),
    onSurfaceVariant = Color(0xFFC8E6C9)
)

private val LightColorScheme = lightColorScheme(
    primary = AgroGreenPrimary,
    onPrimary = AgroGreenOnPrimary,
    primaryContainer = AgroGreenPrimaryContainer,
    onPrimaryContainer = AgroGreenOnPrimaryContainer,
    secondary = AgroGreenSecondary,
    onSecondary = AgroGreenOnSecondary,
    secondaryContainer = AgroGreenSecondaryContainer,
    onSecondaryContainer = Color(0xFF0D3E10),
    tertiary = AgroGoldTertiary,
    background = AgroBackgroundLight,
    surface = AgroSurfaceLight,
    surfaceVariant = AgroSurfaceVariantLight,
    onBackground = Color(0xFF1B2E1D),
    onSurface = Color(0xFF1B2E1D),
    onSurfaceVariant = Color(0xFF2E4E30)
)

@Composable
fun KishanBandhuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Prefer our intentional Agro palette
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
