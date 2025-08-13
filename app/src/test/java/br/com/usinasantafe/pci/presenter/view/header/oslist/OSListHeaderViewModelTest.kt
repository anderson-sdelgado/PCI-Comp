package br.com.usinasantafe.pci.presenter.view.header.oslist

import br.com.usinasantafe.pci.MainCoroutineRule
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.usecases.header.ListOSHeader
import br.com.usinasantafe.pci.domain.usecases.header.SetIdOSHeader
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableOSByIdFactorySection
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTablePlantByIdFactorySection
import br.com.usinasantafe.pci.presenter.model.OSScreenModel
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
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class OSListHeaderViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val updateTableOSByIdFactorySection = mock<UpdateTableOSByIdFactorySection>()
    private val updateTablePlantByIdFactorySection = mock<UpdateTablePlantByIdFactorySection>()
    private val listOSHeader = mock<ListOSHeader>()
    private val setIdOSHeader = mock<SetIdOSHeader>()

    private val viewModel = OSListHeaderViewModel(
        updateTableOSByIdFactorySection = updateTableOSByIdFactorySection,
        updateTablePlantByIdFactorySection = updateTablePlantByIdFactorySection,
        listOSHeader = listOSHeader,
        setIdOSHeader = setIdOSHeader
    )

    @Test
    fun `update - Check return failure if have error in UpdateTableOSByIdFactorySection`() =
        runTest {
            whenever(
                updateTableOSByIdFactorySection(
                    sizeAll = 7f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(1f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ICleanOSByIdFactorySection -> java.lang.NullPointerException",
                        currentProgress = 1f,
                        levelUpdate = null,
                    ),
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                result[0],
                OSListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_os",
                    currentProgress = percentage(1f, 7f)
                )
            )
            assertEquals(
                result[1],
                OSListHeaderState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "OSListHeaderViewModel.updateAllDatabase -> ICleanOSByIdFactorySection -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in UpdateTablePlantByIdFactorySection`() =
        runTest {
            whenever(
                updateTableOSByIdFactorySection(
                    sizeAll = 7f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(1f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(2f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(3f, 7f)
                    ),
                )
            )
            whenever(
                updateTablePlantByIdFactorySection(
                    sizeAll = 7f,
                    count = 2f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_plant",
                        currentProgress = percentage(4f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ICleanPlantByIdFactorySection -> java.lang.NullPointerException",
                        currentProgress = 1f,
                        levelUpdate = null,
                    ),
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                5
            )
            assertEquals(
                result[0],
                OSListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_os",
                    currentProgress = percentage(1f, 7f)
                )
            )
            assertEquals(
                result[1],
                OSListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_os",
                    currentProgress = percentage(2f, 7f)
                )
            )
            assertEquals(
                result[2],
                OSListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_os",
                    currentProgress = percentage(3f, 7f)
                )
            )
            assertEquals(
                result[3],
                OSListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_plant",
                    currentProgress = percentage(4f, 7f)
                )
            )
            assertEquals(
                result[4],
                OSListHeaderState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "OSListHeaderViewModel.updateAllDatabase -> ICleanPlantByIdFactorySection -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
        }

    @Test
    fun `update - Check return success if update execute successfully`() =
        runTest {
            whenever(
                updateTableOSByIdFactorySection(
                    sizeAll = 7f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(1f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(2f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_os",
                        currentProgress = percentage(3f, 7f)
                    ),
                )
            )
            whenever(
                updateTablePlantByIdFactorySection(
                    sizeAll = 7f,
                    count = 2f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_plant",
                        currentProgress = percentage(4f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_plant",
                        currentProgress = percentage(5f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_plant",
                        currentProgress = percentage(6f, 7f)
                    ),
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                7
            )
            assertEquals(
                result[0],
                OSListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_os",
                    currentProgress = percentage(1f, 7f)
                )
            )
            assertEquals(
                result[1],
                OSListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_os",
                    currentProgress = percentage(2f, 7f)
                )
            )
            assertEquals(
                result[2],
                OSListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_os",
                    currentProgress = percentage(3f, 7f)
                )
            )
            assertEquals(
                result[3],
                OSListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_plant",
                    currentProgress = percentage(4f, 7f)
                )
            )
            assertEquals(
                result[4],
                OSListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_plant",
                    currentProgress = percentage(5f, 7f)
                )
            )
            assertEquals(
                result[5],
                OSListHeaderState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_plant",
                    currentProgress = percentage(6f, 7f)
                )
            )
            assertEquals(
                result[6],
                OSListHeaderState(
                    flagProgress = true,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `recoverList - Check return failure if have error in ListOSHeader`() =
        runTest {
            whenever(
                listOSHeader()
            ).thenReturn(
                resultFailure(
                    context = "ListOSHeader",
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
                "OSListHeaderViewModel.recoverList -> ListOSHeader -> java.lang.Exception"
            )
        }

    @Test
    fun `recoverList - Check return true if ListOSHeader execute successfully`() =
        runTest {
            whenever(
                listOSHeader()
            ).thenReturn(
                Result.success(
                    listOf(
                        OSScreenModel(
                            idOS = 1,
                            period = "DIARIO",
                            nroOS = 210000,
                            codPlant = "01.0.002.003",
                            descPlant = "PLANT 1",
                            status = false
                        ),
                        OSScreenModel(
                            idOS = 2,
                            period = "SEMANAL",
                            nroOS = 220000,
                            codPlant = "01.0.002.003",
                            descPlant = "PLANT 2",
                            status = true
                        )
                    )
                )
            )
            viewModel.recoverList()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            val list = viewModel.uiState.value.osList
            assertEquals(
                list.count(),
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idOS,
                1
            )
            assertEquals(
                entity1.period,
                "DIARIO"
            )
            assertEquals(
                entity1.nroOS,
                210000
            )
            assertEquals(
                entity1.codPlant,
                "01.0.002.003"
            )
            assertEquals(
                entity1.descPlant,
                "PLANT 1"
            )
            assertEquals(
                entity1.status,
                false
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idOS,
                2
            )
            assertEquals(
                entity2.period,
                "SEMANAL"
            )
            assertEquals(
                entity2.nroOS,
                220000
            )
            assertEquals(
                entity2.codPlant,
                "01.0.002.003"
            )
            assertEquals(
                entity2.descPlant,
                "PLANT 2"
            )
            assertEquals(
                entity2.status,
                true
            )
        }

    @Test
    fun `setId - Check return failure if have error in SetIdOSHeader`() =
        runTest {
            whenever(
                setIdOSHeader(1)
            ).thenReturn(
                resultFailure(
                    context = "SetIdOSHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setId(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "OSListHeaderViewModel.setId -> SetIdOSHeader -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setId - Check return true if SetIdOSHeader execute successfully`() =
        runTest {
            whenever(
                setIdOSHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setId(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
        }
}