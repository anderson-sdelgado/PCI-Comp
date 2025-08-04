package br.com.usinasantafe.pci.presenter.view.note.questionobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class QuestionObsNoteState(
    val obs: String? = null,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class QuestionObsNoteViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestionObsNoteState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onObsChanged(obs: String) {
        _uiState.update {
            it.copy(obs = obs)
        }
    }



}