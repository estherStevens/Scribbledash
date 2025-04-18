package stevens.software.scribbledash

import android.inputmethodservice.Keyboard.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stevens.software.scribbledash.ui.theme.extendedColours

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home_title),
                        style = TextStyle(
                            fontFamily = bagelFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 26.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColour())
                    .padding(padding)
                    .padding(top = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.home_header),
                    style = TextStyle(
                        fontFamily = bagelFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 44.sp
                    )
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = stringResource(R.string.home_subheader),
                    style = TextStyle(
                        fontFamily = outfitFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.extendedColours.onBackgroundVariant,
                        fontSize = 16.sp
                    )
                )
            }
        },
        bottomBar = {
            BottomNavBar()
        }
    )
}

@Composable
private fun BottomNavBar() {
    var selectedItem by remember { mutableStateOf(BottomNavItem.HOME) }
    val homeSelected = selectedItem == BottomNavItem.HOME
    val chartSelected = selectedItem == BottomNavItem.CHART

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        NavigationBarItem(
            selected = chartSelected,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.chart),
                    contentDescription = stringResource(R.string.chart),
                )
            },
            onClick = { selectedItem = BottomNavItem.CHART },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = MaterialTheme.extendedColours.surfaceLowest,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = homeSelected,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.home),
                    contentDescription = stringResource(R.string.home),
                )
            },
            onClick = { selectedItem = BottomNavItem.HOME },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = MaterialTheme.extendedColours.surfaceLowest,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                indicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun backgroundColour() = Brush.verticalGradient(
    listOf(
        MaterialTheme.extendedColours.backgroundLightGradient,
        MaterialTheme.extendedColours.backgroundDarkGradient
    )
)

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}

enum class BottomNavItem {
    HOME,
    CHART
}