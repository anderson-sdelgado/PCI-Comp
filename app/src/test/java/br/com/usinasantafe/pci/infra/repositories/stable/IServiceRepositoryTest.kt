package br.com.usinasantafe.pci.infra.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Component
import br.com.usinasantafe.pci.domain.entities.stable.Service
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ServiceRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.stable.ServiceRoomDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ServiceRetrofitModel
import br.com.usinasantafe.pci.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.ServiceRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IServiceRepositoryTest {

    private val serviceRoomDatasource = mock<ServiceRoomDatasource>()
    private val serviceRetrofitDatasource = mock<ServiceRetrofitDatasource>()
    private val repository = IServiceRepository(
        serviceRetrofitDatasource = serviceRetrofitDatasource,
        serviceRoomDatasource = serviceRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error in ServiceRoomDatasource addAll`() =
        runTest {
            val roomModelList = listOf(
                ServiceRoomModel(
                    idService = 1,
                    codService = 1,
                    descService = "Service 1"
                )
            )
            val entityList = listOf(
                Service(
                    idService = 1,
                    codService = 1,
                    descService = "Service 1"
                )
            )
            whenever(
                serviceRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IServiceRoomDatasource.addAll",
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
                "IServiceRepository.addAll -> IServiceRoomDatasource.addAll"
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
                ServiceRoomModel(
                    idService = 1,
                    codService = 1,
                    descService = "Service 1"
                )
            )
            val entityList = listOf(
                Service(
                    idService = 1,
                    codService = 1,
                    descService = "Service 1"
                )
            )
            whenever(
                serviceRoomDatasource.addAll(roomModelList)
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
    fun `deleteAll - Check return failure if have error in ServiceRoomDatasource deleteAll`() =
        runTest {
            whenever(
                serviceRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IServiceRoomDatasource.deleteAll",
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
                "IServiceRepository.deleteAll -> IServiceRoomDatasource.deleteAll"
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
                serviceRoomDatasource.deleteAll()
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
    fun `listAll - Check return failure if have error in ServiceRetrofitDatasource listAll`() =
        runTest {
            whenever(
                serviceRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IServiceRetrofitDatasource.listAll",
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
                "IServiceRepository.listAll -> IServiceRetrofitDatasource.listAll"
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
                ServiceRetrofitModel(
                    idService = 1,
                    codService = 1,
                    descService = "Service 1"
                ),
                ServiceRetrofitModel(
                    idService = 2,
                    codService = 2,
                    descService = "Service 2"
                )
            )
            val entityList = listOf(
                Service(
                    idService = 1,
                    codService = 1,
                    descService = "Service 1"
                ),
                Service(
                    idService = 2,
                    codService = 2,
                    descService = "Service 2"
                )
            )
            whenever(
                serviceRetrofitDatasource.listAll("token")
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
    fun `listByIdList - Check return failure if have error in ServiceRoomDatasource listByIdList`() =
        runTest {
            val ids = listOf(1, 2)
            whenever(
                serviceRoomDatasource.listByIds(ids)
            ).thenReturn(
                resultFailure(
                    "IServiceRoomDatasource.listByIds",
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
                "IServiceRepository.listByIds -> IServiceRoomDatasource.listByIds"
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
                ServiceRoomModel(
                    idService = 1,
                    codService = 1,
                    descService = "Service 1"
                ),
                ServiceRoomModel(
                    idService = 2,
                    codService = 2,
                    descService = "Service 2"
                )
            )
            val entityList = listOf(
                Service(
                    idService = 1,
                    codService = 1,
                    descService = "Service 1"
                ),
                Service(
                    idService = 2,
                    codService = 2,
                    descService = "Service 2"
                )
            )
            val ids = listOf(1, 2)
            whenever(
                serviceRoomDatasource.listByIds(ids)
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

    @Test
    fun `getById - Check return failure if have error in ServiceRoomDatasource getById`() =
        runTest {
            whenever(
                serviceRoomDatasource.getById(1)
            ).thenReturn(
                resultFailure(
                    "IServiceRoomDatasource.getById",
                    "-",
                    Exception()
                )
            )
            val result = repository.getById(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IServiceRepository.getById -> IServiceRoomDatasource.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getById - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                serviceRoomDatasource.getById(1)
            ).thenReturn(
                Result.success(
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "Service 1"
                    )
                )
            )
            val result = repository.getById(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Service(
                    idService = 1,
                    codService = 1,
                    descService = "Service 1"
                )
            )
        }
}