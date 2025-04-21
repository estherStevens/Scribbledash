package stevens.software.scribbledash

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import stevens.software.scribbledash.DrawingScreenViewModel.PathData
import java.util.UUID

sealed interface DrawingState{
    data class ExampleDrawing(
        val img: Int,
        val countdown: Int,
    ) : DrawingState

    data class UserDrawingState(
        val paths: List<PathData> = emptyList(),
        val currentPath: PathData? = null,
        val undonePaths: List<PathData> = emptyList()
    ) : DrawingState
}

class DrawingScreenViewModel : ViewModel() {

    private val _drawingState = MutableStateFlow(exampleDrawing())
    val drawingState = _drawingState.asStateFlow()

    data class UserDrawingState(
        val paths: List<PathData> = emptyList(),
        val currentPath: PathData? = null,
        val undonePaths: List<PathData> = emptyList()
    )

    data class PathData(
        val id: String,
        val offsets: List<Offset>
    )

    private fun exampleDrawing(): DrawingState.ExampleDrawing{
        return DrawingState.ExampleDrawing(
            img = R.drawable.whale,
            countdown = 1
        )
    }

    fun clearCanvas() {
        val userDrawingState = _drawingState.value
        if (userDrawingState is DrawingState.UserDrawingState) {
            userDrawingState.copy(
                paths = emptyList(),
                currentPath = null,
                undonePaths = emptyList()
            )
        }

    }

    fun onNewPathStart() {
        val userDrawingState = _drawingState.value
        if (userDrawingState is DrawingState.UserDrawingState) {
            userDrawingState.copy(
                currentPath = PathData(
                    id = UUID.randomUUID().toString(),
                    offsets = emptyList()
                )
            )
        }
    }

    fun onPathEnd() {
        val userDrawingState = _drawingState.value
        if (userDrawingState is DrawingState.UserDrawingState) {
            val currentPathData = userDrawingState.currentPath ?: return
            userDrawingState.copy(
                currentPath = null,
                paths = userDrawingState.paths + currentPathData
            )
        }
    }

    fun onDraw(offset: Offset) {
        val userDrawingState = _drawingState.value
        if (userDrawingState is DrawingState.UserDrawingState) {
            val currentPathData = userDrawingState.currentPath ?: return
            userDrawingState.copy(
                currentPath = currentPathData.copy(
                    offsets = currentPathData.offsets + offset
                )
            )
        }
    }

    fun undoPath() {
        val userDrawingState = _drawingState.value
        if (userDrawingState is DrawingState.UserDrawingState) {
            val paths = userDrawingState.paths
            if (paths.isEmpty()) return

            val lastPath = paths.last()
            val newPathsList = paths.subList(0, paths.size - 1)

            userDrawingState.copy(
                paths = newPathsList,
                undonePaths = userDrawingState.undonePaths + lastPath
            )

        }
    }

    fun redoPath() {
        val userDrawingState = _drawingState.value
        if (userDrawingState is DrawingState.UserDrawingState) {
            val undonePaths = userDrawingState.undonePaths
            if (undonePaths.isEmpty()) return

            val paths = userDrawingState.paths
            val lastUndonePath = undonePaths.last()
            val newUndonePathsList = undonePaths.subList(0, undonePaths.size - 1)

            userDrawingState.copy(
                paths = paths + lastUndonePath,
                undonePaths = newUndonePathsList
            )
        }

    }
}