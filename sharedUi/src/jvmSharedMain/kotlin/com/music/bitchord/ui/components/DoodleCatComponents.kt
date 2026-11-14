package com.music.bitchord.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.music.bitchord.ui.AppUi

/**
 * Quick UI Switcher Bar allowing instant switching between:
 * - Persona 5 (Take Your Heart / Phantom Thieves Crimson Red & Black)
 * - Doodle Cat (Little Explorers Monochrome Black Cat Line-Art)
 */
@Composable
fun UiStyleSwitcher(
    activeTheme: String,
    modifier: Modifier = Modifier,
) {
    val isDoodle = activeTheme == "DOODLE_CAT"
    val isPersona = !isDoodle
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = if (isPersona) Color(0xFF141418) else (if (isDark) Color(0xFF1E1E24) else Color(0xFFF4F4F6)),
            border = BorderStroke(
                width = 2.dp,
                color = if (isPersona) Color(0xFFE60012) else (if (isDark) Color.White else Color.Black),
            ),
            shadowElevation = 4.dp,
        ) {
            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Option 1: Persona 5
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (isPersona) Color(0xFFE60012) else Color.Transparent)
                        .clickable { AppUi.host.selectTheme("PHANTOM_THIEVES") }
                        .padding(horizontal = 14.dp, vertical = 7.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "🎭 PERSONA 5",
                        fontWeight = FontWeight.Black,
                        fontSize = 11.sp,
                        letterSpacing = 0.5.sp,
                        color = if (isPersona) Color.White else (if (isDark) Color.LightGray else Color.DarkGray),
                    )
                }

                Spacer(Modifier.width(4.dp))

                // Option 2: Doodle Cat
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (isDoodle) (if (isDark) Color.White else Color.Black) else Color.Transparent)
                        .clickable { AppUi.host.selectTheme("DOODLE_CAT") }
                        .padding(horizontal = 14.dp, vertical = 7.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "🐱 DOODLE CAT",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 0.5.sp,
                        color = if (isDoodle) (if (isDark) Color.Black else Color.White) else (if (isDark) Color.LightGray else Color.DarkGray),
                    )
                }
            }
        }
    }
}

/**
 * Hand-drawn Vector Mascot of Professor Whiskers / Little Explorers Black Cat.
 */
