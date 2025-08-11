package br.com.usinasantafe.pci.presenter.view.note.questionresp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pci.domain.usecases.note.GetDescItem
import br.com.usinasantafe.pci.domain.usecases.note.SetRespItem
import br.com.usinasantafe.pci.presenter.Args.ID_ITEM_ARG
import br.com.usinasantafe.pci.presenter.Args.ID_PLANT_ARG
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class QuestionRespNoteState(
    val desc: String = "",
    val flagAccess: Boolean? = null,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class QuestionRespNoteViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getDescItem: GetDescItem,
    private val setRespItem: SetRespItem
) : ViewModel() {

    private val id: Int = saveStateHandle[ID_ITEM_ARG]!!
    private val idPlant: Int = saveStateHandle[ID_PLANT_ARG]!!

    private val _uiState = MutableStateFlow(QuestionRespNoteState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun recover() = viewModelScope.launch {
        val result = getDescItem(id)
        if(result.isFailure){
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
        val desc = result.getOrNull()!!
        _uiState.update {
            it.copy(
                desc = desc
            )
        }
    }

    fun setResp(option: OptionResp) = viewModelScope.launch {
        val flagAccess = option == OptionResp.ACCORDING
        if(flagAccess) {
            val result = setRespItem(
                id = id,
                idPlant = idPlant,
                option = option
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
                    )
                }
                return@launch
            }
        }
        _uiState.update {
            it.copy(
                flagAccess = flagAccess
            )
        }
    }

}