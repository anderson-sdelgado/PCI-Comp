package br.com.usinasantafe.pci.domain.usecases.common

import br.com.usinasantafe.pci.domain.entities.variable.Config
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pci.utils.token
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.text.get

class IGetTokenTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = IGetToken(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository get`() =
        runTest {
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
                "IGetToken -> IConfigRepository.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if get return config with field empty`() =
        runTest {
            val config = Config(
                idBD = 1,
                number = 1
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(config)
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val config = Config(
                idBD = 1,
                number = 1,
                version = "1.00"
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(config)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val token = token(
                idBD = config.idBD!!,
                number = config.number!!,
                version = config.version!!
            )
            assertEquals(
                result.getOrNull()!!,
                token
            )
        }

}