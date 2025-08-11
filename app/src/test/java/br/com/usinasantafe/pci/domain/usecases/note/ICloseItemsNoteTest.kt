package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.entities.stable.Item
import br.com.usinasantafe.pci.domain.entities.variable.Resp
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.OptionResp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICloseItemsNoteTest {

    private val checkListRepository = mock<CheckListRepository>()
    private val itemRepository = mock<ItemRepository>()
    private val usecase = ICloseItemsNote(
        checkListRepository = checkListRepository,
        itemRepository = itemRepository
    )

    @Test
    fun `Check return failure if have error in CheckListRepository closeItem`() =
        runTest {
            whenever(
                checkListRepository.closeItems(1)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.closeItem",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICloseItemsNote -> ICheckListRepository.closeItem"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository getIdOSHeaderOpen`() =
        runTest {
            whenever(
                checkListRepository.closeItems(1)
            ).thenReturn(
                Result.success(true)
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
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICloseItemsNote -> ICheckListRepository.getIdOSHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ItemRepository listByIdOSAndIdPlant`() =
        runTest {
            whenever(
                checkListRepository.closeItems(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemRepository.listByIdOSAndIdPlant(
                    idOS = 1,
                    idPlant = 1
                )
            ).thenReturn(
                resultFailure(
                    "IItemRepository.listByIdOSAndIdPlant",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICloseItemsNote -> IItemRepository.listByIdOSAndIdPlant"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository listRespByIdPlantAndHeaderOpen`() =
        runTest {
            whenever(
                checkListRepository.closeItems(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemRepository.listByIdOSAndIdPlant(
                    idOS = 1,
                    idPlant = 1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Item(
                            idItem = 1,
                            seqItem = 1,
                            idOSItem = 1,
                            idPlantItem = 1,
                            idComponentItem = 0,
                            idServiceItem = 1
                        )
                    )
                )
            )
            whenever(
                checkListRepository.listRespByIdPlantAndHeaderOpen(1)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.listRespByIdPlantAndHeaderOpen",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICloseItemsNote -> ICheckListRepository.listRespByIdPlantAndHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if function execute successfully and item equals resp`() =
        runTest {
            whenever(
                checkListRepository.closeItems(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemRepository.listByIdOSAndIdPlant(
                    idOS = 1,
                    idPlant = 1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Item(
                            idItem = 1,
                            seqItem = 1,
                            idOSItem = 1,
                            idPlantItem = 1,
                            idComponentItem = 0,
                            idServiceItem = 1
                        )
                    )
                )
            )
            whenever(
                checkListRepository.listRespByIdPlantAndHeaderOpen(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        Resp(
                            id = 1,
                            idHeader = 1,
                            idPlant = 1,
                            idItem = 1,
                            option = OptionResp.ACCORDING
                        )
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `Check return false if function execute successfully and item different resp`() =
        runTest {
            whenever(
                checkListRepository.closeItems(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemRepository.listByIdOSAndIdPlant(
                    idOS = 1,
                    idPlant = 1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Item(
                            idItem = 1,
                            seqItem = 1,
                            idOSItem = 1,
                            idPlantItem = 1,
                            idComponentItem = 0,
                            idServiceItem = 1
                        ),
                        Item(
                            idItem = 2,
                            seqItem = 2,
                            idOSItem = 1,
                            idPlantItem = 1,
                            idComponentItem = 0,
                            idServiceItem = 1
                        )
                    )
                )
            )
            whenever(
                checkListRepository.listRespByIdPlantAndHeaderOpen(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        Resp(
                            id = 1,
                            idHeader = 1,
                            idPlant = 1,
                            idItem = 1,
                            option = OptionResp.ACCORDING
                        )
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

}