package uz.gita.otabek.ebooklib.screens.book

import org.orbitmvi.orbit.ContainerHost

interface BookContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(val isLoading: Boolean = false)

    sealed interface SideEffect {
        data class ResultMessage(val message: String) : SideEffect
    }

    interface Direction {

    }

    interface Intent {

    }
}