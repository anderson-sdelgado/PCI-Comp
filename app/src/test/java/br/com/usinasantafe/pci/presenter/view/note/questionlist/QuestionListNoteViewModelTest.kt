package br.com.usinasantafe.pci.presenter.view.note.questionlist

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pci.MainCoroutineRule
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.usecases.note.CheckItemNote
import br.com.usinasantafe.pci.domain.usecases.note.ListItemNote
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableComponent
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableService
import br.com.usinasantafe.pci.presenter.Args.ID_PLANT_ARG
import br.com.usinasantafe.pci.presenter.model.ItemScreenModel
import br.com.usinasantafe.pci.presenter.model.ResultUpdateModel
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import br.com.usinasantafe.pci.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class QuestionListNoteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkItemNote = mock<CheckItemNote>()
    private val updateTableComponent = mock<UpdateTableComponent>()
    private val updateTableService = mock<UpdateTableService>()
    private val listItemNote = mock<ListItemNote>()
    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
               ID_PLANT_ARG to 0
            )
        )
    ) = QuestionListNoteViewModel(
        savedStateHandle,
        checkItemNote = checkItemNote,
        updateTableComponent = updateTableComponent,
        updateTableService = updateTableService,
        listItemNote = listItemNote
    )

    private val qtdTable = 2f
    private val sizeAll = (qtdTable * 3) + 1f
    private var contWhenever = 0f
    private var contResult = 0f
    private var contUpdate = 0f

    @Test
    fun `checkAndUpdateData - Check return failure if have error in CheckItemNote`() =
        runTest {
            whenever(
                checkItemNote()
            ).thenReturn(
                resultFailure(
                    context = "CheckItemNote",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.checkAndUpdateData()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "QuestionListNoteViewModel.checkAndUpdateData -> CheckItemNote -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `checkAndUpdateData - Check return correct if CheckItemNote return true`() =
        runTest {
            whenever(
                checkItemNote()
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = createViewModel()
            viewModel.checkAndUpdateData()
            assertEquals(
                viewModel.uiState.value.flagProgress,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                false
            )
            assertEquals(
                viewModel.uiState.value.levelUpdate,
                LevelUpdate.FINISH_UPDATE_COMPLETED
            )
            assertEquals(
                viewModel.uiState.value.currentProgress,
                1f
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableComponent`() =
        runTest {
            val qtdBefore = 0f
            whenever(
                updateTableComponent(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_component",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanComponent -> java.lang.NullPointerException",
                    )
                )
            )
            val viewModel = createViewModel()
            val result = viewModel.updateComponentAndService().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                QuestionListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_component",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                QuestionListNoteState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "QuestionListNoteViewModel.updateComponentAndService -> CleanComponent -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTableService`() =
        runTest {
            val qtdBefore = 1f
            wheneverSuccessComponent()
            whenever(
                updateTableService(
                    sizeAll = sizeAll,
                    count = (qtdBefore + 1)
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_service",
                        currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanService -> java.lang.NullPointerException",
                    )
                )
            )
            val viewModel = createViewModel()
            val result = viewModel.updateComponentAndService().toList()
            assertEquals(
                result.count(),
                ((qtdBefore * 3) + 2).toInt()
            )
            checkResultUpdateComponent(result)
            assertEquals(
                result[(qtdBefore * 3).toInt()],
                QuestionListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_service",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                )
            )
            assertEquals(
                result[((qtdBefore * 3) + 1).toInt()],
                QuestionListNoteState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "QuestionListNoteViewModel.updateComponentAndService -> CleanService -> java.lang.NullPointerException",
                )
            )
        }

    @Test
    fun `update - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                checkItemNote()
            ).thenReturn(
                Result.success(false)
            )
            wheneverSuccessComponent()
            wheneverSuccessService()
            val viewModel = createViewModel()
            val result = viewModel.updateComponentAndService().toList()
            assertEquals(
                result.count(),
                ((qtdTable * 3) + 1).toInt()
            )
            checkResultUpdateComponent(result)
            checkResultUpdateService(result)
            assertEquals(
                result[(qtdTable * 3).toInt()],
                QuestionListNoteState(
                    flagDialog = false,
                    flagProgress = true,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
            viewModel.checkAndUpdateData()
            val state = viewModel.uiState.value
            assertEquals(
                state,
                QuestionListNoteState(
                    flagDialog = false,
                    flagProgress = true,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `recoverList - Check return failure if have error in ListItemNote`() =
        runTest {
            whenever(
                listItemNote(1)
            ).thenReturn(
                resultFailure(
                    context = "ListItemNote",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        ID_PLANT_ARG to 1
                    )
                )
            )
            viewModel.recoverList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "QuestionListNoteViewModel.recoverList -> ListItemNote -> java.lang.Exception"
            )
        }

    @Test
    fun `recoverList - Check return true if ListOSHeader execute successfully`() =
        runTest {
            whenever(
                listItemNote(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemScreenModel(
                            id = 1,
                            pos = 1,
                            descService = "Teste 1",
                            descComponent = "Teste 1",
                            option = null
                        ),
                        ItemScreenModel(
                            id = 2,
                            pos = 2,
                            descComponent = "Teste 2",
                            descService = "Teste 2",
                            option = null
                        ),
                        ItemScreenModel(
                            id = 3,
                            pos = 3,
                            descComponent = "Teste 3",
                            descService = "Teste 3",
                            option = null
                        ),
                    )
                )
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        ID_PLANT_ARG to 1
                    )
                )
            )
            viewModel.recoverList()
            val list = viewModel.uiState.value.itemList
            assertEquals(
                list.count(),
                3
            )
            val item1 = list[0]
            assertEquals(
                item1.id,
                1
            )
            assertEquals(
                item1.pos,
                1
            )
            assertEquals(
                item1.descService,
                "Teste 1"
            )
            assertEquals(
                item1.descComponent,
                "Teste 1"
            )
            assertEquals(
                item1.option,
                null
            )
            val item2 = list[1]
            assertEquals(
                item2.id,
                2
            )
            assertEquals(
                item2.pos,
                2
            )
            assertEquals(
                item2.descService,
                "Teste 2"
            )
            assertEquals(
                item2.descComponent,
                "Teste 2"
            )
            assertEquals(
                item2.option,
                null
            )
            val item3 = list[2]
            assertEquals(
                item3.id,
                3
            )
            assertEquals(
                item3.pos,
                3
            )
            assertEquals(
                item3.descService,
                "Teste 3"
            )
            assertEquals(
                item3.descComponent,
                "Teste 3"
            )
            assertEquals(
                item3.option,
                null
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
        }

    private fun wheneverSuccessComponent() =
        runTest {
            whenever(
                updateTableComponent(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_component",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_component",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_component",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateComponent(result: List<QuestionListNoteState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                QuestionListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_component",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                QuestionListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_component",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                QuestionListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_component",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessService() =
        runTest {
            whenever(
                updateTableService(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateService(result: List<QuestionListNoteState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                QuestionListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                QuestionListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                QuestionListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

}