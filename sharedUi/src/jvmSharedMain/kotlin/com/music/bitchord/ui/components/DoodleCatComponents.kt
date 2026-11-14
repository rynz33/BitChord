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