@Composable
fun DoodleCatMascot(
    modifier: Modifier = Modifier,
    wearingHeadphones: Boolean = false,
    sleeping: Boolean = false,
    catColor: Color = Color.Black,
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val centerX = w * 0.5f
        val bodyBottom = h * 0.95f
        val headCenterY = h * 0.42f
        val headRadius = w * 0.32f

        // 1. Cat Body (Smooth rounded silhouette)
        val bodyPath = Path().apply {
            moveTo(centerX - headRadius * 0.9f, bodyBottom)
            cubicTo(
                centerX - headRadius * 1.1f, h * 0.65f,
                centerX - headRadius * 0.7f, headCenterY + headRadius * 0.5f,
                centerX - headRadius * 0.6f, headCenterY + headRadius * 0.2f
            )
            lineTo(centerX + headRadius * 0.6f, headCenterY + headRadius * 0.2f)
            cubicTo(
                centerX + headRadius * 0.7f, headCenterY + headRadius * 0.5f,
                centerX + headRadius * 1.1f, h * 0.65f,
                centerX + headRadius * 0.9f, bodyBottom
            )
            close()
        }
        drawPath(bodyPath, color = catColor)

        // 2. Ears (Pointed cute triangular cat ears)
        val leftEar = Path().apply {
            moveTo(centerX - headRadius * 0.85f, headCenterY - headRadius * 0.2f)
            lineTo(centerX - headRadius * 0.88f, headCenterY - headRadius * 1.25f)
            lineTo(centerX - headRadius * 0.22f, headCenterY - headRadius * 0.8f)
            close()
        }
        drawPath(leftEar, color = catColor)

        val rightEar = Path().apply {
            moveTo(centerX + headRadius * 0.22f, headCenterY - headRadius * 0.8f)
            lineTo(centerX + headRadius * 0.88f, headCenterY - headRadius * 1.25f)
            lineTo(centerX + headRadius * 0.85f, headCenterY - headRadius * 0.2f)
            close()
        }
        drawPath(rightEar, color = catColor)

        // 3. Head (Circular base)
        drawCircle(
            color = catColor,
            radius = headRadius,
            center = Offset(centerX, headCenterY),
        )

        // 4. White Collar & Necktie (Professor Whiskers Signature look)
        val collarY = headCenterY + headRadius * 0.75f
        val collarPath = Path().apply {
            moveTo(centerX - headRadius * 0.35f, collarY)
            lineTo(centerX, collarY + headRadius * 0.45f)
            lineTo(centerX + headRadius * 0.35f, collarY)
            close()
        }
        drawPath(collarPath, color = Color.White)

        // Cute Black Tie
        val tiePath = Path().apply {
            moveTo(centerX - headRadius * 0.12f, collarY + headRadius * 0.15f)
            lineTo(centerX + headRadius * 0.12f, collarY + headRadius * 0.15f)
            lineTo(centerX + headRadius * 0.18f, collarY + headRadius * 0.85f)
            lineTo(centerX, collarY + headRadius * 1.05f)
            lineTo(centerX - headRadius * 0.18f, collarY + headRadius * 0.85f)
            close()
        }
        drawPath(tiePath, color = catColor)

        // 5. Eyes
        val eyeOffsetY = headCenterY - headRadius * 0.05f
        val eyeDistance = headRadius * 0.42f
        val eyeRadius = headRadius * 0.18f

        if (sleeping) {
            // Sleeping closed eyes: ^ ^
            val stroke = Stroke(width = 2.5f.dp.toPx(), cap = StrokeCap.Round)
            val leftArc = Path().apply {
                moveTo(centerX - eyeDistance - eyeRadius, eyeOffsetY)
                quadraticTo(centerX - eyeDistance, eyeOffsetY - eyeRadius, centerX - eyeDistance + eyeRadius, eyeOffsetY)
            }
            drawPath(leftArc, color = Color.White, style = stroke)

            val rightArc = Path().apply {
                moveTo(centerX + eyeDistance - eyeRadius, eyeOffsetY)
                quadraticTo(centerX + eyeDistance, eyeOffsetY - eyeRadius, centerX + eyeDistance + eyeRadius, eyeOffsetY)
            }
            drawPath(rightArc, color = Color.White, style = stroke)
        } else {
            // Big cute white doodle eyes
            drawCircle(
                color = Color.White,
                radius = eyeRadius,
                center = Offset(centerX - eyeDistance, eyeOffsetY),
            )
            drawCircle(
                color = Color.White,
                radius = eyeRadius,
                center = Offset(centerX + eyeDistance, eyeOffsetY),
            )
            // Black pupils looking friendly
            drawCircle(
                color = catColor,
                radius = eyeRadius * 0.5f,
                center = Offset(centerX - eyeDistance, eyeOffsetY),
            )
            drawCircle(
                color = catColor,
                radius = eyeRadius * 0.5f,
                center = Offset(centerX + eyeDistance, eyeOffsetY),
            )
        }

        // 6. Whiskers (Crisp white lines extending outward)
        val whiskerY1 = headCenterY + headRadius * 0.2f
        val whiskerY2 = headCenterY + headRadius * 0.38f
        val whiskerStroke = Stroke(width = 1.8f.dp.toPx(), cap = StrokeCap.Round)

        // Left whiskers
        drawLine(
            color = Color.White,
            start = Offset(centerX - headRadius * 0.6f, whiskerY1),
            end = Offset(centerX - headRadius * 1.25f, whiskerY1 - headRadius * 0.15f),
            strokeWidth = 1.8f.dp.toPx(),
            cap = StrokeCap.Round,
        )
        drawLine(
            color = Color.White,
            start = Offset(centerX - headRadius * 0.6f, whiskerY2),
            end = Offset(centerX - headRadius * 1.25f, whiskerY2 + headRadius * 0.1f),
            strokeWidth = 1.8f.dp.toPx(),
            cap = StrokeCap.Round,
        )

        // Right whiskers
        drawLine(
            color = Color.White,
            start = Offset(centerX + headRadius * 0.6f, whiskerY1),
            end = Offset(centerX + headRadius * 1.25f, whiskerY1 - headRadius * 0.15f),
            strokeWidth = 1.8f.dp.toPx(),
            cap = StrokeCap.Round,
        )
        drawLine(
            color = Color.White,
            start = Offset(centerX + headRadius * 0.6f, whiskerY2),
            end = Offset(centerX + headRadius * 1.25f, whiskerY2 + headRadius * 0.1f),
            strokeWidth = 1.8f.dp.toPx(),
            cap = StrokeCap.Round,
        )

        // 7. Optional Headphones
        if (wearingHeadphones) {
            val hpStroke = Stroke(width = 3.5f.dp.toPx(), cap = StrokeCap.Round)
            // Headphone band
            val hpBand = Path().apply {
                moveTo(centerX - headRadius * 1.05f, headCenterY - headRadius * 0.1f)
                cubicTo(
                    centerX - headRadius * 1.15f, headCenterY - headRadius * 1.4f,
                    centerX + headRadius * 1.15f, headCenterY - headRadius * 1.4f,
                    centerX + headRadius * 1.05f, headCenterY - headRadius * 0.1f
                )
            }
            drawPath(hpBand, color = Color.White, style = hpStroke)

            // Ear cups
            drawCircle(
                color = Color.White,
                radius = headRadius * 0.32f,
                center = Offset(centerX - headRadius * 1.05f, headCenterY - headRadius * 0.1f),
            )
            drawCircle(
                color = catColor,
                radius = headRadius * 0.22f,
                center = Offset(centerX - headRadius * 1.05f, headCenterY - headRadius * 0.1f),
            )

            drawCircle(
                color = Color.White,
                radius = headRadius * 0.32f,
                center = Offset(centerX + headRadius * 1.05f, headCenterY - headRadius * 0.1f),
            )
            drawCircle(
                color = catColor,
                radius = headRadius * 0.22f,
                center = Offset(centerX + headRadius * 1.05f, headCenterY - headRadius * 0.1f),
            )
        }
    }
}

