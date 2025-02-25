package uz.gita.otabek.ebooklib.screens.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.otabek.ebooklib.R


object SplashScreen : Screen {
    private fun readResolve(): Any = SplashScreen

    @Composable
    override fun Content() {
        val viewModel: SplashContract.ViewModel = getViewModel<SplashViewModel>()
        val uiState = viewModel.collectAsState()
        SplashScreenContent(uiState, viewModel::onEventDispatcher)
    }
}

@SuppressLint("ResourceAsColor")
@Composable
private fun SplashScreenContent(
    uiState: State<SplashContract.UiState>, onEventDispatcher: (SplashContract.Intent) -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onEventDispatcher(SplashContract.Intent.MoveToHome)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Text(
            text = "ReadEasy",
            color = Color.Black,
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.montserrat_bold)),
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White)
    systemUiController.setNavigationBarColor(Color.White)
}