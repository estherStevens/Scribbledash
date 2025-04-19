package stevens.software.scribbledash

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

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
            DrawingScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}