/**
 * Doodle Sun icon with rays.
 */
@Composable
fun DoodleSun(modifier: Modifier = Modifier, color: Color = Color.Black) {
    Canvas(modifier = modifier) {
        val r = size.minDimension * 0.24f
        val center = Offset(size.width * 0.5f, size.height * 0.5f)
        val stroke = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)

        // Sun center circle
        drawCircle(color = color, radius = r, center = center, style = stroke)

        // 8 radiating rays
        for (i in 0 until 8) {
            val angle = (i * 45) * (Math.PI / 180.0)
            val startR = r * 1.45f
            val endR = r * 1.95f
            val startX = (center.x + startR * Math.cos(angle)).toFloat()
            val startY = (center.y + startR * Math.sin(angle)).toFloat()
            val endX = (center.x + endR * Math.cos(angle)).toFloat()
            val endY = (center.y + endR * Math.sin(angle)).toFloat()
            drawLine(color = color, start = Offset(startX, startY), end = Offset(endX, endY), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
        }
    }
}

/**
 * Doodle Cloud icon.
 */
@Composable
fun DoodleCloud(modifier: Modifier = Modifier, color: Color = Color.Black) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)

        val path = Path().apply {
            moveTo(w * 0.2f, h * 0.75f)
            lineTo(w * 0.8f, h * 0.75f)
            cubicTo(w * 0.95f, h * 0.75f, w * 0.95f, h * 0.45f, w * 0.75f, h * 0.45f)
            cubicTo(w * 0.75f, h * 0.2f, w * 0.45f, h * 0.15f, w * 0.38f, h * 0.4f)
            cubicTo(w * 0.2f, h * 0.35f, w * 0.05f, h * 0.55f, w * 0.2f, h * 0.75f)
            close()
        }
        drawPath(path, color = color, style = stroke)
    }
}

/**
 * Layer 04 (Project Overview):
 * Two side-by-side 2dp-bordered cards ("01. Synchronized Lyrics" & "02. Studio Gear Notes: Warm Vinyl & 1/4 Inch Analog")
 * with DoodleCatMascot(wearingHeadphones = true).
 */
