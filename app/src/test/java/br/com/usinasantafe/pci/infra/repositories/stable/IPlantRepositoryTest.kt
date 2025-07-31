package br.com.usinasantafe.pci.infra.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Plant
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.PlantRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.stable.PlantRoomDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.PlantRetrofitModel
import br.com.usinasantafe.pci.infra.models.room.stable.PlantRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IPlantRepositoryTest {

    private val plantRetrofitDatasource = mock<PlantRetrofitDatasource>()
    private val plantRoomDatasource = mock<PlantRoomDatasource>()
    private val repository = IPlantRepository(
        plantRoomDatasource = plantRoomDatasource,
        plantRetrofitDatasource = plantRetrofitDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val modelList = listOf(
                PlantRoomModel(
                    idPlant = 1,
                    codPlant = "01",
                    descPlant = "PLANT 01",
                    idFactorySectionPlant = 1
                )
            )
            val entityList = listOf(
                Plant(
                    idPlant = 1,
                    codPlant = "01",
                    descPlant = "PLANT 01",
                    idFactorySectionPlant = 1
                )
            )
            whenever(
                plantRoomDatasource.addAll(modelList)
            ).thenReturn(
                resultFailure(
                    context = "IPlantRoomDatasource.addAll",
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
                "IPlantRepository.addAll -> IPlantRoomDatasource.addAll"
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
                PlantRoomModel(
                    idPlant = 1,
                    codPlant = "01",
                    descPlant = "PLANT 01",
                    idFactorySectionPlant = 1
                )
            )
            val entityList = listOf(
                Plant(
                    idPlant = 1,
                    codPlant = "01",
                    descPlant = "PLANT 01",
                    idFactorySectionPlant = 1
                )
            )
            whenever(
                plantRoomDatasource.addAll(modelList)
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
    fun `deleteAll - Check return failure if have error`() =
        runTest {
            whenever(
                plantRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "IPlantRoomDatasource.deleteAll",
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
                "IPlantRepository.deleteAll -> IPlantRoomDatasource.deleteAll"
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
                plantRoomDatasource.deleteAll()
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
    fun `listByIdFactorySection - Check return failure if have error`() =
        runTest {
            whenever(
                plantRetrofitDatasource.listByIdFactorySection(
                    token = "token",
                    idFactorySection = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "IPlantRetrofitDatasource.listByIdFactorySection",
                    "-",
                    Exception()
                )
            )
            val result = repository
                .listByIdFactorySection(
                    token = "token",
                    idFactorySection = 1
                )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPlantRepository.listByIdFactorySection -> IPlantRetrofitDatasource.listByIdFactorySection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdFactorySection - Check return true if function execute successfully`() =
        runTest {
            val modelList = listOf(
                PlantRetrofitModel(
                    idPlant = 1,
                    codPlant = "01",
                    descPlant = "PLANT 01",
                    idFactorySectionPlant = 1
                )
            )
            val entityList = listOf(
                Plant(
                    idPlant = 1,
                    codPlant = "01",
                    descPlant = "PLANT 01",
                    idFactorySectionPlant = 1
                )
            )
            whenever(
                plantRetrofitDatasource.listByIdFactorySection(
                    token = "token",
                    idFactorySection = 1
                )
            ).thenReturn(
                Result.success(modelList)
            )
            val result = repository.listByIdFactorySection(
                token = "token",
                idFactorySection = 1
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
    fun `listByIdFactorySection - Check return failure if have error in PlantRoomDatasource listByIdFactorySection`() =
        runTest {
            whenever(
                plantRoomDatasource.listByIdFactorySection(1)
            ).thenReturn(
                resultFailure(
                    "IPlantRoomDatasource.listByIdFactorySection",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdFactorySection(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPlantRepository.listByIdFactorySection -> IPlantRoomDatasource.listByIdFactorySection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdFactorySection - Check return correct if function execute successfully`() =
        runTest {
            val modelList = listOf(
                PlantRoomModel(
                    idPlant = 1,
                    codPlant = "01",
                    descPlant = "PLANT 01",
                    idFactorySectionPlant = 1
                )
            )
            whenever(
                plantRoomDatasource.listByIdFactorySection(1)
            ).thenReturn(
                Result.success(modelList)
            )
            val result = repository.listByIdFactorySection(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            assertEquals(
                list[0].idPlant,
                1
            )
            assertEquals(
                list[0].codPlant,
                "01"
            )
            assertEquals(
                list[0].descPlant,
                "PLANT 01"
            )
            assertEquals(
                list[0].idFactorySectionPlant,
                1
            )
        }

    @Test
    fun `listByIdList - Check return failure if have error in PlantRoomDatasource listByIdList`() =
        runTest {
            val ids = listOf(1, 2, 3)
            whenever(
                plantRoomDatasource.listByIdList(ids)
            ).thenReturn(
                resultFailure(
                    "IPlantRoomDatasource.listByIdList",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdList(ids)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPlantRepository.listByIdList -> IPlantRoomDatasource.listByIdList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdList - Check return correct if function execute successfully`() =
        runTest {
            val modelList = listOf(
                PlantRoomModel(
                    idPlant = 1,
                    codPlant = "01",
                    descPlant = "PLANT 01",
                    idFactorySectionPlant = 1
                )
            )
            val entityList = listOf(
                Plant(
                    idPlant = 1,
                    codPlant = "01",
                    descPlant = "PLANT 01",
                    idFactorySectionPlant = 1
                )
            )
            val ids = listOf(1, 2, 3)
            whenever(
                plantRoomDatasource.listByIdList(ids)
            ).thenReturn(
                Result.success(modelList)
            )
            val result = repository.listByIdList(ids)
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