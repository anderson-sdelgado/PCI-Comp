package br.com.usinasantafe.pci.presenter.view.note.plantlist

import br.com.usinasantafe.pci.MainCoroutineRule
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.usecases.note.ListPlantNote
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableItemByIdOS
import br.com.usinasantafe.pci.presenter.model.PlantScreenModel
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
class PlantListNoteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val updateTableItemByIdOS = mock<UpdateTableItemByIdOS>()
    private val listPlantNote = mock<ListPlantNote>()
    private val viewModel = PlantListNoteViewModel(
        updateTableItemByIdOS = updateTableItemByIdOS,
        listPlantNote = listPlantNote
    )

    @Test
    fun `update - Check return failure if have error in UpdateTableItemByIdOS`() =
        runTest {
            whenever(
                updateTableItemByIdOS(
                    sizeAll = 4f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item",
                        currentProgress = percentage(1f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ICleanItem -> java.lang.NullPointerException",
                        currentProgress = 1f,
                        levelUpdate = null,
                    ),
                )
            )
            val result = viewModel.updateItem().toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                result[0],
                PlantListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item",
                    currentProgress = percentage(1f, 7f)
                )
            )
            assertEquals(
                result[1],
                PlantListNoteState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "PlantListNoteViewModel.updateItem -> ICleanItem -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
        }

    @Test
    fun `update - Check return success if update execute successfully`() =
        runTest {
            whenever(
                updateTableItemByIdOS(
                    sizeAll = 4f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item",
                        currentProgress = percentage(1f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_item",
                        currentProgress = percentage(2f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_item",
                        currentProgress = percentage(3f, 7f)
                    ),
                )
            )

            val result = viewModel.updateItem().toList()
            assertEquals(
                result.count(),
                4
            )
            assertEquals(
                result[0],
                PlantListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item",
                    currentProgress = percentage(1f, 7f)
                )
            )
            assertEquals(
                result[1],
                PlantListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item",
                    currentProgress = percentage(2f, 7f)
                )
            )
            assertEquals(
                result[2],
                PlantListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item",
                    currentProgress = percentage(3f, 7f)
                )
            )
            assertEquals(
                result[3],
                PlantListNoteState(
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
                listPlantNote()
            ).thenReturn(
                resultFailure(
                    context = "ListPlantNote",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.recoverList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "PlantListNoteViewModel.recoverList -> ListPlantNote -> java.lang.Exception"
            )
        }

    @Test
    fun `recoverList - Check return true if ListOSHeader execute successfully`() =
        runTest {
            whenever(
                listPlantNote()
            ).thenReturn(
                Result.success(
                    listOf(
                        PlantScreenModel(
                            id = 1,
                            cod = "01",
                            desc = "Plant 1"
                        ),
                        PlantScreenModel(
                            id = 2,
                            cod = "02",
                            desc = "Plant 2"
                        )
                    )
                )
            )
            viewModel.recoverList()
            val list = viewModel.uiState.value.plantList
            assertEquals(
                list.count(),
                2
            )
            val item1 = list[0]
            assertEquals(
                item1.id,
                1
            )
            assertEquals(
                item1.cod,
                "01"
            )
            assertEquals(
                item1.desc,
                "Plant 1"
            )
            val item2 = list[1]
            assertEquals(
                item2.id,
                2
            )
            assertEquals(
                item2.cod,
                "02"
            )
            assertEquals(
                item2.desc,
                "Plant 2"
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
        }
}