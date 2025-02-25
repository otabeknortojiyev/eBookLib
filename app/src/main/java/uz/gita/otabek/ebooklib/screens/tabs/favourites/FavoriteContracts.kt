package uz.gita.otabek.ebooklib.screens.tabs.favourites

import org.orbitmvi.orbit.ContainerHost
import uz.gita.otabek.ebooklib.data.BookData
import uz.gita.otabek.ebooklib.screens.tabs.home.HomeContracts.Intent

interface FavoriteContracts {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val isLoading: Boolean = false, val books: List<BookData> = emptyList()
    )

    sealed interface SideEffect {
        data class ResultMessage(val message: String) : SideEffect
    }

    interface Directions {
        suspend fun moveToRead(path: String)
    }

    interface Intent {
        data object InitData : Intent
        data class ReadBook(val path: String) : Intent
        data class UpdateBook(val data: BookData, val index: Int) : Intent
    }
}