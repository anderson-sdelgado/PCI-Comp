package br.com.usinasantafe.pci.presenter.view.header.oslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pci.domain.usecases.flow.ListOSHeader
import br.com.usinasantafe.pci.domain.usecases.flow.SetIdOSHeader
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableOSByIdFactorySection
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTablePlantByIdFactorySection
import br.com.usinasantafe.pci.presenter.model.OSScreenModel
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

data class OSListHeaderState(
    val osList: List<OSScreenModel> = listOf(),
    val flagProgress: Boolean = true,
    val flagMsgUpdate: Boolean = false,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
)

fun ResultUpdateModel.resultUpdateToOSListHeader(classAndMethod: String): OSListHeaderState {
    val fail = if(failure.isNotEmpty()){
        val ret = "$classAndMethod -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    return OSListHeaderState(
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
class OSListHeaderViewModel @Inject constructor(
    private val updateTableOSByIdFactorySection: UpdateTableOSByIdFactorySection,
    private val updateTablePlantByIdFactorySection: UpdateTablePlantByIdFactorySection,
    private val listOSHeader: ListOSHeader,
    private val setIdOSHeader: SetIdOSHeader,
) : ViewModel() {

    private val _uiState = MutableStateFlow(OSListHeaderState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun recoverAndUpdateData() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                flagProgress = true,
                flagMsgUpdate = false
            )
        }
        updateAllDatabase().collect { stateUpdate ->
            _uiState.value = stateUpdate
        }
    }

    fun updateAllDatabase(): Flow<OSListHeaderState> = flow {
        val sizeAllUpdate = sizeUpdate(2f)
        var state = OSListHeaderState()
        updateTableOSByIdFactorySection(
            sizeAll = sizeAllUpdate,
            count = 1f
        ).collect {
            state = it.resultUpdateToOSListHeader(getClassAndMethod())
            emit(
                it.resultUpdateToOSListHeader(getClassAndMethod())
            )
        }
        if (state.flagFailure) return@flow
        updateTablePlantByIdFactorySection(
            sizeAll = sizeAllUpdate,
            count = 2f
        ).collect {
            state = it.resultUpdateToOSListHeader(getClassAndMethod())
            emit(
                it.resultUpdateToOSListHeader(getClassAndMethod())
            )
        }
        if (state.flagFailure) return@flow
        emit(
            OSListHeaderState(
                flagProgress = true,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }

    fun recoverList() = viewModelScope.launch {
        val result = listOSHeader()
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
        val osList = result.getOrNull()!!
        _uiState.update {
            it.copy(
                osList = osList,
                flagProgress = false,
            )
        }
    }

    fun setId(id: Int) = viewModelScope.launch {
        val result = setIdOSHeader(id)
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
        val flagAccess = result.getOrNull()!!
        _uiState.update {
            it.copy(
                flagAccess = flagAccess,
            )
        }
    }
}