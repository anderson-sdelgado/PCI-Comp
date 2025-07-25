package br.com.usinasantafe.pci.infra.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.OS
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.OSRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.stable.OSRoomDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.pci.infra.models.room.stable.OSRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IOSRepositoryTest {

    private val osRetrofitDatasource = mock<OSRetrofitDatasource>()
    private val osRoomDatasource = mock<OSRoomDatasource>()
    private val repository = IOSRepository(
        osRoomDatasource = osRoomDatasource,
        osRetrofitDatasource = osRetrofitDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val modelList = listOf(
                OSRoomModel(
                    idOS = 1,
                    nroOS = 1,
                    idPlantOS = 1,
                    qtdDayOS = 1,
                    descPeriodOS = "DIARIO"
                )
            )
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 1,
                    idPlantOS = 1,
                    qtdDayOS = 1,
                    descPeriodOS = "DIARIO"
                )
            )
            whenever(
                osRoomDatasource.addAll(modelList)
            ).thenReturn(
                resultFailure(
                    context = "IOSRoomDatasource.addAll",
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
                "IColabRepository.addAll -> IOSRoomDatasource.addAll"
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
                OSRoomModel(
                    idOS = 1,
                    nroOS = 1,
                    idPlantOS = 1,
                    qtdDayOS = 1,
                    descPeriodOS = "DIARIO"
                )
            )
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 1,
                    idPlantOS = 1,
                    qtdDayOS = 1,
                    descPeriodOS = "DIARIO"
                )
            )
            whenever(
                osRoomDatasource.addAll(modelList)
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
                osRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "IOSRoomDatasource.deleteAll",
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
                "IColabRepository.deleteAll -> IOSRoomDatasource.deleteAll"
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
                osRoomDatasource.deleteAll()
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
                osRetrofitDatasource.listByIdFactorySection(
                    token = "token",
                    idFactorySection = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "IOSRetrofitDatasource.listByIdFactorySection",
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
                "IColabRepository.listByIdFactorySection -> IOSRetrofitDatasource.listByIdFactorySection"
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
                OSRetrofitModel(
                    idOS = 1,
                    nroOS = 1,
                    idPlantOS = 1,
                    qtdDayOS = 1,
                    descPeriodOS = "DIARIO"
                )
            )
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 1,
                    idPlantOS = 1,
                    qtdDayOS = 1,
                    descPeriodOS = "DIARIO"
                )
            )
            whenever(
                osRetrofitDatasource.listByIdFactorySection(
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
}