package br.com.usinasantafe.pci.domain.usecases.update

import br.com.usinasantafe.pci.domain.entities.stable.Item
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.domain.usecases.flow.GetToken
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

class IUpdateTableItemByIdOSTest {

    private val getToken = mock<GetToken>()
    private val checkListRepository = mock<CheckListRepository>()
    private val itemRepository = mock<ItemRepository>()
    private val usecase = IUpdateTableItemByIdOS(
        getToken = getToken,
        checkListRepository = checkListRepository,
        itemRepository = itemRepository
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
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemByIdOS -> GetToken -> java.lang.Exception",
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
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.getIdOSHeaderOpen",
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
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemByIdOS -> ICheckListRepository.getIdOSHeaderOpen -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemRepository listByIdOS`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemRepository.listByIdOS(
                    token = "token",
                    idOS = 1
                )
            ).thenReturn(
                resultFailure(
                    "IItemRepository.listByIdOS",
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
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemByIdOS -> IItemRepository.listByIdOS -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemRepository deleteAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemRepository.listByIdOS(
                    token = "token",
                    idOS = 1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Item(
                            idItem = 1,
                            seqItem = 1,
                            idOSItem = 1,
                            idPlantItem = 1,
                            idComponentItem = 1,
                            idServiceItem = 1
                        )
                    )
                )
            )
            whenever(
                itemRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IItemRepository.deleteAll",
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
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemByIdOS -> IItemRepository.deleteAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemRepository addAll`() =
        runTest {
            val entityList = listOf(
                Item(
                    idItem = 1,
                    seqItem = 1,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemRepository.listByIdOS(
                    token = "token",
                    idOS = 1
                )
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                itemRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                itemRepository.addAll(entityList)
            ).thenReturn(
                resultFailure(
                    "IItemRepository.addAll",
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
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemByIdOS -> IItemRepository.addAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return correct if process execute successfully`() =
        runTest {
            val entityList = listOf(
                Item(
                    idItem = 1,
                    seqItem = 1,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemRepository.listByIdOS(
                    token = "token",
                    idOS = 1
                )
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                itemRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                itemRepository.addAll(entityList)
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
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
        }

}