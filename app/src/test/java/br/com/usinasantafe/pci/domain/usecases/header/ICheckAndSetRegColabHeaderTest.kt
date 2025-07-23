package br.com.usinasantafe.pci.domain.usecases.header

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.junit.Test
import org.mockito.kotlin.whenever

class ICheckAndSetRegColabHeaderTest {

    private val colabRepository = mock<ColabRepository>()
    private val checkListRepository = mock<CheckListRepository>()
    private val usecase = ICheckAndSetRegColabHeader(
        colabRepository = colabRepository,
        checkListRepository = checkListRepository
    )

    @Test
    fun `Check return failure if have error in ColabRepository getByRegColab`() =
        runTest {
            whenever(
                colabRepository.getByRegColab(19759)
            ).thenReturn(
                resultFailure(
                    "IColabRepository.getByRegColab",
                    "-",
                    Exception()
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckAndSetRegColabHeader -> IColabRepository.getByRegColab"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

}