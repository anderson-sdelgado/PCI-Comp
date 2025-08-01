package br.com.usinasantafe.pci.infra.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Item
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ItemRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.stable.ItemRoomDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ItemRetrofitModel
import br.com.usinasantafe.pci.infra.models.room.stable.ItemRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IItemRepositoryTest {

    private val itemRetrofitDatasource = mock<ItemRetrofitDatasource>()
    private val itemRoomDatasource = mock<ItemRoomDatasource>()
    private val repository = IItemRepository(
        itemRoomDatasource = itemRoomDatasource,
        itemRetrofitDatasource = itemRetrofitDatasource
    )

    @Test
    fun `addAll - Check return failure if have error in ItemRoomDatasource addAll`() =
        runTest {
            val modelList = listOf(
                ItemRoomModel(
                    idItem = 1,
                    seqItem = 1,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                )
            )
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
                itemRoomDatasource.addAll(modelList)
            ).thenReturn(
                resultFailure(
                    context = "IItemRoomDatasource.addAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemRepository.addAll -> IItemRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `addAll - Check return true if function execute successfully`() =
        runTest {
            val modelList = listOf(
                ItemRoomModel(
                    idItem = 1,
                    seqItem = 1,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                )
            )
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
                itemRoomDatasource.addAll(modelList)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.addAll(entityList)
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
    fun `deleteAll - Check return failure if have error in ItemRoomDatasource deleteAll`() =
        runTest {
            whenever(
                itemRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "IItemRoomDatasource.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = repository
                .deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemRepository.deleteAll -> IItemRoomDatasource.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                itemRoomDatasource.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository
                .deleteAll()
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
    fun `listByIdOS - Check return failure if have error in ItemRoomDatasource listByIdOS`() =
        runTest {
            whenever(
                itemRetrofitDatasource.listByIdOS(
                    token = "token",
                    idOS = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "IItemRetrofitDatasource.listByIdOS",
                    "-",
                    Exception()
                )
            )
            val result = repository
                .listByIdOS(
                    token = "token",
                    idOS = 1
                )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemRepository.listByIdOS -> IItemRetrofitDatasource.listByIdOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdOS - Check return true if function execute successfully`() =
        runTest {
            val modelList = listOf(
                ItemRetrofitModel(
                    idItem = 1,
                    seqItem = 1,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                )
            )
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
                itemRetrofitDatasource.listByIdOS(
                    token = "token",
                    idOS = 1
                )
            ).thenReturn(
                Result.success(modelList)
            )
            val result = repository.listByIdOS(
                token = "token",
                idOS = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                entityList
            )
        }

    @Test
    fun `listAll - Check return failure if have error in ItemRoomDatasource listAll`() =
        runTest {
            whenever(
                itemRoomDatasource.listAll()
            ).thenReturn(
                resultFailure(
                    "IItemRoomDatasource.listAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.listAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemRepository.listAll -> IItemRoomDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listAll - Check return correct if function execute successfully`() =
        runTest {
            val modelList = listOf(
                ItemRoomModel(
                    idItem = 1,
                    seqItem = 1,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                )
            )
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
                itemRoomDatasource.listAll()
            ).thenReturn(
                Result.success(modelList)
            )
            val result = repository.listAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                entityList
            )
        }

    @Test
    fun `listByIdOSAndIdPlant - Check return failure if have error in ItemRoomDatasource listByIdOSAndIdPlant`() =
        runTest {
            whenever(
                itemRoomDatasource.listByIdOSAndIdPlant(
                    idOS = 1,
                    idPlant = 1
                )
            ).thenReturn(
                resultFailure(
                    "IItemRoomDatasource.listByIdOSAndIdPlant",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdOSAndIdPlant(
                idOS = 1,
                idPlant = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemRepository.listByIdOSAndIdPlant -> IItemRoomDatasource.listByIdOSAndIdPlant"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdOSAndIdPlant - Check return correct if function execute successfully`() =
        runTest {
            val modelList = listOf(
                ItemRoomModel(
                    idItem = 1,
                    seqItem = 1,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                )
            )
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
                itemRoomDatasource.listByIdOSAndIdPlant(
                    idOS = 1,
                    idPlant = 1
                )
            ).thenReturn(
                Result.success(modelList)
            )
            val result = repository.listByIdOSAndIdPlant(
                idOS = 1,
                idPlant = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                entityList
            )
        }

}