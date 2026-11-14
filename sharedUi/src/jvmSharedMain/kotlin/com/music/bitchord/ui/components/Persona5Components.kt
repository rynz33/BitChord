package com.music.bitchord.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.music.bitchord.data.model.Song
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import com.music.bitchord.sharedui.resources.*

/**
 * Persona 5 Ransom-Note Cutout typography tile row:
 * Alternates between stark white on Crimson Red (#E60012) and stark black on White,
 * with slight dynamic tilts for an authentic Phantom Thieves calling-card aesthetic.
 */
@Composable
fun PersonaCutoutText(
    text: String,
    modifier: Modifier = Modifier,
    tileSize: Dp = 24.dp,
    fontSize: TextUnit = 13.sp,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        text.split(" ").forEachIndexed { wordIdx, word ->
            if (wordIdx > 0) Spacer(Modifier.width(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                word.forEachIndexed { charIdx, char ->
                    val isRed = (charIdx + wordIdx) % 2 == 1
                    val tilt = when ((charIdx + wordIdx) % 4) {
                        0 -> -2.5f
                        1 -> 1.8f
                        2 -> -1.5f
                        else -> 2.2f
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .graphicsLayer { rotationZ = tilt }
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (isRed) Color(0xFFE60012) else Color.White)
                            .border(1.2.dp, Color.Black, RoundedCornerShape(3.dp))
                            .size(tileSize),
                    ) {
                        Text(
                            text = char.toString(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = fontSize,
                            ),
                            color = if (isRed) Color.White else Color.Black,
                        )
                    }
                }
            }
        }
    }
}

/**
 * Persona 5 authentic Vinyl Record / Calling Card Center Sleeve:
 * - Slanted bordered card (-1.5f rotation) with drop shadow
 * - "128 BPM" Crimson Red pill badge & white star
 * - Concentric Crimson Red & Phantom Black vinyl record with grooves
 * - Center circular album art spindle label (shows real song cover art)
 * - "PHANTOM THIEVES OST" & "STEREO // HI-RES" badges
 * - Tilted white "P5-OST SHOWTIME!! ★" sticker badge
 */
@Composable
fun PersonaVinylDiscCard(
    song: Song,
    artModel: Any?,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "vinylSpin")
    val spinAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "spinAngle",
    )

    val cardShape = RoundedCornerShape(10.dp)
    Box(
        modifier = modifier
            .graphicsLayer { rotationZ = -1.5f }
            .shadow(10.dp, cardShape, ambientColor = Color(0xFFE60012), spotColor = Color(0xFFE60012), clip = false)
            .clip(cardShape)
            .background(Color(0xFF141418))
            .border(2.5.dp, Color.White, cardShape)
            .padding(10.dp),
    ) {
        // 1. Top Badges Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFE60012))
                    .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp),
            ) {
                Text(
                    text = "128 BPM",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 11.sp,
                    letterSpacing = 0.5.sp,
                )
            }
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp),
            )
        }

        // 2. Center Concentric Vinyl Disc with Grooves & Album Cover Spindle
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            // Vinyl disc canvas
            Canvas(
                modifier = Modifier
                    .fillMaxSize(0.85f)
                    .graphicsLayer {
                        if (isPlaying) rotationZ = spinAngle
                    },
            ) {
                val discRadius = size.minDimension / 2f
                val center = Offset(size.width / 2f, size.height / 2f)

                // Outer crimson vinyl rim
                drawCircle(
                    color = Color(0xFFE60012),
                    radius = discRadius,
                    center = center,
                )
                // Black vinyl groove zone
                drawCircle(
                    color = Color(0xFF0D0D10),
                    radius = discRadius * 0.94f,
                    center = center,
                )
                // Subtle concentric grooves
                val grooveColor = Color.White.copy(alpha = 0.08f)
                val crimsonGroove = Color(0xFFE60012).copy(alpha = 0.15f)
                drawCircle(color = crimsonGroove, radius = discRadius * 0.86f, center = center, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5f))
                drawCircle(color = grooveColor, radius = discRadius * 0.78f, center = center, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.2f))
                drawCircle(color = crimsonGroove, radius = discRadius * 0.70f, center = center, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5f))
                drawCircle(color = grooveColor, radius = discRadius * 0.62f, center = center, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.2f))
                drawCircle(color = crimsonGroove, radius = discRadius * 0.54f, center = center, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5f))

                // Inner label rim
                drawCircle(
                    color = Color.White,
                    radius = discRadius * 0.40f,
                    center = center,
                )
            }

            // Real album artwork inside center spindle label
            Box(
                modifier = Modifier
                    .fillMaxSize(0.36f)
                    .clip(CircleShape)
                    .background(Color(0xFFE60012))
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                if (artModel != null) {
                    AsyncImage(
                        model = artModel,
                        contentDescription = song.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                // Center spindle hole
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF09090B))
                        .border(1.5.dp, Color.White, CircleShape),
                )
            }
        }

        // 3. Bottom OST & Showtime Badges
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color.White)
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                ) {
                    Text(
                        text = "PHANTOM THIEVES OST",
                        color = Color.Black,
                        fontWeight = FontWeight.Black,
                        fontSize = 9.sp,
                        letterSpacing = 0.5.sp,
                    )
                }
                Spacer(Modifier.height(3.dp))
                Text(
                    text = "STEREO // HI-RES",
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp,
                    letterSpacing = 0.6.sp,
                )
            }

            // Slanted white sticker: "P5-OST SHOWTIME!! ★"
            Box(
                modifier = Modifier
                    .graphicsLayer { rotationZ = 3.5f }
                    .shadow(3.dp, RoundedCornerShape(4.dp), clip = false)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .border(1.2.dp, Color.Black, RoundedCornerShape(4.dp))
                    .padding(horizontal = 7.dp, vertical = 3.dp),
            ) {
                Text(
                    text = "P5-OST SHOWTIME!! ★",
                    color = Color.Black,
                    fontWeight = FontWeight.Black,
                    fontSize = 9.5.sp,
                    letterSpacing = 0.5.sp,
                )
            }
        }
    }
}

