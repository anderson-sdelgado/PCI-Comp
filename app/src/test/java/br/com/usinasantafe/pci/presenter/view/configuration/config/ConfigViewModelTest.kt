package br.com.usinasantafe.pci.presenter.view.configuration.config

import br.com.usinasantafe.pci.MainCoroutineRule
import br.com.usinasantafe.pci.domain.entities.variable.Config
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.pci.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.pci.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.pci.domain.usecases.config.SetFinishUpdateAllTable
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.pci.presenter.model.ConfigModel
import br.com.usinasantafe.pci.presenter.model.ResultUpdateModel
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import br.com.usinasantafe.pci.utils.QTD_TABLE
import br.com.usinasantafe.pci.utils.percentage
import br.com.usinasantafe.pci.utils.sizeUpdate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ConfigViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getConfigInternal = mock<GetConfigInternal>()
    private val sendDataConfig = mock<SendDataConfig>()
    private val saveDataConfig = mock<SaveDataConfig>()
    private val updateTableColab = mock<UpdateTableColab>()
    private val setFinishUpdateAllTable = mock<SetFinishUpdateAllTable>()
    private var contUpdate = 0f
    private var contWhenever = 0f
    private var contResult = 0f

    private val viewModel = ConfigViewModel(
        getConfigInternal = getConfigInternal,
        sendDataConfig = sendDataConfig,
        saveDataConfig = saveDataConfig,
        updateTableColab = updateTableColab,
        setFinishUpdateAllTable = setFinishUpdateAllTable
    )

    @Test
    fun `returnDataConfig - Check return failure if have error in GetConfigInternal`() =
        runTest {
            whenever(
                getConfigInternal()
            ).thenReturn(
                resultFailure(
                    "GetConfigInternal",
                    "-",
                    Exception()
                )
            )
            viewModel.returnDataConfig()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                true
            )
            assertEquals(
                uiState.flagFailure,
                true
            )
            assertEquals(
                uiState.failure,
                "ConfigViewModel.returnDataConfig -> GetConfigInternal -> java.lang.Exception"
            )
        }

    @Test
    fun `returnDataConfig - Check return correct if function execute successfully and config table internal is empty`() =
        runTest {
            whenever(
                getConfigInternal()
            ).thenReturn(
                Result.success(
                    null
                )
            )
            viewModel.returnDataConfig()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                false
            )
            assertEquals(
                uiState.flagFailure,
                false
            )
            assertEquals(
                uiState.failure,
                ""
            )
            assertEquals(
                uiState.number,
                ""
            )
            assertEquals(
                uiState.password,
                ""
            )
        }

    @Test
    fun `returnDataConfig - Check return correct if function execute successfully and config table internal is data`() =
        runTest {
            whenever(
                getConfigInternal()
            ).thenReturn(
                Result.success(
                    ConfigModel(
                        number = "16997417840",
                        password = "12345"
                    )
                )
            )
            viewModel.returnDataConfig()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                false
            )
            assertEquals(
                uiState.flagFailure,
                false
            )
            assertEquals(
                uiState.failure,
                ""
            )
            assertEquals(
                uiState.number,
                "16997417840"
            )
            assertEquals(
                uiState.password,
                "12345"
            )
        }

    @Test
    fun `onSaveAndUpdate - Check return failure if number or password is empty`() =
        runTest {
            viewModel.onSaveAndUpdate()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                true
            )
            assertEquals(
                uiState.flagFailure,
                true
            )
            assertEquals(
                uiState.errors,
                Errors.FIELD_EMPTY
            )
        }

    @Test
    fun `onSaveAndUpdate - Check return failure if have error in SendDataConfig`() =
        runTest {
            whenever(
                sendDataConfig(
                    number = "16997417840",
                    password = "12345",
                    version = "1.00"
                )
            ).thenReturn(
                resultFailure(
                    "SendDataConfig",
                    "-",
                    Exception()
                )
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onVersionChanged("1.00")
            val result = viewModel.token().toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                result[0],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.GET_TOKEN,
                    currentProgress = percentage(1f, 3f)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.token -> SendDataConfig -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
            viewModel.onSaveAndUpdate()
            assertEquals(
                viewModel.uiState.value.failure,
                "ConfigViewModel.token -> SendDataConfig -> java.lang.Exception"
            )
        }

    @Test
    fun `onSaveAndUpdate - Check return failure if have error in SaveDataConfig`() =
        runTest {
            whenever(
                sendDataConfig(
                    number = "16997417840",
                    password = "12345",
                    version = "1.00"
                )
            ).thenReturn(
                Result.success(
                    Config(
                        idBD = 1
                    )
                )
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    version = "1.00",
                    idBD = 1
                )
            ).thenReturn(
                resultFailure(
                    "SaveDataConfig",
                    "-",
                    Exception()
                )
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onVersionChanged("1.00")
            val result = viewModel.token().toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                result[0],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.GET_TOKEN,
                    currentProgress = percentage(1f, 3f)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE_TOKEN,
                    currentProgress = percentage(2f, 3f),
                )
            )
            assertEquals(
                result[2],
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.token -> SaveDataConfig -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
            viewModel.onSaveAndUpdate()
            assertEquals(
                viewModel.uiState.value.failure,
                "ConfigViewModel.token -> SaveDataConfig -> java.lang.Exception"
            )
        }

    @Test
    fun `onSaveAndUpdate - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                sendDataConfig(
                    number = "16997417840",
                    password = "12345",
                    version = "1.00"
                )
            ).thenReturn(
                Result.success(
                    Config(
                        idBD = 1
                    )
                )
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    version = "1.00",
                    idBD = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onVersionChanged("1.00")
            val result = viewModel.token().toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                result[0],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.GET_TOKEN,
                    currentProgress = percentage(1f, 3f)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE_TOKEN,
                    currentProgress = percentage(2f, 3f),
                )
            )
            assertEquals(
                result[2],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_INITIAL,
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableColab`() =
        runTest {
            val qtdBefore = 0f
            val sizeAll = sizeUpdate(QTD_TABLE)
            whenever(
                updateTableColab(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ICleanColab -> java.lang.NullPointerException",
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ConfigViewModel.updateAllDatabase -> ICleanColab -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in SetCheckUpdateAllTable`() =
        runTest {
            whenever(
                sendDataConfig(
                    number = "16997417840",
                    password = "12345",
                    version = "1.00"
                )
            ).thenReturn(
                Result.success(
                    Config(
                        idBD = 1
                    )
                )
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    version = "1.00",
                    idBD = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            wheneverSuccessColab()
            whenever(
                setFinishUpdateAllTable()
            ).thenReturn(
                resultFailure(
                    "SetFinishCheckUpdateAllTable",
                    "-",
                    Exception()
                )
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onVersionChanged("1.00")
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((QTD_TABLE * 3) + 1).toInt()
            )
            checkResultUpdateColab(result)
            assertEquals(
                result[(QTD_TABLE * 3).toInt()],
                ConfigState(
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    flagProgress = true,
                    currentProgress = 1f,
                    failure = "ConfigViewModel.updateAllDatabase -> SetFinishCheckUpdateAllTable -> java.lang.Exception",
                )
            )
            viewModel.onSaveAndUpdate()
            val configState = viewModel.uiState.value
            assertEquals(
                configState,
                ConfigState(
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    flagProgress = true,
                    currentProgress = 1f,
                    failure = "ConfigViewModel.updateAllDatabase -> SetFinishCheckUpdateAllTable -> java.lang.Exception",
                )
            )
        }

    @Test
    fun `update - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                sendDataConfig(
                    number = "16997417840",
                    password = "12345",
                    version = "1.00"
                )
            ).thenReturn(
                Result.success(
                    Config(
                        idBD = 1
                    )
                )
            )
            whenever(
                saveDataConfig(
                    number = "16997417840",
                    password = "12345",
                    version = "1.00",
                    idBD = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            wheneverSuccessColab()
            whenever(
                setFinishUpdateAllTable()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.onNumberChanged("16997417840")
            viewModel.onPasswordChanged("12345")
            viewModel.onVersionChanged("1.00")
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                ((QTD_TABLE * 3) + 1).toInt()
            )
            checkResultUpdateColab(result)
            assertEquals(
                result[(QTD_TABLE * 3).toInt()],
                ConfigState(
                    flagDialog = true,
                    flagProgress = true,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
            viewModel.onSaveAndUpdate()
            val configState = viewModel.uiState.value
            assertEquals(
                configState,
                ConfigState(
                    flagDialog = true,
                    flagProgress = true,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
        }

    private fun wheneverSuccessColab() =
        runTest {
            val sizeAll = sizeUpdate(QTD_TABLE)
            whenever(
                updateTableColab(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateColab(result: List<ConfigState>) =
        runTest {
            val sizeAll = sizeUpdate(QTD_TABLE)
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }
}