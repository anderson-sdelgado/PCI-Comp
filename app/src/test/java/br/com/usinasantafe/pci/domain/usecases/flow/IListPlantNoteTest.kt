package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.entities.stable.Item
import br.com.usinasantafe.pci.domain.entities.stable.Plant
import br.com.usinasantafe.pci.domain.entities.variable.Resp
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.PlantRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.presenter.model.PlantScreenModel
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.Status
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IListPlantNoteTest {

    private val itemRepository = mock<ItemRepository>()
    private val plantRepository = mock<PlantRepository>()
    private val checkListRepository = mock<CheckListRepository>()
    private val usecase = IListPlantNote(
        itemRepository = itemRepository,
        plantRepository = plantRepository,
        checkListRepository = checkListRepository
    )

    private val itemList = listOf(
        Item(
            idItem = 1,
            seqItem = 1,
            idOSItem = 1,
            idPlantItem = 1,
            idComponentItem = 1,
            idServiceItem = 1
        ),
        Item(
            idItem = 2,
            seqItem = 2,
            idOSItem = 1,
            idPlantItem = 1,
            idComponentItem = 1,
            idServiceItem = 1
        ),
        Item(
            idItem = 3,
            seqItem = 3,
            idOSItem = 1,
            idPlantItem = 2,
            idComponentItem = 2,
            idServiceItem = 2
        ),
        Item(
            idItem = 4,
            seqItem = 4,
            idOSItem = 1,
            idPlantItem = 3,
            idComponentItem = 3,
            idServiceItem = 3
        ),
        Item(
            idItem = 5,
            seqItem = 5,
            idOSItem = 1,
            idPlantItem = 3,
            idComponentItem = 3,
            idServiceItem = 4
        )
    )

    private val plantList = listOf(
        Plant(
            idPlant = 1,
            codPlant = "01",
            descPlant = "Plant 1",
            idFactorySectionPlant = 1
        ),
        Plant(
            idPlant = 2,
            codPlant = "02",
            descPlant = "Plant 2",
            idFactorySectionPlant = 1
        ),
        Plant(
            idPlant = 3,
            codPlant = "03",
            descPlant = "Plant 3",
            idFactorySectionPlant = 1
        )
    )

    private val plantMinorList = listOf(
        Plant(
            idPlant = 1,
            codPlant = "01",
            descPlant = "Plant 1",
            idFactorySectionPlant = 1
        ),
        Plant(
            idPlant = 3,
            codPlant = "03",
            descPlant = "Plant 3",
            idFactorySectionPlant = 1
        )
    )

    @Test
    fun `Check return failure if have error in ItemRepository listAll`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                resultFailure(
                    "IItemRepository.listAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListPlantNote -> IItemRepository.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository listByIdItems`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                checkListRepository.listRespByIdItems(
                    listOf(1, 2, 3, 4, 5)
                )
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.listByIdItems",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListPlantNote -> ICheckListRepository.listByIdItems"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in PlantRepository getById and resp is list empty`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                checkListRepository.listRespByIdItems(
                    listOf(1, 2, 3, 4, 5)
                )
            ).thenReturn(
                Result.success(emptyList())
            )
            val ids = listOf(1, 2, 3)
            whenever(
                plantRepository.listByIds(ids)
            ).thenReturn(
                resultFailure(
                    "IPlantRepository.listByIdList",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListPlantNote -> IPlantRepository.listByIdList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return list if process execute successfully and resp is list empty`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                checkListRepository.listRespByIdItems(
                    listOf(1, 2, 3, 4, 5)
                )
            ).thenReturn(
                Result.success(emptyList())
            )
            val ids = listOf(1, 2, 3)
            whenever(
                plantRepository.listByIds(ids)
            ).thenReturn(
                Result.success(plantList)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    PlantScreenModel(
                        id = 1,
                        cod = "01",
                        desc = "Plant 1",
                        status = false
                    ),
                    PlantScreenModel(
                        id = 2,
                        cod = "02",
                        desc = "Plant 2",
                        status = false
                    ),
                    PlantScreenModel(
                        id = 3,
                        cod = "03",
                        desc = "Plant 3",
                        status = false
                    )
                )
            )
        }

    @Test
    fun `Check return failure if have error in PlantRepository getById and resp with data`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                checkListRepository.listRespByIdItems(
                    listOf(1, 2, 3, 4, 5)
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Resp(
                            idHeader = 1,
                            idItem = 5,
                            idPlant = 1,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs"
                        ),
                    )
                )
            )
            val ids = listOf(1, 2, 3)
            whenever(
                plantRepository.listByIds(ids)
            ).thenReturn(
                resultFailure(
                    "IPlantRepository.listByIdList",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListPlantNote -> IPlantRepository.listByIdList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return list if process execute successfully and resp with data`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                checkListRepository.listRespByIdItems(
                    listOf(1, 2, 3, 4, 5)
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Resp(
                            idHeader = 1,
                            idItem = 5,
                            idPlant = 1,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs"
                        ),
                    )
                )
            )
            val ids = listOf(1, 2, 3)
            whenever(
                plantRepository.listByIds(ids)
            ).thenReturn(
                Result.success(plantList)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    PlantScreenModel(
                        id = 1,
                        cod = "01",
                        desc = "Plant 1",
                        status = false
                    ),
                    PlantScreenModel(
                        id = 2,
                        cod = "02",
                        desc = "Plant 2",
                        status = false
                    ),
                    PlantScreenModel(
                        id = 3,
                        cod = "03",
                        desc = "Plant 3",
                        status = true
                    )
                )
            )
        }

    @Test
    fun `Check return failure if have error in PlantRepository getById and resp with data and resp finish`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                checkListRepository.listRespByIdItems(
                    listOf(1, 2, 3, 4, 5)
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Resp(
                            idHeader = 1,
                            idItem = 4,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs",
                            status = Status.FINISH
                        ),
                        Resp(
                            idHeader = 1,
                            idItem = 5,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs",
                        ),
                    )
                )
            )
            val ids = listOf(1, 2, 3)
            whenever(
                plantRepository.listByIds(ids)
            ).thenReturn(
                resultFailure(
                    "IPlantRepository.listByIdList",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListPlantNote -> IPlantRepository.listByIdList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return list if process execute successfully and resp with data and resp finish`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                checkListRepository.listRespByIdItems(
                    listOf(1, 2, 3, 4, 5)
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Resp(
                            idHeader = 1,
                            idItem = 4,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs",
                            status = Status.FINISH
                        ),
                        Resp(
                            idHeader = 1,
                            idItem = 5,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs",
                        ),
                    )
                )
            )
            val ids = listOf(1, 2, 3)
            whenever(
                plantRepository.listByIds(ids)
            ).thenReturn(
                Result.success(plantList)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    PlantScreenModel(
                        id = 1,
                        cod = "01",
                        desc = "Plant 1",
                        status = false
                    ),
                    PlantScreenModel(
                        id = 2,
                        cod = "02",
                        desc = "Plant 2",
                        status = false
                    ),
                    PlantScreenModel(
                        id = 3,
                        cod = "03",
                        desc = "Plant 3",
                        status = true
                    )
                )
            )
        }

    @Test
    fun `Check return failure if have error in PlantRepository getById and resp with data and plant finish`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                checkListRepository.listRespByIdItems(
                    listOf(1, 2, 3, 4, 5)
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Resp(
                            idHeader = 1,
                            idItem = 3,
                            idPlant = 2,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs",
                            status = Status.FINISH
                        ),
                        Resp(
                            idHeader = 1,
                            idItem = 4,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs",
                        ),
                        Resp(
                            idHeader = 1,
                            idItem = 5,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs",
                            status = Status.FINISH
                        ),
                    )
                )
            )
            val ids = listOf(1, 3)
            whenever(
                plantRepository.listByIds(ids)
            ).thenReturn(
                resultFailure(
                    "IPlantRepository.listByIdList",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListPlantNote -> IPlantRepository.listByIdList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return list if process execute successfully and resp with data and plant finish`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                checkListRepository.listRespByIdItems(
                    listOf(1, 2, 3, 4, 5)
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Resp(
                            idHeader = 1,
                            idItem = 3,
                            idPlant = 2,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs",
                            status = Status.FINISH
                        ),
                        Resp(
                            idHeader = 1,
                            idItem = 4,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs",
                        ),
                        Resp(
                            idHeader = 1,
                            idItem = 5,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs",
                            status = Status.FINISH
                        ),
                    )
                )
            )
            val ids = listOf(1, 3)
            whenever(
                plantRepository.listByIds(ids)
            ).thenReturn(
                Result.success(plantMinorList)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    PlantScreenModel(
                        id = 1,
                        cod = "01",
                        desc = "Plant 1",
                        status = false
                    ),
                    PlantScreenModel(
                        id = 3,
                        cod = "03",
                        desc = "Plant 3",
                        status = true
                    )
                )
            )
        }

    @Test
    fun `Check return order list if process execute successfully and resp with data`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                checkListRepository.listRespByIdItems(
                    listOf(1, 2, 3, 4, 5)
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Resp(
                            idHeader = 1,
                            idItem = 1,
                            idPlant = 1,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs",
                        ),
                        Resp(
                            idHeader = 4,
                            idItem = 4,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs",
                        ),
                    )
                )
            )
            val ids = listOf(1, 2, 3)
            whenever(
                plantRepository.listByIds(ids)
            ).thenReturn(
                Result.success(plantList)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    PlantScreenModel(
                        id = 1,
                        cod = "01",
                        desc = "Plant 1",
                        status = true
                    ),
                    PlantScreenModel(
                        id = 3,
                        cod = "03",
                        desc = "Plant 3",
                        status = true
                    ),
                    PlantScreenModel(
                        id = 2,
                        cod = "02",
                        desc = "Plant 2",
                        status = false
                    )
                )
            )
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "IListPlantNote"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException"
//            )
        }
}