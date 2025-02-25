package uz.gita.otabek.ebooklib.screens.splash

import org.orbitmvi.orbit.ContainerHost

interface SplashContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(val language: String = "")

    sealed interface SideEffect {
        data class ResultMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun moveToHome()
    }


    interface Intent {
        data object MoveToHome : Intent
    }
}