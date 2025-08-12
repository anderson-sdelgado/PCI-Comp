package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.entities.stable.Item
import br.com.usinasantafe.pci.domain.entities.variable.Resp
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.OptionResp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICloseItemsNoteTest {

    private val checkListRepository = mock<CheckListRepository>()
    private val usecase = ICloseItemsNote(
        checkListRepository = checkListRepository
    )

    @Test
    fun `Check return failure if have error in CheckListRepository closeItem`() =
        runTest {
            whenever(
                checkListRepository.closeItems(1)
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
                "ICloseItemsNote -> ICheckListRepository.closeItem"
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
                checkListRepository.closeItems(1)
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