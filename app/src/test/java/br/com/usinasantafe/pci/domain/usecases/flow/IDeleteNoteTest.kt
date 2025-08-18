package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.entities.variable.Header
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.domain.usecases.background.StartWorkManager
import br.com.usinasantafe.pci.utils.Status
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test

class IDeleteNoteTest {

    private val checkListRepository = mock<CheckListRepository>()
    private val startWorkManager = mock<StartWorkManager>()
    private val usecase = IDeleteNote(
        checkListRepository = checkListRepository,
        startWorkManager = startWorkManager
    )

    @Test
    fun `Check return failure if have error in CheckListRepository allHeader`() =
        runTest {
            whenever(
                checkListRepository.allHeader()
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.allHeader",
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
                "IDeleteNote -> ICheckListRepository.allHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully with noted current OPEN`() =
        runTest {
            whenever(
                checkListRepository.allHeader()
            ).thenReturn(
                Result.success(
                    listOf(
                        Header(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date()
                        )
                    )
                )
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

    @Test
    fun `Check return correct if function execute successfully with noted current FINISH`() =
        runTest {
            whenever(
                checkListRepository.allHeader()
            ).thenReturn(
                Result.success(
                    listOf(
                        Header(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date()
                        ),
                        Header(
                            id = 2,
                            idColab = 2,
                            idFactorySection = 1,
                            idOS = 2,
                            status = Status.FINISH,
                            dateHour = Date()
                        )
                    )
                )
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

    @Test
    fun `Check return failure if have error in CheckListRepository deleteRespByIdHeader and note minor year`() =
        runTest {
            whenever(
                checkListRepository.allHeader()
            ).thenReturn(
                Result.success(
                    listOf(
                        Header(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date()
                        ),
                        Header(
                            id = 2,
                            idColab = 2,
                            idFactorySection = 1,
                            idOS = 2,
                            status = Status.FINISH,
                            dateHour = Date()
                        ),
                        Header(
                            id = 3,
                            idColab = 3,
                            idFactorySection = 1,
                            idOS = 3,
                            status = Status.OPEN,
                            dateHour = Date(1723292662000)
                        ),
                    )
                )
            )
            whenever(
                checkListRepository.deleteRespByIdHeader(3)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.deleteRespByIdHeader",
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
                "IDeleteNote -> ICheckListRepository.deleteRespByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository deleteHeader and note minor year`() =
        runTest {
            whenever(
                checkListRepository.allHeader()
            ).thenReturn(
                Result.success(
                    listOf(
                        Header(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date()
                        ),
                        Header(
                            id = 2,
                            idColab = 2,
                            idFactorySection = 1,
                            idOS = 2,
                            status = Status.FINISH,
                            dateHour = Date()
                        ),
                        Header(
                            id = 3,
                            idColab = 3,
                            idFactorySection = 1,
                            idOS = 3,
                            status = Status.OPEN,
                            dateHour = Date(1723292662000)
                        ),
                    )
                )
            )
            whenever(
                checkListRepository.deleteRespByIdHeader(3)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.deleteHeader(3)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.deleteHeader",
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
                "IDeleteNote -> ICheckListRepository.deleteHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository deleteRespByIdHeader and note minor month and FINISH`() =
        runTest {
            whenever(
                checkListRepository.allHeader()
            ).thenReturn(
                Result.success(
                    listOf(
                        Header(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date()
                        ),
                        Header(
                            id = 2,
                            idColab = 2,
                            idFactorySection = 1,
                            idOS = 2,
                            status = Status.FINISH,
                            dateHour = Date()
                        ),
                        Header(
                            id = 3,
                            idColab = 3,
                            idFactorySection = 1,
                            idOS = 3,
                            status = Status.OPEN,
                            dateHour = Date(1723292662000)
                        ),
                        Header(
                            id = 4,
                            idColab = 4,
                            idFactorySection = 1,
                            idOS = 4,
                            status = Status.FINISH,
                            dateHour = Date(1752150262000)
                        ),
                    )
                )
            )
            whenever(
                checkListRepository.deleteRespByIdHeader(3)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.deleteHeader(3)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.deleteRespByIdHeader(4)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.deleteRespByIdHeader",
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
                "IDeleteNote -> ICheckListRepository.deleteRespByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository deleteHeader and note minor month and FINISH`() =
        runTest {
            whenever(
                checkListRepository.allHeader()
            ).thenReturn(
                Result.success(
                    listOf(
                        Header(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date()
                        ),
                        Header(
                            id = 2,
                            idColab = 2,
                            idFactorySection = 1,
                            idOS = 2,
                            status = Status.FINISH,
                            dateHour = Date()
                        ),
                        Header(
                            id = 3,
                            idColab = 3,
                            idFactorySection = 1,
                            idOS = 3,
                            status = Status.OPEN,
                            dateHour = Date(1723292662000)
                        ),
                        Header(
                            id = 4,
                            idColab = 4,
                            idFactorySection = 1,
                            idOS = 4,
                            status = Status.FINISH,
                            dateHour = Date(1752150262000)
                        ),
                    )
                )
            )
            whenever(
                checkListRepository.deleteRespByIdHeader(3)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.deleteHeader(3)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.deleteRespByIdHeader(4)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.deleteHeader(4)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.deleteHeader",
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
                "IDeleteNote -> ICheckListRepository.deleteHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return success if process execute successfully`() =
        runTest {
            whenever(
                checkListRepository.allHeader()
            ).thenReturn(
                Result.success(
                    listOf(
                        Header(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date()
                        ),
                        Header(
                            id = 2,
                            idColab = 2,
                            idFactorySection = 1,
                            idOS = 2,
                            status = Status.FINISH,
                            dateHour = Date()
                        ),
                        Header(
                            id = 3,
                            idColab = 3,
                            idFactorySection = 1,
                            idOS = 3,
                            status = Status.OPEN,
                            dateHour = Date(1723292662000)
                        ),
                        Header(
                            id = 4,
                            idColab = 4,
                            idFactorySection = 1,
                            idOS = 4,
                            status = Status.FINISH,
                            dateHour = Date(1752150262000)
                        ),
                    )
                )
            )
            whenever(
                checkListRepository.deleteRespByIdHeader(3)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.deleteHeader(3)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.deleteRespByIdHeader(4)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.deleteHeader(4)
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