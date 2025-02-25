package uz.gita.otabek.ebooklib.screens.tabs.home

import uz.gita.otabek.ebooklib.screens.book.BookScreen
import uz.gita.otabek.ebooklib.ui.navigation.AppNavigator
import javax.inject.Inject

class HomeDirections @Inject constructor(private val navigator: AppNavigator) : HomeContracts.Directions {
    override suspend fun moveToRead(path: String) {
        navigator.push(BookScreen(path))
    }
}