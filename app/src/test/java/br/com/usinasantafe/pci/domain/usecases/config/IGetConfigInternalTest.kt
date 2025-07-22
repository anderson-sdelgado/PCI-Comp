package br.com.usinasantafe.pci.domain.usecases.config

import br.com.usinasantafe.pci.domain.entities.variable.Config
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.text.get

class IGetConfigInternalTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = IGetConfigInternal(
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
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetConfigInternal -> IConfigRepository.hasConfig"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return null if haven't data in Config table internal`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                null
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository get`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
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
                "IGetConfigInternal -> IConfigRepository.get"
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
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
                        number = 16997417840,
                        password = "12345"
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val configModel = result.getOrNull()!!
            assertEquals(
                configModel.number,
                "16997417840"
            )
            assertEquals(
                configModel.password,
                "12345"
            )
        }

}