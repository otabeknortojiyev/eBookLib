package uz.gita.otabek.ebooklib.screens.tabs.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.otabek.ebooklib.R
import uz.gita.otabek.ebooklib.ui.theme.MainColor


object InfoTab : Tab {
    private fun readResolve(): Any = InfoTab
    override val options: TabOptions
        @Composable get() {
            val title = "Инфо"

            return remember {
                TabOptions(
                    index = 0u, title = title
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: InfoContracts.ViewModel = getViewModel<InfoViewModel>()
        val uiState = viewModel.collectAsState()
        InfoScreenContent(uiState, viewModel::onEventDispatcher)
    }
}

@Composable
private fun InfoScreenContent(
    uiState: State<InfoContracts.UiState>, onEventDispatcher: (InfoContracts.Intent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MainColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(Color.White, fontFamily = FontFamily(Font(R.font.montserrat_semibold)), fontSize = 24.sp)) {
                append("Это приложение я сделал как домашнее задание когда учился в GITA Academy. Основные знания которые я тут использовал это: ")
            }
            withStyle(style = SpanStyle(Color.Blue, fontFamily = FontFamily(Font(R.font.montserrat_semibold)), fontSize = 24.sp)) {
                append("Firebase, FirebaseStorage,RecyclerView, PDFView, Coil, Navigation, BottomSheetDialog. ")
            }
            withStyle(style = SpanStyle(Color.White, fontFamily = FontFamily(Font(R.font.montserrat_semibold)), fontSize = 24.sp)) {
                append("Приложение находится на разработке. Ждите Обновления!")
            }
        }
        Text(text = annotatedString, modifier = Modifier.padding(16.dp))
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MainColor)
    systemUiController.setNavigationBarColor(MainColor)
}