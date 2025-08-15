package br.com.usinasantafe.pci.presenter.view.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pci.domain.usecases.flow.CloseHeaders
import br.com.usinasantafe.pci.domain.usecases.flow.CheckItemsOpen
import br.com.usinasantafe.pci.domain.usecases.flow.DeleteNote
import br.com.usinasantafe.pci.domain.usecases.flow.FinishItemsNote
import br.com.usinasantafe.pci.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class SplashState(
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val finishItemsNote: FinishItemsNote,
    private val checkItemsOpen: CheckItemsOpen,
    private val closeHeaders: CloseHeaders,
    private val deleteNote: DeleteNote,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun startApp() = viewModelScope.launch {
        val resultFinish = finishItemsNote()
        if(resultFinish.isFailure) {
            val error = resultFinish.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagAccess = false,
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val resultCheck = checkItemsOpen()
        if(resultCheck.isFailure) {
            val error = resultCheck.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagAccess = false,
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val resultCloseHeaders = closeHeaders()
        if(resultCloseHeaders.isFailure) {
            val error = resultCloseHeaders.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagAccess = false,
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val resultDeleteNote = deleteNote()
        if(resultDeleteNote.isFailure) {
            val error = resultDeleteNote.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagAccess = false,
                    flagDialog = true,
                    failure = failure
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