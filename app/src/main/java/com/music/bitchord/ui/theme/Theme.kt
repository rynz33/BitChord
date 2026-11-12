package com.music.bitchord.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.music.bitchord.R
import com.music.bitchord.data.settings.AppFont
import com.music.bitchord.data.settings.AccentTheme

val LocalAccentColor = staticCompositionLocalOf { Color(0xFF8B5CF6) }

// Apple Music's signature red. Kept for Replay rank badges.
val AccentRed = Color(0xFFFA2D48)

/* =========================================================================
 * RynzMusic Multi-Theme Color Palettes
 * ========================================================================= */

// 1. Rynz Violet Glow (Flagship theme)
private val VioletDarkColors = darkColorScheme(
    primary = Color(0xFFA78BFA),
    onPrimary = Color(0xFF0D0B14),
    secondary = Color(0xFF818CF8),
    onSecondary = Color.White,
    tertiary = Color(0xFFC084FC),
    background = Color(0xFF090710),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF120E1F),
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF1D1730),
    onSurfaceVariant = Color(0xFFA59EC4),
    outline = Color(0xFF332952),
)

private val VioletLightColors = lightColorScheme(
    primary = Color(0xFF7C3AED),
    onPrimary = Color.White,
    secondary = Color(0xFF4F46E5),
    onSecondary = Color.White,
    tertiary = Color(0xFF9333EA),
    background = Color(0xFFF8F6FD),
    onBackground = Color(0xFF0F0B1E),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF130F24),
    surfaceVariant = Color(0xFFEFEAF8),
    onSurfaceVariant = Color(0xFF6B6280),
    outline = Color(0xFFDED8EB),
)

// 2. Cyber Cyan
private val CyberCyanDarkColors = darkColorScheme(
    primary = Color(0xFF22D3EE),
    onPrimary = Color(0xFF041014),
    secondary = Color(0xFF38BDF8),
    onSecondary = Color.White,
    tertiary = Color(0xFF06B6D4),
    background = Color(0xFF040A0F),
    onBackground = Color(0xFFF0FDF4),
    surface = Color(0xFF081621),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF102636),
    onSurfaceVariant = Color(0xFF789DAB),
    outline = Color(0xFF1B4059),
)

private val CyberCyanLightColors = lightColorScheme(
    primary = Color(0xFF0891B2),
    onPrimary = Color.White,
    secondary = Color(0xFF0284C7),
    onSecondary = Color.White,
    tertiary = Color(0xFF0E7490),
    background = Color(0xFFF4FAFC),
    onBackground = Color(0xFF05171F),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF081621),
    surfaceVariant = Color(0xFFE5F3F7),
    onSurfaceVariant = Color(0xFF4E6B78),
    outline = Color(0xFFCFE4EC),
)

// 3. Emerald Aurora
private val EmeraldAuroraDarkColors = darkColorScheme(
    primary = Color(0xFF34D399),
    onPrimary = Color(0xFF03140C),
    secondary = Color(0xFF10B981),
    onSecondary = Color.White,
    tertiary = Color(0xFF6EE7B7),
    background = Color(0xFF040E0A),
    onBackground = Color.White,
    surface = Color(0xFF091C14),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF112D21),
    onSurfaceVariant = Color(0xFF7CA692),
    outline = Color(0xFF1C4C38),
)

private val EmeraldAuroraLightColors = lightColorScheme(
    primary = Color(0xFF059669),
    onPrimary = Color.White,
    secondary = Color(0xFF10B981),
    onSecondary = Color.White,
    tertiary = Color(0xFF047857),
    background = Color(0xFFF3FBF7),
    onBackground = Color(0xFF051C12),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF091C14),
    surfaceVariant = Color(0xFFE2F4EC),
    onSurfaceVariant = Color(0xFF4A7260),
    outline = Color(0xFFCBE7DB),
)

// 4. Sunset Crimson
private val SunsetCrimsonDarkColors = darkColorScheme(
    primary = Color(0xFFFB7185),
    onPrimary = Color(0xFF170308),
    secondary = Color(0xFFF43F5E),
    onSecondary = Color.White,
    tertiary = Color(0xFFFDA4AF),
    background = Color(0xFF100508),
    onBackground = Color.White,
    surface = Color(0xFF1F0B11),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF30131B),
    onSurfaceVariant = Color(0xFFB58A94),
    outline = Color(0xFF52202E),
)

private val SunsetCrimsonLightColors = lightColorScheme(
    primary = Color(0xFFE11D48),
    onPrimary = Color.White,
    secondary = Color(0xFFBE123C),
    onSecondary = Color.White,
    tertiary = Color(0xFFF43F5E),
    background = Color(0xFFFFF5F7),
    onBackground = Color(0xFF20050B),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1F0B11),
    surfaceVariant = Color(0xFFFDE4E9),
    onSurfaceVariant = Color(0xFF82505C),
    outline = Color(0xFFF8CAD3),
)

