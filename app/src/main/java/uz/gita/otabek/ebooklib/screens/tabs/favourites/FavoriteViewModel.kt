package uz.gita.otabek.ebooklib.screens.tabs.favourites

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.otabek.ebooklib.data.BookData
import uz.gita.otabek.ebooklib.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val direction: FavoriteContracts.Directions,
    private val repository: AppRepository
) : FavoriteContracts.ViewModel, ViewModel() {

    override fun onEventDispatcher(intent: FavoriteContracts.Intent) = intent {
        when (intent) {
            FavoriteContracts.Intent.InitData -> {
                reduce { state.copy(isLoading = true) }
                val books = repository.loadAllFavoriteBooks()
                reduce { state.copy(isLoading = false, books = books) }
            }

            is FavoriteContracts.Intent.ReadBook -> {
                direction.moveToRead(intent.path)
            }

            is FavoriteContracts.Intent.UpdateBook -> {
                reduce { state.copy(isLoading = true) }
                repository.updateBook(intent.data)
                val list = mutableListOf<BookData>()
                for (index in 0 until state.books.size) {
                    if (index != intent.index) {
                        list.add(state.books[index])
                    }
                }
                reduce { state.copy(isLoading = false, books = list) }
            }
        }
    }

    override val container = container<FavoriteContracts.UiState, FavoriteContracts.SideEffect>(FavoriteContracts.UiState())
}