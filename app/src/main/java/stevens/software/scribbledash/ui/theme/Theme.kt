package stevens.software.scribbledash.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Secondary,
    tertiaryContainer = TertiaryContainer,
    error = Error,
    background = Background,
    onBackground = OnBackground,
    onSurface = OnSurface,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceVariant = SurfaceVariant,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainerLowest = SurfaceContainerLowest
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Secondary,
    tertiaryContainer = TertiaryContainer,
    error = Error,
    background = Background,
    onBackground = OnBackground,
    onSurface = OnSurface,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceVariant = SurfaceVariant,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainerLowest = SurfaceContainerLowest
)

@Composable
fun ScribbleDashTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(localExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

val localExtendedColors = compositionLocalOf { extendedColors }

data class ExtendedColors(
    val success: Color,
    val onPrimaryOpacity: Color,
    val onBackgroundVariant: Color
)

val extendedColors = ExtendedColors(
    success = Success,
    onPrimaryOpacity = OnPrimaryOpacity,
    onBackgroundVariant = OnBackgroundVariant
)