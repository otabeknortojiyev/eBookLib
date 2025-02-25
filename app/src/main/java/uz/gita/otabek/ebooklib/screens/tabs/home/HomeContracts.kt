package uz.gita.otabek.ebooklib.screens.tabs.home

import org.orbitmvi.orbit.ContainerHost
import uz.gita.otabek.ebooklib.data.AuthorData
import uz.gita.otabek.ebooklib.data.BookData
import uz.gita.otabek.ebooklib.data.GenresData
import uz.gita.otabek.ebooklib.utils.GenresType

interface HomeContracts {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val isBottomSheetVisible: Boolean = false,
        val isLoading: Boolean = false,
        val books: List<BookData> = emptyList(),
        val genres: List<GenresData> = arrayListOf(
            GenresData("Все", Intent.All),
            GenresData("По автору", Intent.ByAuthor),
            GenresData("Фантастика", Intent.Fantasy(GenresType.Fantasy)),
            GenresData("Комиксы", Intent.Comics(GenresType.COMICS)),
            GenresData("Приключения", Intent.Adventure(GenresType.ADVENTURE)),
            GenresData("Мистика", Intent.Mysticism(GenresType.MYSTICISM)),
            GenresData("Учебная литература", Intent.EducationalLiterature(GenresType.EDUCATIONAL_LITERATURE)),
            GenresData("Роман", Intent.Novel(GenresType.NOVEL)),
        ),
        val authors: List<AuthorData> = emptyList(),
        val favoriteList: MutableList<Boolean> = mutableListOf()
    )

    sealed interface SideEffect {
        data class ResultMessage(val message: String) : SideEffect
    }

    interface Directions {
        suspend fun moveToRead(path: String)
    }

    interface Intent {
        data object InitData : Intent
        data object All : Intent
        data object ByAuthor : Intent
        data class Fantasy(val genre: GenresType) : Intent
        data class Adventure(val genre: GenresType) : Intent
        data class Mysticism(val genre: GenresType) : Intent
        data class EducationalLiterature(val genre: GenresType) : Intent
        data class Comics(val genre: GenresType) : Intent
        data class Novel(val genre: GenresType) : Intent
        data class ReadBook(val path: String) : Intent
        data class Authors(val value: String) : Intent
        object ShowBottomSheet : Intent
        object HideBottomSheet : Intent
        data class UpdateBook(val data: BookData, val index: Int) : Intent
    }
}