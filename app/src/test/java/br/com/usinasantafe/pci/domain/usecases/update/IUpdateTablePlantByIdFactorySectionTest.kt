package br.com.usinasantafe.pci.domain.usecases.update

import br.com.usinasantafe.pci.domain.entities.stable.Plant
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.PlantRepository
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

class IUpdateTablePlantByIdFactorySectionTest {

    private val getToken = mock<GetToken>()
    private val checkListRepository = mock<CheckListRepository>()
    private val plantRepository = mock<PlantRepository>()
    private val usecase = IUpdateTablePlantByIdFactorySection(
        getToken = getToken,
        checkListRepository = checkListRepository,
        plantRepository = plantRepository
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
                    tableUpdate = "tb_plant",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTablePlantByIdFactorySection -> GetToken -> java.lang.Exception",
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
                    tableUpdate = "tb_plant",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTablePlantByIdFactorySection -> ICheckListRepository.getIdFactorySectionHeaderOpen -> java.lang.Exception",
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
                plantRepository.listByIdFactorySection(
                    token = "token",
                    idFactorySection = 1
                )
            ).thenReturn(
                resultFailure(
                    "IPlantRepository.listByIdFactorySection",
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
                    tableUpdate = "tb_plant",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTablePlantByIdFactorySection -> IPlantRepository.listByIdFactorySection -> java.lang.Exception",
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
                plantRepository.listByIdFactorySection(
                    token = "token",
                    idFactorySection = 1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Plant(
                            idPlant = 1,
                            codPlant = "01",
                            descPlant = "PLANT 1",
                            idFactorySectionPlant = 1
                        )
                    )
                )
            )
            whenever(
                plantRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IPlantRepository.deleteAll",
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
                    tableUpdate = "tb_plant",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_plant",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTablePlantByIdFactorySection -> IPlantRepository.deleteAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository addAll`() =
        runTest {
            val entityList = listOf(
                Plant(
                    idPlant = 1,
                    codPlant = "01",
                    descPlant = "PLANT 1",
                    idFactorySectionPlant = 1
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
                plantRepository.listByIdFactorySection(
                    token = "token",
                    idFactorySection = 1
                )
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                plantRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                plantRepository.addAll(entityList)
            ).thenReturn(
                resultFailure(
                    "IPlantRepository.addAll",
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
                    tableUpdate = "tb_plant",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_plant",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_plant",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTablePlantByIdFactorySection -> IPlantRepository.addAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return correct if process execute successfully`() =
        runTest {
            val entityList = listOf(
                Plant(
                    idPlant = 1,
                    codPlant = "01",
                    descPlant = "PLANT 1",
                    idFactorySectionPlant = 1
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
                plantRepository.listByIdFactorySection(
                    token = "token",
                    idFactorySection = 1
                )
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                plantRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                plantRepository.addAll(entityList)
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
                    tableUpdate = "tb_plant",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_plant",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_plant",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
        }

}