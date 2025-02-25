package uz.gita.otabek.ebooklib.screens.tabs.home

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.otabek.ebooklib.R
import uz.gita.otabek.ebooklib.data.BookData
import uz.gita.otabek.ebooklib.ui.theme.MainColor
import uz.gita.otabek.ebooklib.ui.theme.MainColor2


object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab
    override val options: TabOptions
        @Composable get() {
            val title = "Главная"

            return remember {
                TabOptions(
                    index = 0u, title = title
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: HomeContracts.ViewModel = getViewModel<HomeViewModel>()
        val uiState = viewModel.collectAsState()
        HomeScreenContent(uiState, viewModel::onEventDispatcher)
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun HomeScreenContent(
    uiState: State<HomeContracts.UiState>, onEventDispatcher: (HomeContracts.Intent) -> Unit
) {
    val pullRefreshState =
        rememberPullRefreshState(refreshing = uiState.value.isLoading, onRefresh = { onEventDispatcher(HomeContracts.Intent.InitData) })
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    if (uiState.value.isBottomSheetVisible) {
        coroutineScope.launch { bottomSheetState.show() }
    } else {
        coroutineScope.launch { bottomSheetState.hide() }
    }
    LaunchedEffect(Unit) {
        onEventDispatcher(HomeContracts.Intent.InitData)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
            .background(color = MainColor)
            .pullRefresh(pullRefreshState)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyRow(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)) {
                items(uiState.value.genres) {
                    Genres(it.genreString, it.intent, onEventDispatcher)
                }
            }
            LazyColumn {
                itemsIndexed(uiState.value.books) { index, value ->
                    BooksForHome(value, HomeContracts.Intent.ReadBook(value.path), onEventDispatcher, index, uiState.value.favoriteList[index])
                }
            }
        }
        PullRefreshIndicator(
            refreshing = uiState.value.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = if (uiState.value.isLoading) Color.Red else Color.Green,
        )
        if (uiState.value.isBottomSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { onEventDispatcher(HomeContracts.Intent.HideBottomSheet) },
                sheetState = bottomSheetState,
                contentColor = Color.Gray
            ) {
                if (uiState.value.authors.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        itemsIndexed(uiState.value.authors) { index, author ->
                            Text(
                                text = author.name,
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                fontSize = 24.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 20.dp,
                                        top = 20.dp,
                                        end = 20.dp,
                                        bottom = if (index == uiState.value.authors.size - 1) 36.dp else 20.dp
                                    )
                                    .clip(shape = RoundedCornerShape(10.dp))
                                    .background(color = Color.White)
                                    .padding(20.dp)
                                    .clickable {
                                        onEventDispatcher(HomeContracts.Intent.Authors(author.name))
                                    },
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MainColor)
    systemUiController.setNavigationBarColor(MainColor)
}

@Composable
fun Genres(genre: String, intent: HomeContracts.Intent, onEventDispatcher: (HomeContracts.Intent) -> Unit) {
    Text(text = genre,
        color = Color.Black,
        fontSize = 24.sp,
        modifier = Modifier
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MainColor2)
            .padding(10.dp)
            .clickable {
                if (intent == HomeContracts.Intent.ByAuthor) {
                    onEventDispatcher(HomeContracts.Intent.ShowBottomSheet)
                }
                onEventDispatcher(intent)
            })
}

@Composable
fun BooksForHome(
    data: BookData, intent: HomeContracts.Intent.ReadBook, onEventDispatcher: (HomeContracts.Intent) -> Unit, index: Int, favorite: Boolean
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .background(color = Color.White)
        .clickable {
            onEventDispatcher(intent)
        }
        .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
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
                Image(painter = if (favorite) {
                    painterResource(id = R.drawable.like_red)
                } else {
                    painterResource(id = R.drawable.like_gray)
                },
                    contentDescription = null,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .clickable { onEventDispatcher(HomeContracts.Intent.UpdateBook(data.copy(favorite = !data.favorite), index)) }
                        .padding(16.dp))
            }
            Text(text = data.name)
            Text(text = data.author)
            Text(text = data.year)
        }
    }
}