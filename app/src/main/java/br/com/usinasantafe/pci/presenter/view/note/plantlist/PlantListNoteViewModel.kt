package br.com.usinasantafe.pci.presenter.view.note.plantlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pci.domain.usecases.flow.ListPlantNote
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableItemByIdOS
import br.com.usinasantafe.pci.presenter.model.PlantScreenModel
import br.com.usinasantafe.pci.presenter.model.ResultUpdateModel
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import br.com.usinasantafe.pci.utils.getClassAndMethod
import br.com.usinasantafe.pci.utils.sizeUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class PlantListNoteState(
    val plantList: List<PlantScreenModel> = listOf(),
    val flagProgress: Boolean = true,
    val flagMsgUpdate: Boolean = false,
    val flagMsgClose: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
)

fun ResultUpdateModel.resultUpdateToPlantListNote(classAndMethod: String): PlantListNoteState {
    val fail = if(failure.isNotEmpty()){
        val ret = "$classAndMethod -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    return PlantListNoteState(
        flagDialog = this.flagDialog,
        failure = fail,
        flagFailure = this.flagFailure,
        errors = this.errors,
        flagProgress = this.flagProgress,
        currentProgress = this.currentProgress,
        levelUpdate = this.levelUpdate,
        tableUpdate = this.tableUpdate,
    )
}

@HiltViewModel
class PlantListNoteViewModel @Inject constructor(
    private val updateTableItemByIdOS: UpdateTableItemByIdOS,
    private val listPlantNote: ListPlantNote,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlantListNoteState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setFlagMsgUpdate(status: Boolean) {
        _uiState.update {
            it.copy(flagMsgUpdate = status)
        }
    }

    fun setFlagMsgClose(status: Boolean) {
        _uiState.update {
            it.copy(flagMsgClose = status)
        }
    }

    fun recoverAndUpdateData() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                flagProgress = true,
                flagMsgUpdate = false
            )
        }
        updateItem().collect { stateUpdate ->
            _uiState.value = stateUpdate
        }
    }

    fun updateItem(): Flow<PlantListNoteState> = flow {
        val sizeAllUpdate = sizeUpdate(1f)
        var state = PlantListNoteState()
        updateTableItemByIdOS(
            sizeAll = sizeAllUpdate,
            count = 1f
        ).collect {
            state = it.resultUpdateToPlantListNote(getClassAndMethod())
            emit(
                it.resultUpdateToPlantListNote(getClassAndMethod())
            )
        }
        if (state.flagFailure) return@flow
        emit(
            PlantListNoteState(
                flagProgress = true,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
        return@flow
    }

    fun recoverList() = viewModelScope.launch {
        val result = listPlantNote()
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                )
            }
            return@launch
        }
        val plantList = result.getOrNull()!!
        _uiState.update {
            it.copy(
                plantList = plantList,
                flagProgress = false,
            )
        }
    }

}