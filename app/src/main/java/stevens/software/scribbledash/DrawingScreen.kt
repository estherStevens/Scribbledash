package stevens.software.scribbledash

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import stevens.software.scribbledash.DrawingScreenViewModel.DrawingState
import stevens.software.scribbledash.ui.theme.extendedColours
import kotlin.math.abs

@Composable
fun DrawingScreen(
    uiState: DrawingState,
    onPathStart: () -> Unit,
    onDraw: (Offset) -> Unit,
    onClearCanvas: () -> Unit,
    onPathEnd: () -> Unit,
    onNavigateBack: () -> Unit,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.close),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
                .clickable { onNavigateBack() }
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 29.dp)
                .padding(bottom = 20.dp)
            )
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(53.dp))
                Text(
                    text = stringResource(R.string.drawing_title),
                    style = TextStyle(
                        fontFamily = bagelFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 40.sp,
                    )
                )
                Spacer(Modifier.size(32.dp))
                val gridColour =  MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(Color.Transparent)
                        .shadow(
                            elevation = 1.dp,
                            shape = RoundedCornerShape(36.dp),
                            clip = false
                        )
                        .clip(RoundedCornerShape(36.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                            .clipToBounds()
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                shape = RoundedCornerShape(36.dp)
                            )
                            .pointerInput(true) {
                                detectDragGestures(
                                    onDragStart = { onPathStart() },
                                    onDragEnd = { onPathEnd() },
                                    onDrag = { change, _ -> onDraw(change.position) },
                                    onDragCancel = { onPathStart() },
                                )
                            }
                    ) {
                        drawGrid(gridColour)
                        uiState.paths.fastForEach { pathData ->
                            drawPath(
                                path = pathData.offsets,
                                color = Color.Black,
                            )
                        }
                        uiState.currentPath?.let {
                            drawPath(
                                path = it.offsets,
                                color = Color.Black
                            )
                        }
                    }
                }

                Spacer(Modifier.weight(1f))
                BottomBar(
                    onUndo = onUndo,
                    onRedo = onRedo
                )
            }

        }
    }
}

private fun DrawScope.drawGrid(gridColour: Color) {
    val canvasWidth = size.width
    val canvasHeight = size.height

    drawRoundRect(
        color = gridColour,
        size = size,
        cornerRadius = CornerRadius(36.dp.toPx(), 36.dp.toPx()),
        style = Stroke(width = 2.dp.toPx())
    )

    val thirdWidth = canvasWidth / 3
    val thirdHeight = canvasHeight / 3

    val lineWidth = 2.dp.toPx()

    for (i in 1..2) {
        val x = thirdWidth * i
        drawLine(
            color = gridColour,
            start = Offset(x, 0f),
            end = Offset(x, canvasHeight),
            strokeWidth = lineWidth
        )
    }

    for (i in 1..2) {
        val y = thirdHeight * i
        drawLine(
            color = gridColour,
            start = Offset(0f, y),
            end = Offset(canvasWidth, y),
            strokeWidth = lineWidth
        )
    }

}

private fun DrawScope.drawPath(
    path: List<Offset>,
    color: Color,
    thickness: Float = 10f
) {
    val smoothedPath = Path().apply {
        if(path.isNotEmpty()) {
            moveTo(path.first().x, path.first().y)

            val smoothness = 12
            for(i in 1..path.lastIndex) {
                val from = path[i - 1]
                val to = path[i]
                val dx = abs(from.x - to.x)
                val dy = abs(from.y - to.y)
                if(dx >= smoothness || dy >= smoothness) {
                    quadraticTo(
                        x1 = (from.x + to.x) / 2f,
                        y1 = (from.y + to.y) / 2f,
                        x2 = to.x,
                        y2 = to.y
                    )
                }
            }
        }
    }
    drawPath(
        path = smoothedPath,
        color = color,
        style = Stroke(
            width = thickness,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}

@Composable
fun BottomBar(onUndo: () -> Unit,
              onRedo : () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(22.dp)
                )
                .size(64.dp)
                .clickable { onUndo() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.undo),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(22.dp)
                )
                .size(64.dp)
                .clickable { onRedo() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.redo),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    shape = RoundedCornerShape(20.dp)
                )
                .height(64.dp)
                .weight(2f),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .background(
                        color = MaterialTheme.extendedColours.surfaceLowest,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.drawing_clear_canvas).uppercase(),
                    style = TextStyle(
                        fontFamily = bagelFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimary.copy(0.8f),
                        fontSize = 18.sp,
                    )
                )
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun DrawingScreenPreview() {
    MaterialTheme {
        DrawingScreen(
            uiState = DrawingState(),
            onNavigateBack = {},
            onDraw = {},
            onPathEnd = {},
            onPathStart = {},
            onClearCanvas = {},
            onUndo = {},
            onRedo = {}
        )
    }
}