@Composable
fun DoodleLayer04ProjectOverview(
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val outlineColor = if (isDark) Color.White else Color.Black
    val cardBg = if (isDark) Color(0xFF1C1C20) else Color(0xFFFBFBFC)
    val subTextColor = if (isDark) Color.LightGray else Color.Gray

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = "Layer 04 // Project Overview",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = subTextColor,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = "Project Overview",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    DoodleCloud(Modifier.size(20.dp), color = outlineColor)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // Card 01: Synchronized Lyrics
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(cardBg)
                    .border(2.dp, outlineColor, RoundedCornerShape(16.dp))
                    .padding(12.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,
                    ) {
                        Text(
                            text = "01. Synchronized Lyrics",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.weight(1f),
                        )
                        Spacer(Modifier.width(4.dp))
                        DoodleCatMascot(
                            modifier = Modifier.size(36.dp),
                            wearingHeadphones = true,
                            catColor = outlineColor,
                        )
                    }
                    Text(
                        text = "Syllable-accurate acoustic verse tracking & cozy sing-along cards.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            // Card 02: Studio Gear Notes
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(cardBg)
                    .border(2.dp, outlineColor, RoundedCornerShape(16.dp))
                    .padding(12.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,
                    ) {
                        Text(
                            text = "02. Studio Gear Notes: Warm Vinyl & 1/4 Inch Analog",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.weight(1f),
                        )
                        Spacer(Modifier.width(4.dp))
                        DoodleCatMascot(
                            modifier = Modifier.size(36.dp),
                            wearingHeadphones = true,
                            catColor = outlineColor,
                        )
                    }
                    Text(
                        text = "Mastered through vintage reel-to-reel tape with gentle room warmth.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

/**
 * Layer 05 (Sound Theory / EQ):
 * "01 Warm Vinyl Crackle", "02 Purr Sub-Bass Boost" toggleable pills +
 * a "5-Band Acoustic EQ" card drawing 5 vertical slider stems with circular knobs
 * (60Hz, 250Hz, 1kHz, 4kHz, 12kHz) and a cat mascot.
 */
@Composable
fun DoodleLayer05SoundTheoryEq(
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val outlineColor = if (isDark) Color.White else Color.Black
    val invertedColor = if (isDark) Color.Black else Color.White
    val cardBg = if (isDark) Color(0xFF1C1C20) else Color(0xFFFBFBFC)
    val subTextColor = if (isDark) Color.LightGray else Color.Gray

    var vinylCrackleEnabled by remember { mutableStateOf(true) }
    var purrBassEnabled by remember { mutableStateOf(true) }
    val bandLevels = remember { mutableStateListOf(0.75f, 0.60f, 0.48f, 0.68f, 0.82f) }
    val bandLabels = listOf("60Hz", "250Hz", "1kHz", "4kHz", "12kHz")

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Layer 05 // Sound Theory & EQ",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = subTextColor,
        )
        Text(
            text = "Sound Theory & Acoustic EQ",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 6.dp),
        )

        // Toggleable pills
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (vinylCrackleEnabled) outlineColor else cardBg)
                    .border(1.5.dp, outlineColor, RoundedCornerShape(20.dp))
                    .clickable { vinylCrackleEnabled = !vinylCrackleEnabled }
                    .padding(horizontal = 10.dp, vertical = 7.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "01 Warm Vinyl Crackle",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = if (vinylCrackleEnabled) invertedColor else outlineColor,
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (purrBassEnabled) outlineColor else cardBg)
                    .border(1.5.dp, outlineColor, RoundedCornerShape(20.dp))
                    .clickable { purrBassEnabled = !purrBassEnabled }
                    .padding(horizontal = 10.dp, vertical = 7.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "02 Purr Sub-Bass Boost",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = if (purrBassEnabled) invertedColor else outlineColor,
                )
            }
        }

        // 5-Band Acoustic EQ Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(cardBg)
                .border(2.dp, outlineColor, RoundedCornerShape(16.dp))
                .padding(14.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(
                            text = "5-Band Acoustic EQ",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Text(
                            text = "Tap any frequency stem to cycle gain contour",
                            style = MaterialTheme.typography.labelSmall,
                            color = subTextColor,
                        )
                    }
                    DoodleCatMascot(
                        modifier = Modifier.size(38.dp),
                        wearingHeadphones = true,
                        catColor = outlineColor,
                    )
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    bandLabels.forEachIndexed { index, freqLabel ->
                        val level = bandLevels[index]
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    val next = if (level + 0.18f > 0.92f) 0.25f else level + 0.18f
                                    bandLevels[index] = next
                                }
                                .padding(horizontal = 6.dp, vertical = 4.dp),
                        ) {
                            Canvas(
                                modifier = Modifier
                                    .width(26.dp)
                                    .height(76.dp),
                            ) {
                                val trackX = size.width * 0.5f
                                val topY = 8.dp.toPx()
                                val bottomY = size.height - 8.dp.toPx()
                                val knobY = bottomY - (bottomY - topY) * level

                                // Background stem
                                drawLine(
                                    color = outlineColor.copy(alpha = 0.28f),
                                    start = Offset(trackX, topY),
                                    end = Offset(trackX, bottomY),
                                    strokeWidth = 2.dp.toPx(),
                                    cap = StrokeCap.Round,
                                )

                                // Active stem
                                drawLine(
                                    color = outlineColor,
                                    start = Offset(trackX, knobY),
                                    end = Offset(trackX, bottomY),
                                    strokeWidth = 3.2.dp.toPx(),
                                    cap = StrokeCap.Round,
                                )

                                // Circular knob outer & inner
                                drawCircle(
                                    color = cardBg,
                                    radius = 6.5.dp.toPx(),
                                    center = Offset(trackX, knobY),
                                )
                                drawCircle(
                                    color = outlineColor,
                                    radius = 6.5.dp.toPx(),
                                    center = Offset(trackX, knobY),
                                    style = Stroke(width = 2.dp.toPx()),
                                )
                                drawCircle(
                                    color = outlineColor,
                                    radius = 2.5.dp.toPx(),
                                    center = Offset(trackX, knobY),
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = freqLabel,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Layer 06 (Listening Analysis):
 * "Weekly Listening Activity (+24% vs last week)" line chart drawn with Canvas
 * (7 day points Mon-Sun with line and circular dots) +
 * solid black card "Top Mood: Midnight Study (68%) • Streak: 14 Days Active Listener".
 */
@Composable
fun DoodleLayer06ListeningAnalysis(
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val outlineColor = if (isDark) Color.White else Color.Black
    val cardBg = if (isDark) Color(0xFF1C1C20) else Color(0xFFFBFBFC)
    val subTextColor = if (isDark) Color.LightGray else Color.Gray
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val points = listOf(0.35f, 0.52f, 0.44f, 0.70f, 0.64f, 0.88f, 0.94f)

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Layer 06 // Listening Analysis",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = subTextColor,
        )
        Text(
            text = "Weekly Listening Activity (+24% vs last week)",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 6.dp),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(cardBg)
                .border(2.dp, outlineColor, RoundedCornerShape(16.dp))
                .padding(14.dp),
        ) {
            Column {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(92.dp),
                ) {
                    val leftPad = 14.dp.toPx()
                    val rightPad = size.width - 14.dp.toPx()
                    val topPad = 12.dp.toPx()
                    val bottomPad = size.height - 12.dp.toPx()
                    val stepX = (rightPad - leftPad) / (points.size - 1).coerceAtLeast(1)

                    // Subtle horizontal guide lines
                    for (g in 0..2) {
                        val gy = topPad + (bottomPad - topPad) * (g / 2f)
                        drawLine(
                            color = outlineColor.copy(alpha = 0.12f),
                            start = Offset(leftPad, gy),
                            end = Offset(rightPad, gy),
                            strokeWidth = 1.dp.toPx(),
                        )
                    }

                    val linePath = Path()
                    val offsets = points.mapIndexed { idx, value ->
                        val x = leftPad + idx * stepX
                        val y = bottomPad - (bottomPad - topPad) * value
                        if (idx == 0) linePath.moveTo(x, y) else linePath.lineTo(x, y)
                        Offset(x, y)
                    }

                    drawPath(
                        path = linePath,
                        color = outlineColor,
                        style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round),
                    )

                    offsets.forEach { pt ->
                        drawCircle(
                            color = cardBg,
                            radius = 5.dp.toPx(),
                            center = pt,
                        )
                        drawCircle(
                            color = outlineColor,
                            radius = 5.dp.toPx(),
                            center = pt,
                            style = Stroke(width = 2.dp.toPx()),
                        )
                        drawCircle(
                            color = outlineColor,
                            radius = 2.dp.toPx(),
                            center = pt,
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    days.forEach { day ->
                        Text(
                            text = day,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = subTextColor,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Solid black card for Top Mood & Streak
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color.Black)
                .border(1.5.dp, outlineColor, RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Top Mood: Midnight Study (68%) • Streak: 14 Days Active Listener",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
            )
        }
    }
}

/**
 * Layer 09 (Thank You / Mixing Receipt):
 * "LITTLE EXPLORERS MIXING RECEIPT" card with track items (#01 Midnight Purr Sonata,
 * #02 Tuxedo Paws on Piano, #03 Cardboard Box Reveries), "100% VIBES: 100% COZY MONOCHROME",
 * a Canvas-drawn vertical barcode, and an "Export Story Poster" solid black pill button.
 */
@Composable
fun DoodleLayer09MixingReceipt(
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val outlineColor = if (isDark) Color.White else Color.Black
    val cardBg = if (isDark) Color(0xFF1C1C20) else Color(0xFFFBFBFC)
    val subTextColor = if (isDark) Color.LightGray else Color.Gray
    var exported by remember { mutableStateOf(false) }

    val receiptTracks = listOf(
        "#01 Midnight Purr Sonata" to "03:24",
        "#02 Tuxedo Paws on Piano" to "02:48",
        "#03 Cardboard Box Reveries" to "04:12",
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Layer 09 // Thank You & Mixing Receipt",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = subTextColor,
            modifier = Modifier.padding(bottom = 6.dp),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(cardBg)
                .border(2.dp, outlineColor, RoundedCornerShape(18.dp))
                .padding(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "LITTLE EXPLORERS MIXING RECEIPT",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 0.8.sp),
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "STUDIO SESSION #409 • MONOCHROME PRESSING",
                    style = MaterialTheme.typography.labelSmall,
                    color = subTextColor,
                )

                Spacer(Modifier.height(10.dp))

                receiptTracks.forEach { (title, duration) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Text(
                            text = duration,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = subTextColor,
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "100% VIBES: 100% COZY MONOCHROME",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 0.6.sp),
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(Modifier.height(10.dp))

                // Canvas-drawn vertical barcode
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .padding(horizontal = 8.dp),
                ) {
                    val barCount = 42
                    val totalW = size.width
                    val step = totalW / barCount
                    for (i in 0 until barCount) {
                        val thicknessFactor = when {
                            i % 7 == 0 -> 0.68f
                            i % 3 == 0 -> 0.48f
                            i % 2 == 0 -> 0.28f
                            else -> 0.16f
                        }
                        val x = i * step + step * 0.5f
                        drawLine(
                            color = outlineColor,
                            start = Offset(x, 2.dp.toPx()),
                            end = Offset(x, size.height - 2.dp.toPx()),
                            strokeWidth = (step * thicknessFactor).coerceAtLeast(1.2.dp.toPx()),
                            cap = StrokeCap.Butt,
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Solid black pill button "Export Story Poster"
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.Black)
                        .border(1.5.dp, outlineColor, RoundedCornerShape(24.dp))
                        .clickable { exported = !exported }
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (exported) "Export Story Poster ✓" else "Export Story Poster",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                    )
                }
            }
        }
    }
}

