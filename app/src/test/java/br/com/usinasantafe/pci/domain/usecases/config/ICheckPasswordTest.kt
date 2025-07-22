package br.com.usinasantafe.pci.domain.usecases.config

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICheckPasswordTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = ICheckPassword(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository hasConfig`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.hasConfig",
                    "-",
                    Exception()
                )
            )
            val result = usecase("12345")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPassword -> IConfigRepository.hasConfig"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if have not data in Config table internal`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase("12345")
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
    fun `Check return failure if have error in ConfigRepository getPassword`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getPassword()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase("12345")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPassword -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if input password equal password db`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getPassword()
            ).thenReturn(
                Result.success("12345")
            )
            val result = usecase("12345")
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
    fun `Check return false if input password different password table internal`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getPassword()
            ).thenReturn(
                Result.success("12345")
            )
            val result = usecase("123456")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

}