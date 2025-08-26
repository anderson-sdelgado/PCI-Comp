package br.com.usinasantafe.pci.presenter.view.configuration.initial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pci.domain.usecases.config.GetStatusSend
import br.com.usinasantafe.pci.domain.usecases.flow.CheckAccessInitial
import br.com.usinasantafe.pci.utils.StatusSend
import br.com.usinasantafe.pci.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class InitialMenuState(
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagFailure: Boolean = false,
    val statusSend: StatusSend = StatusSend.STARTED
)

@HiltViewModel
class InitialMenuViewModel @Inject constructor(
    private val checkAccessInitial: CheckAccessInitial,
    private val getStatusSend: GetStatusSend
) : ViewModel() {

    private val _uiState = MutableStateFlow(InitialMenuState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    init {
        viewModelScope.launch {
            getStatusSend()
                .collect { result ->
                    if (result.isFailure) {
                        val error = result.exceptionOrNull()!!
                        val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause}"
                        _uiState.update { it.copy(failure = failure) }
                    } else {
                        _uiState.update { it.copy(statusSend = result.getOrNull()!!) }
                    }
                }
        }
    }

    fun onCheckAccess() {
        viewModelScope.launch {
            val resultCheck = checkAccessInitial()
            if(resultCheck.isFailure){
                val error = resultCheck.exceptionOrNull()!!
                val failure =
                    "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagAccess = false,
                        flagFailure = true,
                        failure = failure
                    )
                }
                return@launch
            }
            val statusAccess = resultCheck.getOrNull()!!
            _uiState.update {
                it.copy(
                    flagDialog = !statusAccess,
                    flagAccess = statusAccess,
                    flagFailure = false,
                )
            }
        }
    }
}