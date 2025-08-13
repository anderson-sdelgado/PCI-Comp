package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IFinishItemsNoteTest {

    private val checkListRepository = mock<CheckListRepository>()
    private val usecase = IFinishItemsNote(
        checkListRepository = checkListRepository
    )

    @Test
    fun `Check return failure if have error in CheckListRepository closeItem`() =
        runTest {
            whenever(
                checkListRepository.finishItems(1)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.closeItem",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFinishItemsNote -> ICheckListRepository.closeItem"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if function execute successfully`() =
        runTest {
            whenever(
                checkListRepository.finishItems(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(1)
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