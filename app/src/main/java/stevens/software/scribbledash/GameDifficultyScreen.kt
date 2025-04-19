package stevens.software.scribbledash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GameDifficultyScreen(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .fillMaxSize()
    ) {
        Icon(
            painter = painterResource(R.drawable.close),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
                .clickable{ onNavigateBack() }
        )
    }

}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    MaterialTheme {
        GameDifficultyScreen(
            onNavigateBack = {}
        )
    }
}