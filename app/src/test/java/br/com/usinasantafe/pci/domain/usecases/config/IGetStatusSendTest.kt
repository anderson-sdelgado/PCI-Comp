package br.com.usinasantafe.pci.domain.usecases.config

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pci.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IGetStatusSendTest {

    private val configRepository = mock<ConfigRepository>()
    private val checkListRepository = mock<CheckListRepository>()
    private val usecase = IGetStatusSend(
        configRepository = configRepository,
        checkListRepository = checkListRepository
    )
    
//    @Test
//    fun `Check return failure if have error in ConfigRepository hasConfig`() =
//        runTest {
//            whenever(
//                configRepository.hasConfig()
//            ).thenReturn(
//                resultFailure(
//                    "IConfigRepository.hasConfig",
//                    "-",
//                    Exception()
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "IGetStatusSend -> IConfigRepository.hasConfig"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.Exception"
//            )
//        }
//
//    @Test
//    fun `Check return StatusSend STARTED if ConfigRepository hasConfig return false`() =
//        runTest {
//            whenever(
//                configRepository.hasConfig()
//            ).thenReturn(
//                Result.success(false)
//            )
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                StatusSend.STARTED
//            )
//        }
//
//    @Test
//    fun `Check return failure if have error in CheckListRepository checkRespSend`() =
//        runTest {
//            whenever(
//                configRepository.hasConfig()
//            ).thenReturn(
//                Result.success(true)
//            )
//            whenever(
//                checkListRepository.checkRespSend()
//            ).thenReturn(
//                resultFailure(
//                    "ICheckListRepository.checkRespSend",
//                    "-",
//                    Exception()
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "IGetStatusSend -> ICheckListRepository.checkRespSend"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.Exception"
//            )
//        }
//
//    @Test
//    fun `Check return StatusSend SENT if CheckListRepository checkRespSend return false`() =
//        runTest {
//            whenever(
//                configRepository.hasConfig()
//            ).thenReturn(
//                Result.success(true)
//            )
//            whenever(
//                checkListRepository.checkRespSend()
//            ).thenReturn(
//                Result.success(false)
//            )
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                StatusSend.SENT
//            )
//        }
//
//    @Test
//    fun `Check return StatusSend SEND if CheckListRepository checkRespSend return true`() =
//        runTest {
//            whenever(
//                configRepository.hasConfig()
//            ).thenReturn(
//                Result.success(true)
//            )
//            whenever(
//                checkListRepository.checkRespSend()
//            ).thenReturn(
//                Result.success(true)
//            )
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                StatusSend.SEND
//            )
//        }

}