package uz.gita.otabek.ebooklib.screens.tabs.favourites

import uz.gita.otabek.ebooklib.screens.book.BookScreen
import uz.gita.otabek.ebooklib.ui.navigation.AppNavigator
import javax.inject.Inject

class FavoriteDirections @Inject constructor(private val navigator: AppNavigator) : FavoriteContracts.Directions {
    override suspend fun moveToRead(path: String) {
        navigator.push(BookScreen(path))
    }

}