/**
 * Doodle Cat (Layer 08 - Now Playing Studio) Center Sleeve Card:
 * - 2dp solid black border, 16dp rounded corners, crisp white background
 * - Top mini studio header with DoodleSun & DoodleCloud
 * - Framed song album artwork with 2dp border
 * - Cute DoodleCatMascot wearing studio headphones badge
 * - "LAYER 08 // AESTHETIC STUDIO" footer pill
 */
@Composable
fun DoodleNowPlayingStudioCard(
    song: Song,
    artModel: Any?,
    isDark: Boolean,
    modifier: Modifier = Modifier,
) {
    val cardShape = RoundedCornerShape(16.dp)
    val cardBg = if (isDark) Color(0xFF1C1C20) else Color.White
    val outline = if (isDark) Color.White else Color.Black

    Box(
        modifier = modifier
            .shadow(4.dp, cardShape, clip = false)
            .clip(cardShape)
            .background(cardBg)
            .border(2.5.dp, outline, cardShape)
            .padding(12.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // 1. Studio Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DoodleSun(modifier = Modifier.size(20.dp), color = outline)
                Text(
                    text = "Now Playing Studio",
                    color = outline,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    letterSpacing = 0.5.sp,
                )
                DoodleCloud(modifier = Modifier.size(20.dp), color = outline)
            }

            // 2. Framed Album Artwork or Doodle Cat Headphone Mascot
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                if (artModel != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(0.88f)
                            .clip(RoundedCornerShape(12.dp))
                            .border(2.dp, outline, RoundedCornerShape(12.dp)),
                    ) {
                        AsyncImage(
                            model = artModel,
                            contentDescription = song.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                        )
                        // Cute headphone cat sticker badge in bottom right corner
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(6.dp)
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(cardBg)
                                .border(1.5.dp, outline, CircleShape)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            DoodleCatMascot(
                                modifier = Modifier.fillMaxSize(),
                                wearingHeadphones = true,
                                catColor = outline,
                            )
                        }
                    }
                } else {
                    // Full Doodle Cat wearing studio headphones mascot
                    DoodleCatMascot(
                        modifier = Modifier.fillMaxSize(0.85f),
                        wearingHeadphones = true,
                        catColor = outline,
                    )
                }
            }

            // 3. Studio Footer Pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isDark) Color(0xFF27272A) else Color(0xFFF4F4F5))
                    .border(1.5.dp, outline, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
            ) {
                Text(
                    text = "LAYER 08 // AESTHETIC STUDIO",
                    color = outline,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    letterSpacing = 0.8.sp,
                )
            }
        }
    }
}

