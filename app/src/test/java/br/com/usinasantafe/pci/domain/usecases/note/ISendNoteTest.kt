package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.entities.variable.Config
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pci.domain.usecases.common.GetToken
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ISendNoteTest {

    private val getToken = mock<GetToken>()
    private val configRepository = mock<ConfigRepository>()
    private val checkListRepository = mock<CheckListRepository>()
    private val usecase = ISendNote(
        getToken = getToken,
        configRepository = configRepository,
        checkListRepository = checkListRepository
    )

    @Test
    fun `Check return failure if have error in GetToken `() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    "IGetToken",
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
                "ISendNote -> IGetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }
    
    @Test
    fun `Check return failure if have error in ConfigRepository get`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.get",
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
                "ISendNote -> IConfigRepository.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository sendNote`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
                        number = 1
                    )
                )
            )
            whenever(
                checkListRepository.sendNote(
                    token = "token",
                    number = 1
                )
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.sendNote",
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
                "ISendNote -> ICheckListRepository.sendNote"
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
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
                        number = 1
                    )
                )
            )
            whenever(
                checkListRepository.sendNote(
                    token = "token",
                    number = 1
                )
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