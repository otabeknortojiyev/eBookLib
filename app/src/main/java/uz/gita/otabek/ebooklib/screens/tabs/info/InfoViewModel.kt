package uz.gita.otabek.ebooklib.screens.tabs.info

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.otabek.ebooklib.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val direction: InfoContracts.Directions, private val repository: AppRepository
) : InfoContracts.ViewModel, ViewModel() {

    override fun onEventDispatcher(intent: InfoContracts.Intent) = intent {
        when (intent) {

        }
    }

    override val container = container<InfoContracts.UiState, InfoContracts.SideEffect>(InfoContracts.UiState())
}