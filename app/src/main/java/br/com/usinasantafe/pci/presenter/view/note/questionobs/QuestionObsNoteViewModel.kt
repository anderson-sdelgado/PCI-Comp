package br.com.usinasantafe.pci.presenter.view.note.questionobs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pci.domain.usecases.note.SetRespItem
import br.com.usinasantafe.pci.presenter.Args.ID_ITEM_ARG
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class QuestionObsNoteState(
    val obs: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
)

@HiltViewModel
class QuestionObsNoteViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val setRespItem: SetRespItem
) : ViewModel() {

    private val id: Int = saveStateHandle[ID_ITEM_ARG]!!

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

    fun setResp() = viewModelScope.launch {
        if (uiState.value.obs.isEmpty()) {
            val failure = "${getClassAndMethod()} -> Field Empty!"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val result = setRespItem(
            id = id,
            option = OptionResp.NON_CONFORMING,
            obs = uiState.value.obs
        )
        if(result.isFailure){
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                    errors = Errors.EXCEPTION
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagAccess = true
            )
        }
    }



}