package br.com.usinasantafe.pci.infra.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Colab
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ColabRetrofitModel
import br.com.usinasantafe.pci.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IColabRepositoryTest {

    private val colabRoomDatasource = mock<ColabRoomDatasource>()
    private val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
    private val repository = IColabRepository(
        colabRetrofitDatasource = colabRetrofitDatasource,
        colabRoomDatasource = colabRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ColabRoomModel(
                    idColab = 1,
                    regColab = 12345L,
                    nameColab = "ANDERSON DA SILVA",
                    idFactorySectionColab = 1
                )
            )
            val entityList = listOf(
                Colab(
                    idColab = 1,
                    regColab = 12345L,
                    nameColab = "ANDERSON DA SILVA",
                    idFactorySectionColab = 1
                )
            )
            whenever(
                colabRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IColabRoomDatasource.addAll",
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
                "IColabRepository.addAll -> IColabRoomDatasource.addAll"
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
                ColabRoomModel(
                    idColab = 1,
                    regColab = 12345L,
                    nameColab = "ANDERSON DA SILVA",
                    idFactorySectionColab = 1
                )
            )
            val entityList = listOf(
                Colab(
                    idColab = 1,
                    regColab = 12345L,
                    nameColab = "ANDERSON DA SILVA",
                    idFactorySectionColab = 1
                )
            )
            whenever(
                colabRoomDatasource.addAll(roomModelList)
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
                colabRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IColabRoomDatasource.deleteAll",
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
                "IColabRepository.deleteAll -> IColabRoomDatasource.deleteAll"
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
                colabRoomDatasource.deleteAll()
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
    fun `recoverAll - Check return failure if have error`() =
        runTest {
            whenever(
                colabRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                resultFailure(
                    "IColabRetrofitDatasource.recoverAll",
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
                "IColabRepository.recoverAll -> IColabRetrofitDatasource.recoverAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `recoverAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                ColabRetrofitModel(
                    idColab = 1,
                    regColab = 12345L,
                    nameColab = "ANDERSON DA SILVA",
                    idFactorySectionColab = 1
                ),
                ColabRetrofitModel(
                    idColab = 2,
                    regColab = 67890L,
                    nameColab = "JOSE APARECIDO",
                    idFactorySectionColab = 2
                )
            )
            val entityList = listOf(
                Colab(
                    idColab = 1,
                    regColab = 12345L,
                    nameColab = "ANDERSON DA SILVA",
                    idFactorySectionColab = 1
                ),
                Colab(
                    idColab = 2,
                    regColab = 67890L,
                    nameColab = "JOSE APARECIDO",
                    idFactorySectionColab = 2
                )
            )
            whenever(
                colabRetrofitDatasource.recoverAll("token")
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

}