package stevens.software.scribbledash

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class DrawingScreenViewModel : ViewModel() {

    private val _drawingState = MutableStateFlow(DrawingState())
    val drawingState = _drawingState.asStateFlow()

    data class DrawingState(
        val paths: List<PathData> = emptyList(),
        val currentPath: PathData? = null,
        val undonePaths: List<PathData> = emptyList()
    )

    data class PathData(
        val id: String,
        val offsets: List<Offset>
    )

    fun clearCanvas() {
        _drawingState.update {
            it.copy(
                paths = emptyList(),
                currentPath = null
            )
        }
    }

    fun onNewPathStart() {
        _drawingState.update {
            it.copy(
                currentPath = PathData(
                    id = UUID.randomUUID().toString(),
                    offsets = emptyList()
                )
            )
        }
    }

    fun onPathEnd() {
        val currentPathData = _drawingState.value.currentPath ?: return
        _drawingState.update {
            it.copy(
                currentPath = null,
                paths = it.paths + currentPathData
            )
        }
    }

    fun onDraw(offset: Offset) {
        val currentPathData = _drawingState.value.currentPath ?: return
        _drawingState.update {
            it.copy(
                currentPath = currentPathData.copy(
                    offsets = currentPathData.offsets + offset
                )
            )
        }
    }

    fun undoPath(){
        val paths = _drawingState.value.paths
        if(paths.isEmpty()) return

        val lastPath = paths.last()
        val newPathsList = paths.subList(0, paths.size -1)

        _drawingState.update {
            it.copy(
                paths = newPathsList,
                undonePaths = it.undonePaths + lastPath
            )
        }
    }
}