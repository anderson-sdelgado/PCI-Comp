package br.com.usinasantafe.pci.presenter.view.note.plantlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pci.presenter.model.PlantScreenModel
import br.com.usinasantafe.pci.presenter.model.ResultUpdateModel
import br.com.usinasantafe.pci.presenter.view.header.oslist.OSListHeaderState
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class PlantListNoteState(
    val plantList: List<PlantScreenModel> = listOf(),
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

fun ResultUpdateModel.resultUpdateToOSListHeader(classAndMethod: String): PlantListNoteState {
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
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlantListNoteState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }



}