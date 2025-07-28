package br.com.usinasantafe.pci.domain.usecases.header

import br.com.usinasantafe.pci.domain.entities.stable.OS
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.OSRepository
import br.com.usinasantafe.pci.domain.repositories.stable.PlantRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IListOSHeaderTest {

    private val osRepository = mock<OSRepository>()
    private val plantRepository = mock<PlantRepository>()
    private val usecase = IListOSHeader(
        osRepository = osRepository,
        plantRepository = plantRepository
    )

    @Test
    fun `Check return failure if have error in OSRepository listAll`() =
        runTest {
            whenever(
                osRepository.listAll()
            ).thenReturn(
                resultFailure(
                    "IOSRepository.listAll",
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
                "IListOSHeader -> IOSRepository.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in PlantRepository listAll`() =
        runTest {
            val osList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 120000,
                    idPlantOS = 1,
                    qtdDayOS = 1,
                    descPeriodOS = "DIÁRIO"
                ),
            )
            whenever(
                osRepository.listAll()
            ).thenReturn(
                resultFailure(
                    "IOSRepository.listAll",
                    "-",
                    Exception()
                )
            )
            whenever(
                plantRepository.listAll()
            ).thenReturn(
                resultFailure(
                    "IPlantRepository.listAll",
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
                "IListOSHeader -> IPlantRepository.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

}