package uz.gita.otabek.ebooklib.screens.book

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val direction: BookContract.Direction
) : ViewModel(), BookContract.ViewModel {
    override fun onEventDispatcher(intent: BookContract.Intent) = intent {
        when (intent) {

        }
    }

    override val container = container<BookContract.UiState, BookContract.SideEffect>(BookContract.UiState())
}