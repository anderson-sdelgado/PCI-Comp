package br.com.usinasantafe.pci.presenter.view.note.questiondesc

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pci.domain.usecases.note.GetResp
import br.com.usinasantafe.pci.presenter.Args.ID_ITEM_ARG
import br.com.usinasantafe.pci.presenter.model.RespScreenModel
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class QuestionDescNoteState(
    val resp: RespScreenModel? = null,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class QuestionDescNoteViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getResp: GetResp
) : ViewModel() {

    private val id: Int = saveStateHandle[ID_ITEM_ARG]!!

    private val _uiState = MutableStateFlow(QuestionDescNoteState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun recover() = viewModelScope.launch {
        val result = getResp(id)
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val model = result.getOrNull()!!
        _uiState.update {
            it.copy(
                resp = model
            )
        }
    }


}