// 5. Electric Amber
private val ElectricAmberDarkColors = darkColorScheme(
    primary = Color(0xFFFBBF24),
    onPrimary = Color(0xFF171003),
    secondary = Color(0xFFF59E0B),
    onSecondary = Color.White,
    tertiary = Color(0xFFFCD34D),
    background = Color(0xFF0E0B05),
    onBackground = Color.White,
    surface = Color(0xFF1C160B),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2C2312),
    onSurfaceVariant = Color(0xFFB2A387),
    outline = Color(0xFF4A3C20),
)

private val ElectricAmberLightColors = lightColorScheme(
    primary = Color(0xFFD97706),
    onPrimary = Color.White,
    secondary = Color(0xFFB45309),
    onSecondary = Color.White,
    tertiary = Color(0xFFF59E0B),
    background = Color(0xFFFFFBF2),
    onBackground = Color(0xFF1D1404),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1C160B),
    surfaceVariant = Color(0xFFFBEFD7),
    onSurfaceVariant = Color(0xFF786A4F),
    outline = Color(0xFFF0DCB5),
)

// 6. Midnight OLED
private val MidnightOledDarkColors = darkColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    secondary = Color(0xFFCBD5E1),
    onSecondary = Color.Black,
    tertiary = Color(0xFF94A3B8),
    background = Color.Black,
    onBackground = Color.White,
    surface = Color(0xFF0D0D0F),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF1C1C1E),
    onSurfaceVariant = Color(0xFF8E8E93),
    outline = Color(0xFF2C2C2E),
)

private val MidnightOledLightColors = lightColorScheme(
    primary = Color.Black,
    onPrimary = Color.White,
    secondary = Color(0xFF334155),
    onSecondary = Color.White,
    tertiary = Color(0xFF64748B),
    background = Color.White,
    onBackground = Color.Black,
    surface = Color(0xFFF7F7F9),
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFF2F2F7),
    onSurfaceVariant = Color(0xFF6E6E73),
    outline = Color(0xFFE5E5EA),
)

fun paletteFor(darkTheme: Boolean, accent: AccentTheme): ColorScheme = when (accent) {
    AccentTheme.VIOLET_GLOW -> if (darkTheme) VioletDarkColors else VioletLightColors
    AccentTheme.CYBER_CYAN -> if (darkTheme) CyberCyanDarkColors else CyberCyanLightColors
    AccentTheme.EMERALD_AURORA -> if (darkTheme) EmeraldAuroraDarkColors else EmeraldAuroraLightColors
    AccentTheme.SUNSET_CRIMSON -> if (darkTheme) SunsetCrimsonDarkColors else SunsetCrimsonLightColors
    AccentTheme.ELECTRIC_AMBER -> if (darkTheme) ElectricAmberDarkColors else ElectricAmberLightColors
    AccentTheme.MIDNIGHT_OLED -> if (darkTheme) MidnightOledDarkColors else MidnightOledLightColors
}

/**
 * SF Pro Display, the face Apple Music itself is set in, drawn one weight
 * lighter than it is asked for.
 *
 * Measured against Apple Music's own screens, every weight here came out a
 * cut heavier than the same text there: its titles are Semibold where these
 * asked for Bold, its metadata Medium where these asked for Semibold. The
 * shift is made once, in the family, so it reaches the type scale and every
 * weight set in place alike and keeps the steps between them: Heavy draws
 * Bold, Bold draws Semibold, Semibold draws Medium, Medium draws Regular.
 * Regular stays Regular — no lighter cut is bundled, and Apple's body text is
 * Regular too. A FontWeight in this app therefore names the step it sits at in
 * the scale, not the cut it draws.
 *
 * Compose synthesises nothing, so a weight outside these falls back to the
 * nearest one listed.
 */
val SFProDisplay = FontFamily(
    Font(R.font.sf_pro_display_regular, FontWeight.W400),
    Font(R.font.sf_pro_display_regular, FontWeight.W500),
    Font(R.font.sf_pro_display_medium, FontWeight.W600),
    Font(R.font.sf_pro_display_semibold, FontWeight.W700),
    Font(R.font.sf_pro_display_bold, FontWeight.W800),
)

/*
 * Letter spacing measured off Apple Music's own screens: the spacing the
 * bundled Display cut needs, at the weight it draws, to set a line to the
 * width Apple sets it. Apple sets text under 20pt in SF Pro Text, which is
 * spaced looser than Display, so the small sizes open up — about +0.25 to
 * +0.4 at 12 to 14 — and the spacing closes as the size grows, to just under
 * nothing for semibold titles at 18 to 24. The large titles had nothing to
 * measure against and follow the same trend.
 */
private val BitChordTypography = Typography(
    displayLarge = TextStyle(fontWeight = FontWeight.W800, fontSize = 34.sp, letterSpacing = (-0.5).sp),
    headlineLarge = TextStyle(fontWeight = FontWeight.W800, fontSize = 30.sp, letterSpacing = (-0.4).sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.W700, fontSize = 22.sp, letterSpacing = (-0.25).sp),
    titleLarge = TextStyle(fontWeight = FontWeight.W700, fontSize = 20.sp, letterSpacing = (-0.2).sp),
    titleMedium = TextStyle(fontWeight = FontWeight.W600, fontSize = 16.sp, letterSpacing = 0.05.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.W400, fontSize = 16.sp, letterSpacing = 0.2.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.W400, fontSize = 14.sp, letterSpacing = 0.3.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.W600, fontSize = 12.sp, letterSpacing = 0.25.sp),
    labelSmall = TextStyle(fontWeight = FontWeight.W600, fontSize = 11.sp, letterSpacing = 0.3.sp),
).withFamily(SFProDisplay)

