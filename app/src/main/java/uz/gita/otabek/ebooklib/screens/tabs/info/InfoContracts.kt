package uz.gita.otabek.ebooklib.screens.tabs.info

import org.orbitmvi.orbit.ContainerHost
import uz.gita.otabek.ebooklib.data.AuthorData
import uz.gita.otabek.ebooklib.data.BookData
import uz.gita.otabek.ebooklib.data.GenresData
import uz.gita.otabek.ebooklib.utils.GenresType

interface InfoContracts {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val isLoading: Boolean = false,
    )

    sealed interface SideEffect {
        data class ResultMessage(val message: String) : SideEffect
    }

    interface Directions {

    }

    interface Intent {

    }
}