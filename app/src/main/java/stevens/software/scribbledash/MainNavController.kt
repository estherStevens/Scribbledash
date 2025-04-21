package stevens.software.scribbledash

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue

@Serializable
object Home

@Serializable
object GameDifficultyMode

@Serializable
object DrawingScreen

@Composable
fun MainNavController() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home){
        composable<Home> {
            HomeScreen(
                onNavigateToOneRoundWonder = {
                    navController.navigate(GameDifficultyMode)
                }
            )
        }
        composable<GameDifficultyMode>{
            GameDifficultyScreen(
                onNavigateBack = { navController.popBackStack() },
                onDifficultyLevelClicked = {
                    navController.navigate(DrawingScreen)
                }
            )
        }
        composable<DrawingScreen>{
            val viewModel = viewModel<DrawingScreenViewModel>()
            val uiState by viewModel.drawingState.collectAsStateWithLifecycle()
            DrawingScreen(
                uiState = uiState,
                onDraw = { viewModel.onDraw(it) },
                onClearCanvas = { viewModel.clearCanvas() },
                onPathEnd = { viewModel.onPathEnd() },
                onPathStart = { viewModel.onNewPathStart() },
                onNavigateBack = { navController.popBackStack() },
                onUndo = { viewModel.undoPath() },
                onRedo = { viewModel.redoPath() },
                startExampleImageTimer = { viewModel.startTimer() }
            )
        }
    }
}