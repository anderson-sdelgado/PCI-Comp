package br.com.usinasantafe.pci.infra.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Component
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ComponentRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.stable.ComponentRoomDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ComponentRetrofitModel
import br.com.usinasantafe.pci.infra.models.room.stable.ComponentRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IComponentRepositoryTest {

    private val componentRoomDatasource = mock<ComponentRoomDatasource>()
    private val componentRetrofitDatasource = mock<ComponentRetrofitDatasource>()
    private val repository = IComponentRepository(
        componentRetrofitDatasource = componentRetrofitDatasource,
        componentRoomDatasource = componentRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error in ComponentRoomDatasource addAll`() =
        runTest {
            val roomModelList = listOf(
                ComponentRoomModel(
                    idComponent = 1,
                    codComponent = "1",
                    descComponent = "Component 1"
                )
            )
            val entityList = listOf(
                Component(
                    idComponent = 1,
                    codComponent = "1",
                    descComponent = "Component 1"
                )
            )
            whenever(
                componentRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IComponentRoomDatasource.addAll",
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
                "IComponentRepository.addAll -> IComponentRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `addAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                ComponentRoomModel(
                    idComponent = 1,
                    codComponent = "1",
                    descComponent = "Component 1"
                )
            )
            val entityList = listOf(
                Component(
                    idComponent = 1,
                    codComponent = "1",
                    descComponent = "Component 1"
                )
            )
            whenever(
                componentRoomDatasource.addAll(roomModelList)
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
    fun `deleteAll - Check return failure if have error in ComponentRoomDatasource deleteAll`() =
        runTest {
            whenever(
                componentRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IComponentRoomDatasource.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IComponentRepository.deleteAll -> IComponentRoomDatasource.deleteAll"
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
                componentRoomDatasource.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteAll()
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
    fun `listAll - Check return failure if have error in ComponentRetrofitDatasource listAll`() =
        runTest {
            whenever(
                componentRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IComponentRetrofitDatasource.listAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IComponentRepository.listAll -> IComponentRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                ComponentRetrofitModel(
                    idComponent = 1,
                    codComponent = "1",
                    descComponent = "Component 1"
                ),
                ComponentRetrofitModel(
                    idComponent = 2,
                    codComponent = "2",
                    descComponent = "Component 2"
                )
            )
            val entityList = listOf(
                Component(
                    idComponent = 1,
                    codComponent = "1",
                    descComponent = "Component 1"
                ),
                Component(
                    idComponent = 2,
                    codComponent = "2",
                    descComponent = "Component 2"
                )
            )
            whenever(
                componentRetrofitDatasource.listAll("token")
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.listAll("token")
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
    fun `listByIdList - Check return failure if have error in ComponentRoomDatasource listByIdList`() =
        runTest {
            val ids = listOf(1, 2)
            whenever(
                componentRoomDatasource.listByIds(ids)
            ).thenReturn(
                resultFailure(
                    "IComponentRoomDatasource.listByIds",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIds(ids)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IComponentRepository.listByIds -> IComponentRoomDatasource.listByIds"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdList - Check return correct if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                ComponentRoomModel(
                    idComponent = 1,
                    codComponent = "1",
                    descComponent = "Component 1"
                ),
                ComponentRoomModel(
                    idComponent = 2,
                    codComponent = "2",
                    descComponent = "Component 2"
                )
            )
            val entityList = listOf(
                Component(
                    idComponent = 1,
                    codComponent = "1",
                    descComponent = "Component 1"
                ),
                Component(
                    idComponent = 2,
                    codComponent = "2",
                    descComponent = "Component 2"
                )
            )
            val ids = listOf(1, 2)
            whenever(
                componentRoomDatasource.listByIds(ids)
            ).thenReturn(
                Result.success(roomModelList)
            )
            val result = repository.listByIds(ids)
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