/**
 * SF Pro Display as the app first shipped it, before [SFProDisplay] moved every
 * weight a cut lighter: each weight draws its own cut, Heavy included. Kept for
 * [AppFont.LEGACY].
 */
val SFProDisplayLegacy = FontFamily(
    Font(R.font.sf_pro_display_regular, FontWeight.W400),
    Font(R.font.sf_pro_display_medium, FontWeight.W500),
    Font(R.font.sf_pro_display_semibold, FontWeight.W600),
    Font(R.font.sf_pro_display_bold, FontWeight.W700),
    Font(R.font.sf_pro_display_heavy, FontWeight.W800),
)

// Heavy, tight typography — the original scale [AppFont.LEGACY] restores.
private val LegacyTypography = Typography(
    displayLarge = TextStyle(fontWeight = FontWeight.W800, fontSize = 34.sp, letterSpacing = (-0.8).sp),
    headlineLarge = TextStyle(fontWeight = FontWeight.W800, fontSize = 30.sp, letterSpacing = (-0.7).sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.W700, fontSize = 22.sp, letterSpacing = (-0.4).sp),
    titleLarge = TextStyle(fontWeight = FontWeight.W700, fontSize = 20.sp, letterSpacing = (-0.3).sp),
    titleMedium = TextStyle(fontWeight = FontWeight.W600, fontSize = 16.sp, letterSpacing = (-0.2).sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.W400, fontSize = 16.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.W400, fontSize = 14.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.W600, fontSize = 12.sp),
    labelSmall = TextStyle(fontWeight = FontWeight.W600, fontSize = 11.sp),
).withFamily(SFProDisplayLegacy)

/** Applies [family] to every style in the scale, so nothing is left on Roboto. */
private fun Typography.withFamily(family: FontFamily) = Typography(
    displayLarge = displayLarge.copy(fontFamily = family),
    displayMedium = displayMedium.copy(fontFamily = family),
    displaySmall = displaySmall.copy(fontFamily = family),
    headlineLarge = headlineLarge.copy(fontFamily = family),
    headlineMedium = headlineMedium.copy(fontFamily = family),
    headlineSmall = headlineSmall.copy(fontFamily = family),
    titleLarge = titleLarge.copy(fontFamily = family),
    titleMedium = titleMedium.copy(fontFamily = family),
    titleSmall = titleSmall.copy(fontFamily = family),
    bodyLarge = bodyLarge.copy(fontFamily = family),
    bodyMedium = bodyMedium.copy(fontFamily = family),
    bodySmall = bodySmall.copy(fontFamily = family),
    labelLarge = labelLarge.copy(fontFamily = family),
    labelMedium = labelMedium.copy(fontFamily = family),
    labelSmall = labelSmall.copy(fontFamily = family),
)

@Composable
fun BitChordTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accentTheme: AccentTheme = AccentTheme.VIOLET_GLOW,
    font: AppFont = AppFont.DEFAULT,
    content: @Composable () -> Unit,
) {
    val colorScheme = remember(darkTheme, accentTheme) {
        paletteFor(darkTheme, accentTheme)
    }
    val accentColor = remember(accentTheme) {
        Color(accentTheme.hexAccent)
    }
    CompositionLocalProvider(LocalAccentColor provides accentColor) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = when (font) {
                AppFont.DEFAULT -> BitChordTypography
                AppFont.LEGACY -> LegacyTypography
            },
            content = content,
        )
    }
}

/**
 * Flagship theme entry point for RynzMusic with dynamic Multi-Theme support.
 */
@Composable
fun RynzTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accentTheme: AccentTheme = AccentTheme.VIOLET_GLOW,
    font: AppFont = AppFont.DEFAULT,
    content: @Composable () -> Unit,
) = BitChordTheme(
    darkTheme = darkTheme,
    accentTheme = accentTheme,
    font = font,
    content = content,
)

/**
 * Draws the status and navigation bar glyphs dark or light.
 *
 * `enableEdgeToEdge()` decides this from the *system* dark-mode setting, which
 * is the wrong input the moment the in-app theme disagrees with it: Light theme
 * on a phone in dark mode left white icons on a white bar, invisible. The bars
 * have to follow the content the app is actually painting. The player supplies
 * its own stable light-icon value and paints contrast behind it; every other
 * surface follows the theme. Hence a parameter rather than reading it here.
 */
@Composable
fun SystemBarIcons(dark: Boolean) {
    val view = LocalView.current
    if (view.isInEditMode) return
    val window = findWindow(view) ?: return
    SideEffect {
        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = dark
            isAppearanceLightNavigationBars = dark
        }
    }
}

