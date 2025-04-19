package stevens.software.scribbledash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stevens.software.scribbledash.ui.theme.extendedColours

@Composable
fun GameDifficultyScreen(
    onNavigateBack: () -> Unit
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
        Spacer(Modifier.size(126.dp))
        Text(
            text = stringResource(R.string.game_difficulty_title),
            style = TextStyle(
                fontFamily = bagelFontFamily,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 40.sp
            )
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = stringResource(R.string.game_difficulty_subtitle),
            style = TextStyle(
                fontFamily = outfitFontFamily,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.extendedColours.onBackgroundVariant,
                fontSize = 16.sp
            )
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