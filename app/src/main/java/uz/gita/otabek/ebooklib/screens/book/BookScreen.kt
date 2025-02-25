package uz.gita.otabek.ebooklib.screens.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rizzi.bouquet.HorizontalPDFReader
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.rememberHorizontalPdfReaderState
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.otabek.ebooklib.ui.theme.MainColor

data class BookScreen(val path: String) : Screen {
    @Composable
    override fun Content() {
        val viewModel: BookContract.ViewModel = getViewModel<BookViewModel>()
        val uiState = viewModel.collectAsState()
        BookScreenContent(uiState, viewModel::onEventDispatcher, path)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BookScreenContent(
    uiState: State<BookContract.UiState>, onEventDispatcher: (BookContract.Intent) -> Unit, path: String
) {
    var isLoaded: Boolean = true
    val pdfState = rememberHorizontalPdfReaderState(
        resource = ResourceType.Remote(path), isZoomEnable = true
    )
    isLoaded = !pdfState.isLoaded
    val pullRefreshState = rememberPullRefreshState(refreshing = isLoaded, onRefresh = {})
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MainColor)
            .pullRefresh(pullRefreshState)
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Текущая страница: " + (pdfState.currentPage + 1).toString(), modifier = Modifier.padding(16.dp))
                Text(text = "Общее число страниц: " + pdfState.pdfPageCount.toString(), modifier = Modifier.padding(16.dp))
            }
            HorizontalPDFReader(
                state = pdfState, modifier = Modifier
                    .background(color = MainColor)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = if (!pdfState.isLoaded) {
                    "Книга загружается..."
                } else {
                    ""
                }
            )
        }
        PullRefreshIndicator(
            refreshing = isLoaded,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = if (uiState.value.isLoading) Color.Red else Color.Green,
        )
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MainColor)
    systemUiController.setNavigationBarColor(MainColor)
}