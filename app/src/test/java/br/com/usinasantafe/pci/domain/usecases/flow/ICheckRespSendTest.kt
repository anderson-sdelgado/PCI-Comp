package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICheckRespSendTest {

    private val checkListRepository = mock<CheckListRepository>()
    private val usecase = ICheckRespSend(
        checkListRepository = checkListRepository
    )

    @Test
    fun `Check return failure if have error in CheckListRepository checkRespSend`() =
        runTest {
            whenever(
                checkListRepository.checkRespSend()
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.checkRespSend",
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
                "ICheckRespSend -> ICheckListRepository.checkRespSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                checkListRepository.checkRespSend()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}