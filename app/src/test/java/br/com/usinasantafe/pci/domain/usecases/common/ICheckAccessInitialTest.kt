package br.com.usinasantafe.pci.domain.usecases.common

import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pci.utils.FlagUpdate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICheckAccessInitialTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = ICheckAccessInitial(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in hasConfig`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.failure(
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
                "ICheckAccessInitial -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `Check return correct if function execute successfully and hasConfig is false`() =
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
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `Check return failure if have error in GetFlagUpdate`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getFlagUpdate()
            ).thenReturn(
                Result.failure(
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
                "ICheckAccessInitial -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `Check return correct if function execute successfully and hasConfig is true and flagUpdate is OUTDATED`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getFlagUpdate()
            ).thenReturn(
                Result.success(FlagUpdate.OUTDATED)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `Check return correct if function execute successfully and hasConfig is true and flagUpdate is UPDATED`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getFlagUpdate()
            ).thenReturn(
                Result.success(FlagUpdate.UPDATED)
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