/**
 * Layer 10 (Similar Elements):
 * A 3x3 grid of cute cat avatar sticker cards ("Tuxedo Boss", "Lo-fi Sleeper",
 * "Vinyl Crafter", "Beat Purr", "Shadow Paws", "Rhythm Tail", "Meow Mixer",
 * "Curious Kit", "Night Meow"), each with a mini DoodleCatMascot and selectable border.
 */
@Composable
fun DoodleLayer10SimilarElements(
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val outlineColor = if (isDark) Color.White else Color.Black
    val cardBg = if (isDark) Color(0xFF1C1C20) else Color(0xFFFBFBFC)
    val selectedBg = if (isDark) Color(0xFF2C2C34) else Color(0xFFEAEAEF)
    val subTextColor = if (isDark) Color.LightGray else Color.Gray

    val stickers = listOf(
        "Tuxedo Boss",
        "Lo-fi Sleeper",
        "Vinyl Crafter",
        "Beat Purr",
        "Shadow Paws",
        "Rhythm Tail",
        "Meow Mixer",
        "Curious Kit",
        "Night Meow",
    )
    var selectedSticker by remember { mutableIntStateOf(0) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Layer 10 // Similar Elements",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = subTextColor,
        )
        Text(
            text = "Cat Avatar Sticker Pack (3×3)",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 6.dp),
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (rowIdx in 0 until 3) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    for (colIdx in 0 until 3) {
                        val itemIdx = rowIdx * 3 + colIdx
                        val label = stickers[itemIdx]
                        val isSelected = selectedSticker == itemIdx
                        val hasHeadphones = itemIdx % 2 == 0
                        val isSleeping = label == "Lo-fi Sleeper" || label == "Night Meow"

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isSelected) selectedBg else cardBg)
                                .border(
                                    width = if (isSelected) 2.5.dp else 1.5.dp,
                                    color = if (isSelected) outlineColor else outlineColor.copy(alpha = 0.55f),
                                    shape = RoundedCornerShape(14.dp),
                                )
                                .clickable { selectedSticker = itemIdx }
                                .padding(vertical = 10.dp, horizontal = 6.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                DoodleCatMascot(
                                    modifier = Modifier.size(34.dp),
                                    wearingHeadphones = hasHeadphones,
                                    sleeping = isSleeping,
                                    catColor = outlineColor,
                                )
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                        fontSize = 10.sp,
                                    ),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    maxLines = 1,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
