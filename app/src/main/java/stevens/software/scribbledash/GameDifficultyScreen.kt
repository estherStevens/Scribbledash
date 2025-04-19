package stevens.software.scribbledash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    onNavigateBack: () -> Unit,
    onDifficultyLevelClicked: (DifficultyLevel) -> Unit
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
        Spacer(Modifier.size(55.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(36.dp)) {
            GameDifficultyOption(
                difficultyLevel = DifficultyLevel.BEGINNER,
                image = R.drawable.level_beginner,
                text = R.string.game_difficulty_beginner,
                alignment = Alignment.TopEnd,
                modifier = Modifier.padding(top = 16.dp),
                onDifficultyLevelClicked = onDifficultyLevelClicked
            )
            GameDifficultyOption(
                difficultyLevel = DifficultyLevel.CHALLENGING,
                image = R.drawable.level_challenging,
                text = R.string.game_difficulty_challenging,
                alignment = Alignment.Center,
                modifier = Modifier,
                onDifficultyLevelClicked = onDifficultyLevelClicked
            )
            GameDifficultyOption(
                difficultyLevel = DifficultyLevel.MASTER,
                image = R.drawable.level_master,
                text = R.string.game_difficulty_master,
                alignment = Alignment.Center,
                modifier = Modifier.padding(top = 16.dp),
                onDifficultyLevelClicked = onDifficultyLevelClicked
            )
        }
    }

}

@Composable
private fun GameDifficultyOption(
    difficultyLevel: DifficultyLevel,
    image: Int,
    text: Int,
    alignment: Alignment,
    modifier: Modifier,
    onDifficultyLevelClicked: (DifficultyLevel) -> Unit
) {
    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = modifier
                    .shadow(
                        elevation = 2.dp,
                        shape = CircleShape
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        shape = CircleShape,
                    )
                    .size(88.dp, 88.dp)
                    .clip(CircleShape)
                    .clickable { onDifficultyLevelClicked(difficultyLevel) }
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.align(alignment)
                )
            }

            Spacer(Modifier.size(12.dp))
            Text(
                text = stringResource(text),
                style = TextStyle(
                    fontFamily = outfitFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.extendedColours.onBackgroundVariant,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    MaterialTheme {
        GameDifficultyScreen(
            onNavigateBack = {},
            onDifficultyLevelClicked = {}
        )
    }
}

enum class DifficultyLevel {
    BEGINNER,
    CHALLENGING,
    MASTER
}