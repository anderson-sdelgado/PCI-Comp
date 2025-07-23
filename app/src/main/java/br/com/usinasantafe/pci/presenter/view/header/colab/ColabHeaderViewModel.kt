package br.com.usinasantafe.pci.presenter.view.header.colab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pci.domain.usecases.header.CheckAndSetRegColabHeader
import br.com.usinasantafe.pci.presenter.theme.addTextField
import br.com.usinasantafe.pci.presenter.theme.clearTextField
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.TypeButton
import br.com.usinasantafe.pci.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ColabHeaderState(
    val regColab: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagProgress: Boolean = false,
)

@HiltViewModel
class ColabHeaderViewModel @Inject constructor(
    private val checkAndSetRegColabHeader: CheckAndSetRegColabHeader
) : ViewModel() {

    private val _uiState = MutableStateFlow(ColabHeaderState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when (typeButton) {
            TypeButton.NUMERIC -> {
                val regColab = addTextField(uiState.value.regColab, text)
                _uiState.update {
                    it.copy(regColab = regColab)
                }
            }
            TypeButton.CLEAN -> {
                val regColab = clearTextField(uiState.value.regColab)
                _uiState.update {
                    it.copy(regColab = regColab)
                }
            }
            TypeButton.OK -> {
                if (uiState.value.regColab.isEmpty()) {
                    val failure = "ColabHeaderViewModel.setTextField.OK -> Field Empty!"
                    Timber.e(failure)
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            failure = failure,
                            errors = Errors.FIELD_EMPTY,
                            flagAccess = false
                        )
                    }
                    return
                }
                checkAndSet()
            }
            TypeButton.UPDATE -> {}
        }
    }

    private fun checkAndSet() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                flagProgress = true,
            )
        }
        val result = checkAndSetRegColabHeader(uiState.value.regColab)
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
                    flagProgress = false,
                )
            }
            return@launch
        }
        val check = result.getOrNull()!!
        _uiState.update {
            it.copy(
                flagDialog = !check,
                errors = Errors.INVALID,
                flagProgress = false,
                flagAccess = check
            )
        }
    }
}