/**
 * Dedicated Now Playing Full-Screen Backdrop:
 * - Persona 5: Deep Phantom Black (#09090B), crimson diagonal comic polygon slash, white separator line, and halftone dot matrix.
 * - Doodle Cat: Crisp paper white / dark chalkboard, 2dp black border, and subtle graph-paper grid pattern.
 */
@Composable
fun NowPlayingThemeBackdrop(
    isDoodle: Boolean,
    isDark: Boolean,
    modifier: Modifier = Modifier,
) {
    if (isDoodle) {
        val bg = if (isDark) Color(0xFF121214) else Color(0xFFFAFAFA)
        val outline = if (isDark) Color.White else Color.Black
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(bg)
                .border(2.dp, outline),
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val step = 32.dp.toPx()
                val gridColor = if (isDark) Color.White.copy(alpha = 0.04f) else Color.Black.copy(alpha = 0.04f)
                var x = 0f
                while (x < size.width) {
                    drawLine(gridColor, Offset(x, 0f), Offset(x, size.height), strokeWidth = 1f)
                    x += step
                }
                var y = 0f
                while (y < size.height) {
                    drawLine(gridColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
                    y += step
                }
            }
        }
    } else {
        // Persona 5 Theme Backdrop
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF09090B)),
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                // Top Crimson Red angular polygon (Take Your Heart comic style)
                val topPolygon = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(w, 0f)
                    lineTo(w, h * 0.32f)
                    lineTo(0f, h * 0.22f)
                    close()
                }
                drawPath(topPolygon, color = Color(0xFFE60012))

                // White separator comic slash line
                drawLine(
                    color = Color.White,
                    start = Offset(0f, h * 0.22f),
                    end = Offset(w, h * 0.32f),
                    strokeWidth = 3.5.dp.toPx(),
                )

                // Bottom subtle crimson red angle
                val bottomPolygon = Path().apply {
                    moveTo(w * 0.35f, h)
                    lineTo(w, h * 0.85f)
                    lineTo(w, h)
                    close()
                }
                drawPath(bottomPolygon, color = Color(0xFFE60012).copy(alpha = 0.35f))

                // Halftone dot pattern in top polygon
                val dotSpacing = 16.dp.toPx()
                val dotRadius = 1.8.dp.toPx()
                var dy = 0f
                while (dy < h * 0.30f) {
                    var dx = 0f
                    while (dx < w) {
                        drawCircle(
                            color = Color.Black.copy(alpha = 0.16f),
                            radius = dotRadius,
                            center = Offset(dx, dy),
                        )
                        dx += dotSpacing
                    }
                    dy += dotSpacing
                }
            }
        }
    }
}

/**
 * Persona 5 Boxed Timestamp:
 * Black box with crimson red border and bold white digital text (e.g. "[ 1:16 ]", "[ 3:58 ]")
 */
@Composable
fun PersonaBoxedTimestamp(
    timeText: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(3.dp))
            .background(Color.Black)
            .border(1.dp, Color(0xFFE60012), RoundedCornerShape(3.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = timeText,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp,
            ),
            color = Color.White,
        )
    }
}

/**
 * Persona 5 Big Crimson Red Transport Play/Pause Button:
 * 16dp rounded corner square button with 2.5dp white border and white icon.
 */
@Composable
fun PersonaTransportPlayButton(
    isPlaying: Boolean,
    isLoading: Boolean,
    onPlayPause: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 68.dp,
) {
    val playPillShape = RoundedCornerShape(16.dp)
    Box(
        modifier = modifier
            .size(size)
            .shadow(6.dp, playPillShape, clip = false)
            .clip(playPillShape)
            .background(Color(0xFFE60012))
            .border(2.5.dp, Color.White, playPillShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onPlayPause,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 3.dp,
                modifier = Modifier.size(size * 0.45f),
            )
        } else {
            Icon(
                painter = painterResource(if (isPlaying) Res.drawable.ic_player_pause else Res.drawable.ic_player_play),
                contentDescription = stringResource(if (isPlaying) Res.string.pause else Res.string.play),
                tint = Color.White,
                modifier = Modifier.size(size * 0.55f),
            )
        }
    }
}
