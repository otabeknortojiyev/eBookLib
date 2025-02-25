package uz.gita.otabek.ebooklib.screens.tabs.favourites

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.otabek.ebooklib.R
import uz.gita.otabek.ebooklib.data.BookData
import uz.gita.otabek.ebooklib.ui.theme.MainColor


object FavoriteTab : Tab {
    private fun readResolve(): Any = FavoriteTab

    override val options: TabOptions
        @Composable get() {
            val title = "Любимые"

            return remember {
                TabOptions(
                    index = 0u, title = title
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: FavoriteContracts.ViewModel = getViewModel<FavoriteViewModel>()
        val uiState = viewModel.collectAsState()
        FavoriteScreenContent(uiState, viewModel::onEventDispatcher)
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FavoriteScreenContent(
    uiState: State<FavoriteContracts.UiState>, onEventDispatcher: (FavoriteContracts.Intent) -> Unit,
) {
    val pullRefreshState =
        rememberPullRefreshState(refreshing = uiState.value.isLoading, onRefresh = { onEventDispatcher(FavoriteContracts.Intent.InitData) })
    LaunchedEffect(Unit) {
        onEventDispatcher(FavoriteContracts.Intent.InitData)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        if (uiState.value.books.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .align(alignment = Alignment.Center)
                    .background(color = MainColor)
                    .padding(36.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MainColor)
            ) {
                itemsIndexed(uiState.value.books, key = { _, book -> book.id }) { index, value ->
                    BooksForFavorite(
                        data = value,
                        intent = FavoriteContracts.Intent.ReadBook(value.path),
                        onEventDispatcher = onEventDispatcher,
                        index = index,
                        modifier = Modifier.animateItem(
                            fadeOutSpec = tween(1000), placementSpec = spring(stiffness = Spring.StiffnessMediumLow)
                        )
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = uiState.value.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = if (uiState.value.isLoading) Color.Red else Color.Green,
        )
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MainColor)
    systemUiController.setNavigationBarColor(MainColor)
}

@Composable
fun BooksForFavorite(
    data: BookData,
    intent: FavoriteContracts.Intent.ReadBook,
    onEventDispatcher: (FavoriteContracts.Intent) -> Unit,
    index: Int,
    modifier: Modifier = Modifier,
) {
    val defaultModifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .background(color = Color.White)
        .clickable {
            onEventDispatcher(intent)
        }
        .padding(10.dp)
    Row(defaultModifier.then(modifier), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberAsyncImagePainter(data.imagePath),
            contentDescription = null,
            modifier = Modifier
                .size(width = 100.dp, height = 150.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp), verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                Image(painter = painterResource(id = R.drawable.like_red),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .clickable {
                            onEventDispatcher(
                                FavoriteContracts.Intent.UpdateBook(data.copy(favorite = !data.favorite), index)
                            )
                        }
                        .padding(16.dp))
            }
            Text(text = data.name)
            Text(text = data.author)
            Text(text = data.year)
        }
    }
}