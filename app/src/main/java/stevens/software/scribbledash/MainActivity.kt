package stevens.software.scribbledash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import stevens.software.scribbledash.ui.theme.ScribbleDashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScribbleDashTheme {
                MainNavController()
            }
        }
    }
}

val bagelFontFamily = FontFamily(
    Font(resId = R.font.bagelfatone_regular, weight = FontWeight.Normal)
)

val outfitFontFamily = FontFamily(
    Font(resId = R.font.outfit_regular, weight = FontWeight.Normal),
    Font(resId = R.font.outfit_medium, weight = FontWeight.Medium),
    Font(resId = R.font.outfit_semibold, weight = FontWeight.SemiBold),
)
