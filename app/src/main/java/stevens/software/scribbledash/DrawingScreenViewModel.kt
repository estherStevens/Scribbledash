package stevens.software.scribbledash

import android.os.CountDownTimer
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

sealed interface DrawingState{
    data class ExampleDrawing(
        val img: Int,
        val countdown: Long,
    ) : DrawingState

    data class UserDrawingState(
        val paths: List<PathData> = emptyList(),
        val currentPath: PathData? = null,
        val undonePaths: List<PathData> = emptyList()
    ) : DrawingState
}

data class PathData(
    val id: String,
    val offsets: List<Offset>
)

class DrawingScreenViewModel : ViewModel() {

    private val _drawingState = MutableStateFlow<DrawingState>(exampleDrawing())
    val drawingState = _drawingState.asStateFlow()

    init {
         object : CountDownTimer(3000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished.toDuration(DurationUnit.MILLISECONDS)

                _drawingState.update { currentState ->
                    when(currentState is DrawingState.ExampleDrawing){
                        true -> currentState.copy(countdown = secondsRemaining.roundedUpSeconds())
                        false -> currentState
                    }
                }
            }

            override fun onFinish() {
                _drawingState.value = DrawingState.UserDrawingState()
            }
        }.start()
    }

    private fun exampleDrawing(): DrawingState.ExampleDrawing{
        return DrawingState.ExampleDrawing(
            img = R.drawable.whale,
            countdown = 3
        )
    }

    fun clearCanvas() {
        _drawingState.update { currentState ->
            when(currentState is DrawingState.UserDrawingState){
                true -> {
                    currentState.copy(
                        paths = emptyList(),
                        currentPath = null,
                        undonePaths = emptyList()
                    )
                }
                false -> currentState
            }
        }
    }

    fun onNewPathStart() {
        _drawingState.update { currentState ->
            when(currentState is DrawingState.UserDrawingState){
                true -> {
                    currentState.copy(
                        currentPath = PathData(
                            id = UUID.randomUUID().toString(),
                            offsets = emptyList()
                        )
                    )
                }
                false -> currentState
            }
        }
    }

    fun onPathEnd() {
        _drawingState.update { currentState ->
            when(currentState is DrawingState.UserDrawingState){
                true -> {
                    if(currentState.currentPath == null) return
                    currentState.copy(
                        currentPath = null,
                        paths = currentState.paths + currentState.currentPath
                    )
                }
                false -> currentState
            }
        }
    }

    fun onDraw(offset: Offset) {
        _drawingState.update { currentState ->
            when(currentState is DrawingState.UserDrawingState){
                true -> {
                    if(currentState.currentPath == null) return
                    currentState.copy(
                        currentPath = currentState.currentPath.copy(
                            offsets = currentState.currentPath.offsets + offset
                        )
                    )
                }
                false -> currentState
            }
        }
    }

    fun undoPath() {
        _drawingState.update { currentState ->
            when(currentState is DrawingState.UserDrawingState){
                true -> {
                    if(currentState.paths.isEmpty()) return
                    val lastPath = currentState.paths.last()
                    val newPathsList = currentState.paths.subList(0, currentState.paths.size - 1)
                    currentState.copy(
                        paths = newPathsList,
                        undonePaths = currentState.undonePaths + lastPath
                    )
                }
                false -> currentState
            }
        }
    }

    fun redoPath() {
        _drawingState.update { currentState ->
            when(currentState is DrawingState.UserDrawingState){
                true -> {
                    if(currentState.undonePaths.isEmpty()) return
                    val paths = currentState.paths
                    val lastUndonePath = currentState.undonePaths.last()
                    val newUndonePathsList = currentState.undonePaths.subList(0, currentState.undonePaths.size - 1)

                    currentState.copy(
                        paths = paths + lastUndonePath,
                        undonePaths = newUndonePathsList
                    )
                }
                false -> currentState
            }
        }
    }

    fun Duration.roundedUpSeconds(): Long {
        val seconds = this.inWholeSeconds
        return if (this - seconds.seconds > Duration.ZERO) seconds + 1 else seconds
    }
}