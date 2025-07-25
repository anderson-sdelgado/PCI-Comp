package br.com.usinasantafe.pci.domain.usecases.update

import br.com.usinasantafe.pci.domain.entities.stable.OS
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.OSRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.domain.usecases.common.GetToken
import br.com.usinasantafe.pci.presenter.model.ResultUpdateModel
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import br.com.usinasantafe.pci.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.Int

class IUpdateTableOSByIdFactorySectionTest {

    private val getToken = mock<GetToken>()
    private val checkListRepository = mock<CheckListRepository>()
    private val osRepository = mock<OSRepository>()
    private val usecase = IUpdateTableOSByIdFactorySection(
        getToken = getToken,
        checkListRepository = checkListRepository,
        osRepository = osRepository
    )

    @Test
    fun `Check return failure if have error in GetToken`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    "GetToken",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_os",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableOSByIdFactorySection -> GetToken -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository getIdFactorySectionHeaderOpen`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                checkListRepository.getIdFactorySectionHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.getIdFactorySectionHeaderOpen",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_os",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableOSByIdFactorySection -> ICheckListRepository.getIdFactorySectionHeaderOpen -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository listByIdFactorySection`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                checkListRepository.getIdFactorySectionHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                osRepository.listByIdFactorySection(
                    token = "token",
                    idFactorySection = 1
                )
            ).thenReturn(
                resultFailure(
                    "IOSRepository.listByIdFactorySection",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_os",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableOSByIdFactorySection -> IOSRepository.listByIdFactorySection -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository deleteAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                checkListRepository.getIdFactorySectionHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                osRepository.listByIdFactorySection(
                    token = "token",
                    idFactorySection = 1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OS(
                            idOS = 1,
                            nroOS = 1,
                            idPlantOS = 1,
                            qtdDayOS = 1,
                            descPeriodOS = "DIÁRIO"
                        )
                    )
                )
            )
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IOSRepository.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_os",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_os",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableOSByIdFactorySection -> IOSRepository.deleteAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository addAll`() =
        runTest {
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 1,
                    idPlantOS = 1,
                    qtdDayOS = 1,
                    descPeriodOS = "DIÁRIO"
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                checkListRepository.getIdFactorySectionHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                osRepository.listByIdFactorySection(
                    token = "token",
                    idFactorySection = 1
                )
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.addAll(entityList)
            ).thenReturn(
                resultFailure(
                    "IOSRepository.addAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                4
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_os",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_os",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_os",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableOSByIdFactorySection -> IOSRepository.addAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return correct if process execute successfully`() =
        runTest {
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 1,
                    idPlantOS = 1,
                    qtdDayOS = 1,
                    descPeriodOS = "DIÁRIO"
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                checkListRepository.getIdFactorySectionHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                osRepository.listByIdFactorySection(
                    token = "token",
                    idFactorySection = 1
                )
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osRepository.addAll(entityList)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_os",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_os",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_os",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
        }
}