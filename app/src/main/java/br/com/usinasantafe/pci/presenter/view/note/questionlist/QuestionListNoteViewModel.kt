package br.com.usinasantafe.pci.presenter.view.note.questionlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pci.domain.usecases.note.CheckItemNote
import br.com.usinasantafe.pci.domain.usecases.note.CheckItemsOpen
import br.com.usinasantafe.pci.domain.usecases.note.CloseItemsNote
import br.com.usinasantafe.pci.domain.usecases.note.ListItemNote
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableComponent
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableService
import br.com.usinasantafe.pci.presenter.Args.ID_PLANT_ARG
import br.com.usinasantafe.pci.presenter.model.ItemScreenModel
import br.com.usinasantafe.pci.presenter.model.ResultUpdateModel
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import br.com.usinasantafe.pci.utils.StatusPlant
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

data class QuestionListNoteState(
    val itemList: List<ItemScreenModel> = listOf(),
    val idSelection: Int = 0,
    val flagDialogCheck: Boolean = false,
    val statusPlant: StatusPlant = StatusPlant.OPEN,
    val flagProgress: Boolean = true,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
)

fun ResultUpdateModel.resultUpdateToQuestionListNote(classAndMethod: String): QuestionListNoteState {
    val fail = if(failure.isNotEmpty()){
        val ret = "$classAndMethod -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    return QuestionListNoteState(
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
class QuestionListNoteViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val checkItemNote: CheckItemNote,
    private val updateTableComponent: UpdateTableComponent,
    private val updateTableService: UpdateTableService,
    private val listItemNote: ListItemNote,
    private val closeItemsNote: CloseItemsNote,
    private val checkItemsOpen: CheckItemsOpen,
) : ViewModel() {

    private val idPlant: Int = saveStateHandle[ID_PLANT_ARG]!!

    private val _uiState = MutableStateFlow(QuestionListNoteState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setDialogCheck(flagDialogCheck: Boolean) {
        _uiState.update {
            it.copy(flagDialogCheck = flagDialogCheck)
        }
    }

    fun closeItem() = viewModelScope.launch {
        val resultClose = closeItemsNote(idPlant)
        if (resultClose.isFailure) {
            val error = resultClose.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                    flagDialogCheck = false
                )
            }
            return@launch
        }
        val resultCheck = checkItemsOpen(idPlant)
        if (resultCheck.isFailure) {
            val error = resultCheck.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                    flagDialogCheck = false
                )
            }
            return@launch
        }
        val check = resultCheck.getOrNull()!!
        _uiState.update {
            it.copy(
                statusPlant = check,
                flagDialogCheck = false
            )
        }
    }

    fun checkAndUpdateData() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                flagProgress = true,
                levelUpdate = LevelUpdate.CHECK,
            )
        }
        val result = checkItemNote()
        if(result.isFailure){
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagProgress = true,
                    flagDialog = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                )
            }
            return@launch
        }
        val check = result.getOrNull()!!
        if(check){
            _uiState.update {
                it.copy(
                    flagProgress = true,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            }
            return@launch
        }
        updateComponentAndService().collect { stateUpdate ->
            _uiState.value = stateUpdate
        }
    }

    fun updateComponentAndService(): Flow<QuestionListNoteState> = flow {
        val sizeAllUpdate = sizeUpdate(2f)
        var state = QuestionListNoteState()
        val classAndMethod = getClassAndMethod()
        updateTableComponent(
            sizeAll = sizeAllUpdate,
            count = 1f
        ).collect {
            state = it.resultUpdateToQuestionListNote(classAndMethod)
            emit(
                it.resultUpdateToQuestionListNote(classAndMethod)
            )
        }
        if (state.flagFailure) return@flow
        updateTableService(
            sizeAll = sizeAllUpdate,
            count = 2f
        ).collect {
            state = it.resultUpdateToQuestionListNote(classAndMethod)
            emit(
                it.resultUpdateToQuestionListNote(classAndMethod)
            )
        }
        if (state.flagFailure) return@flow
        emit(
            QuestionListNoteState(
                flagProgress = true,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }

    fun recoverList() = viewModelScope.launch {
        val result = listItemNote(idPlant)
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
        val itemList = result.getOrNull()!!
        _uiState.update {
            it.copy(
                itemList = itemList,
                flagProgress = false,
            )
        }
    }

}