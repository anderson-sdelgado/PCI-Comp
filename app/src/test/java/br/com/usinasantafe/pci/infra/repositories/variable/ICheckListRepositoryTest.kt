package br.com.usinasantafe.pci.infra.repositories.variable

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICheckListRepositoryTest {

    private val headerSharedPreferencesDatasource = mock<HeaderSharedPreferencesDatasource>()
    private val repository = ICheckListRepository(
        headerSharedPreferencesDatasource = headerSharedPreferencesDatasource
    )

    @Test
    fun `setIdColabAndIdFactorySectionHeader - Check return failure if have error in HeaderSharedPreferencesDatasource setRegColab`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setIdColabAndIdFactorySection(
                    idColab = 1,
                    idFactorySection = 1
                )
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.setRegColab",
                    "-",
                    Exception()
                )
            )
            val result = repository.setIdColabAndIdFactorySectionHeader(
                idColab = 1,
                idFactorySection = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.setIdColabAndIdFactorySectionHeader -> IHeaderSharedPreferencesDatasource.setRegColab"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdColabAndIdFactorySectionHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setIdColabAndIdFactorySection(
                    idColab = 1,
                    idFactorySection = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdColabAndIdFactorySectionHeader(
                idColab = 1,
                idFactorySection = 1
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

    @Test
    fun `getIdFactorySectionHeaderOpen - Check return failure if have error in HeaderSharedPreferencesDatasource getIdFactorySection`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.getIdFactorySection()
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.getIdFactorySection",
                    "-",
                    Exception()
                )
            )
            val result = repository.getIdFactorySectionHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.getIdFactorySectionHeaderOpen -> IHeaderSharedPreferencesDatasource.getIdFactorySection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getIdFactorySectionHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.getIdFactorySection()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdFactorySectionHeaderOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }

}