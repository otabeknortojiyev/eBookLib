package uz.gita.otabek.ebooklib.screens.splash

import uz.gita.otabek.ebooklib.screens.main.MainScreen
import uz.gita.otabek.ebooklib.ui.navigation.AppNavigator
import javax.inject.Inject

class SplashDirections @Inject constructor(private val navigator: AppNavigator) : SplashContract.Direction {
    override suspend fun moveToHome() {
        navigator.replaceAll(MainScreen)
    }
}