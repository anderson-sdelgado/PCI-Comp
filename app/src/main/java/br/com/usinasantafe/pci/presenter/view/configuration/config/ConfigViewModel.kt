package br.com.usinasantafe.pci.presenter.view.configuration.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pci.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.pci.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.pci.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.pci.domain.usecases.config.SetFinishUpdateAllTable
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.pci.presenter.model.ResultUpdateModel
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import br.com.usinasantafe.pci.utils.QTD_TABLE
import br.com.usinasantafe.pci.utils.getClassAndMethod
import br.com.usinasantafe.pci.utils.percentage
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

data class ConfigState(
    val number: String = "",
    val password: String = "",
    val version: String = "",
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagProgress: Boolean = false,
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
)

fun ResultUpdateModel.resultUpdateToConfig(classAndMethod: String): ConfigState {
    val fail = if(failure.isNotEmpty()){
        val ret = "$classAndMethod -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    return ConfigState(
        flagDialog = this.flagDialog,
        flagFailure = this.flagFailure,
        errors = this.errors,
        failure = fail,
        flagProgress = this.flagProgress,
        currentProgress = this.currentProgress,
        levelUpdate = this.levelUpdate,
        tableUpdate = this.tableUpdate,
    )
}

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val getConfigInternal: GetConfigInternal,
    private val sendDataConfig: SendDataConfig,
    private val saveDataConfig: SaveDataConfig,
    private val updateTableColab: UpdateTableColab,
    private val setFinishUpdateAllTable: SetFinishUpdateAllTable
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfigState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onNumberChanged(number: String) {
        _uiState.update {
            it.copy(number = number)
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun onVersionChanged(version: String) {
        _uiState.update {
            it.copy(version = version)
        }
    }

    fun returnDataConfig() = viewModelScope.launch {
        val resultGetConfig = getConfigInternal()
        if (resultGetConfig.isFailure) {
            val error = resultGetConfig.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    errors = Errors.EXCEPTION,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val configModel = resultGetConfig.getOrNull()
        configModel?.let { config ->
            _uiState.update {
                it.copy(
                    number = config.number,
                    password = config.password,
                )
            }
        }
    }

    fun onSaveAndUpdate() {
        if (
            _uiState.value.number.isEmpty() ||
            _uiState.value.password.isEmpty()
        ){
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.FIELD_EMPTY,
                    flagFailure = true,
                )
            }
            return
        }
        viewModelScope.launch {
            token().collect { configStateToken ->
                _uiState.value = configStateToken
                if (
                    (!configStateToken.flagFailure) &&
                    (configStateToken.currentProgress == 1f)
                ) {
                    updateAllDatabase().collect { configStateUpdate ->
                        _uiState.value = configStateUpdate
                    }
                }
            }
        }
    }

    fun token(): Flow<ConfigState> = flow {
        val sizeToken = 3f
        val number = uiState.value.number
        val password = uiState.value.password
        val version = uiState.value.version
        emit(
            ConfigState(
                flagProgress = true,
                levelUpdate = LevelUpdate.GET_TOKEN,
                currentProgress = percentage(1f, sizeToken)
            )
        )
        val resultSendDataConfig = sendDataConfig(
            number = number,
            password = password,
            version = version
        )
        if (resultSendDataConfig.isFailure) {
            val error = resultSendDataConfig.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            emit(
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                levelUpdate = LevelUpdate.SAVE_TOKEN,
                currentProgress = percentage(2f, sizeToken),
            )
        )
        val config = resultSendDataConfig.getOrNull()!!
        val resultSave = saveDataConfig(
            number = number,
            password = password,
            version = version,
            idBD = config.idBD!!,
        )
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            emit(
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                currentProgress = 1f,
                levelUpdate = LevelUpdate.FINISH_UPDATE_INITIAL,
            )
        )
    }

    fun updateAllDatabase(): Flow<ConfigState> = flow {
        var pos = 0f
        val sizeAllUpdate = sizeUpdate(QTD_TABLE)
        var configState = ConfigState()
        val classAndMethod = getClassAndMethod()
        updateTableColab(
            sizeAll = sizeAllUpdate,
            count = ++pos
        ).collect {
            configState = it.resultUpdateToConfig(classAndMethod)
            emit(
                it.resultUpdateToConfig(classAndMethod)
            )
        }
        if (configState.flagFailure) return@flow
        val result = setFinishUpdateAllTable()
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            emit(
                ConfigState(
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    failure = failure,
                    flagProgress = true,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagDialog = true,
                flagProgress = true,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }
}