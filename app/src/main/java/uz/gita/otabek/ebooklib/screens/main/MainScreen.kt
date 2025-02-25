package uz.gita.otabek.ebooklib.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import uz.gita.otabek.ebooklib.R
import uz.gita.otabek.ebooklib.screens.tabs.favourites.FavoriteTab
import uz.gita.otabek.ebooklib.screens.tabs.home.HomeTab
import uz.gita.otabek.ebooklib.screens.tabs.info.InfoTab
import uz.gita.otabek.ebooklib.ui.theme.MainColor

object MainScreen : Screen {
    private fun readResolve(): Any = MainScreen

    @Composable
    override fun Content() {
        TabNavigator(tab = HomeTab) {
            Scaffold(content = {
                CurrentTab();
                val p = it
            }, bottomBar = {
                BottomAppBar(
                    actions = {
                        TabNavigationItem(tab = HomeTab)
                        TabNavigationItem(tab = FavoriteTab)
                        TabNavigationItem(tab = InfoTab)
                    },
                    modifier = Modifier
                        .background(Color.White),
                    containerColor = MainColor
                )
            })
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    if (tab == tabNavigator.current) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clip(shape = RoundedCornerShape(20.dp))
                .padding(4.dp)
                .clickable { tabNavigator.current = tab },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = when (tab) {
                    HomeTab -> {
                        painterResource(id = R.drawable.home_white)
                    }

                    InfoTab -> {
                        painterResource(id = R.drawable.info_white)
                    }

                    else -> {
                        painterResource(id = R.drawable.favorite_white)
                    }
                },
                contentDescription = tab.options.title,
            )
            Text(
                text = tab.options.title, color = Color.White, fontSize = 12.sp
            )
        }
    } else {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable { tabNavigator.current = tab },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = when (tab) {
                    HomeTab -> {
                        painterResource(id = R.drawable.home_gray)
                    }

                    InfoTab -> {
                        painterResource(id = R.drawable.info_gray)
                    }

                    else -> {
                        painterResource(id = R.drawable.favorite_gray)
                    }
                },
                contentDescription = tab.options.title,
            )
            Text(
                text = tab.options.title, color = Color.LightGray, fontSize = 12.sp
            )
        }
    }
}