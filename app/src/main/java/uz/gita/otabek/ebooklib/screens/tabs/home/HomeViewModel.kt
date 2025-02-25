package uz.gita.otabek.ebooklib.screens.tabs.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.otabek.ebooklib.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val direction: HomeContracts.Directions, private val repository: AppRepository
) : HomeContracts.ViewModel, ViewModel() {

    override fun onEventDispatcher(intent: HomeContracts.Intent) = intent {
        when (intent) {
            HomeContracts.Intent.InitData -> {
                reduce { state.copy(isLoading = true) }
                val books = repository.loadAllBooks()
                val favorites = mutableListOf<Boolean>()
                for (data in books) {
                    favorites.add(data.favorite)
                }
                reduce { state.copy(isLoading = false, books = books, favoriteList = favorites) }
            }

            HomeContracts.Intent.All -> {
                reduce { state.copy(isLoading = true) }
                val books = repository.loadAllBooks()
                val favorites = mutableListOf<Boolean>()
                for (data in books) {
                    favorites.add(data.favorite)
                }
                reduce { state.copy(isLoading = false, books = books, favoriteList = favorites) }
            }

            HomeContracts.Intent.ByAuthor -> {
                reduce { state.copy(isLoading = true) }
                val authors = repository.loadAuthors()
                reduce { state.copy(isLoading = false, authors = authors) }
            }

            is HomeContracts.Intent.Fantasy -> {
                reduce { state.copy(isLoading = true) }
                val books = repository.loadBooksByGenre(intent.genre.value)
                val favorites = mutableListOf<Boolean>()
                for (data in books) {
                    favorites.add(data.favorite)
                }
                reduce { state.copy(isLoading = false, books = books, favoriteList = favorites) }
            }

            is HomeContracts.Intent.Novel -> {
                reduce { state.copy(isLoading = true) }
                val books = repository.loadBooksByGenre(intent.genre.value)
                val favorites = mutableListOf<Boolean>()
                for (data in books) {
                    favorites.add(data.favorite)
                }
                reduce { state.copy(isLoading = false, books = books, favoriteList = favorites) }
            }

            is HomeContracts.Intent.Comics -> {
                reduce { state.copy(isLoading = true) }
                val books = repository.loadBooksByGenre(intent.genre.value)
                val favorites = mutableListOf<Boolean>()
                for (data in books) {
                    favorites.add(data.favorite)
                }
                reduce { state.copy(isLoading = false, books = books, favoriteList = favorites) }
            }

            is HomeContracts.Intent.Adventure -> {
                reduce { state.copy(isLoading = true) }
                val books = repository.loadBooksByGenre(intent.genre.value)
                val favorites = mutableListOf<Boolean>()
                for (data in books) {
                    favorites.add(data.favorite)
                }
                reduce { state.copy(isLoading = false, books = books, favoriteList = favorites) }
            }

            is HomeContracts.Intent.Mysticism -> {
                reduce { state.copy(isLoading = true) }
                val books = repository.loadBooksByGenre(intent.genre.value)
                val favorites = mutableListOf<Boolean>()
                for (data in books) {
                    favorites.add(data.favorite)
                }
                reduce { state.copy(isLoading = false, books = books, favoriteList = favorites) }
            }

            is HomeContracts.Intent.EducationalLiterature -> {
                reduce { state.copy(isLoading = true) }
                val books = repository.loadBooksByGenre(intent.genre.value)
                val favorites = mutableListOf<Boolean>()
                for (data in books) {
                    favorites.add(data.favorite)
                }
                reduce { state.copy(isLoading = false, books = books, favoriteList = favorites) }
            }

            is HomeContracts.Intent.Authors -> {
                reduce { state.copy(isLoading = true, isBottomSheetVisible = false) }
                val books = repository.loadBooksByAuthors(intent.value)
                val favorites = mutableListOf<Boolean>()
                for (data in books) {
                    favorites.add(data.favorite)
                }
                reduce { state.copy(isLoading = false, books = books, favoriteList = favorites) }
            }

            is HomeContracts.Intent.ReadBook -> {
                direction.moveToRead(intent.path)
            }

            HomeContracts.Intent.ShowBottomSheet -> {
                reduce { state.copy(isBottomSheetVisible = true) }
            }

            HomeContracts.Intent.HideBottomSheet -> {
                reduce { state.copy(isBottomSheetVisible = false) }
            }

            is HomeContracts.Intent.UpdateBook -> {
                reduce { state.copy(isLoading = true) }
                repository.updateBook(intent.data)
                val list = state.favoriteList
                list[intent.index] = intent.data.favorite
                reduce { state.copy(isLoading = false, favoriteList = list) }
            }
        }
    }

    override val container = container<HomeContracts.UiState, HomeContracts.SideEffect>(HomeContracts.UiState())
}
