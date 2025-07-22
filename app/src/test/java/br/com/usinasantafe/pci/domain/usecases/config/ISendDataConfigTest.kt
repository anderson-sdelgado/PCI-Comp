package br.com.usinasantafe.pci.domain.usecases.config

import br.com.usinasantafe.pci.domain.entities.variable.Config
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISendDataConfigTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = ISendDataConfig(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if set value incorrect`() =
        runTest {
            val result = usecase(
                number = "dfas",
                password = "12345",
                version = "1.00"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendDataConfig"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"dfas\""
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository send`() =
        runTest {
            whenever(
                configRepository.send(
                    Config(
                        number = 16997417840,
                        password = "12345",
                        version = "1.00"
                    )
                )
            ).thenReturn(
                resultFailure(
                    context = "IConfigRepository.send",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                number = "16997417840",
                password = "12345",
                version = "1.00"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendDataConfig -> IConfigRepository.send"
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
                configRepository.send(
                    Config(
                        number = 16997417840,
                        password = "12345",
                        version = "1.00"
                    )
                )
            ).thenReturn(
                Result.success(
                    Config(
                        number = 16997417840,
                        password = "12345",
                        version = "1.00",
                        idBD = 1
                    )
                )
            )
            val result = usecase(
                number = "16997417840",
                password = "12345",
                version = "1.00"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "1.00",
                    idBD = 1
                )
            )
        }

}