package br.com.usinasantafe.pci.domain.usecases.config

import br.com.usinasantafe.pci.domain.entities.variable.Config
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISaveDataConfigTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = ISaveDataConfig(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if input data config is incorrect`() =
        runTest {
            val result = usecase(
                number = "16997417840a",
                password = "12345",
                version = "1.00",
                idBD = 1,
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveDataConfig"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"16997417840a\""
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository save`() =
        runTest {
            whenever(
                configRepository.save(
                    Config(
                        number = 16997417840,
                        password = "12345",
                        version = "1.00",
                        idBD = 1
                    )
                )
            ).thenReturn(
                resultFailure(
                    context = "ISaveDataConfig",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                number = "16997417840",
                password = "12345",
                version = "1.00",
                idBD = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveDataConfig -> ISaveDataConfig"
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
                configRepository.save(
                    Config(
                        number = 16997417840,
                        password = "12345",
                        version = "1.00",
                        idBD = 1
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                number = "16997417840",
                password = "12345",
                version = "1.00",
                idBD = 1
            )
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