package br.com.usinasantafe.pci.infra.repositories.variable

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.infra.models.sharedpreferences.HeaderSharedPreferencesModel
import br.com.usinasantafe.pci.utils.Status
import br.com.usinasantafe.pci.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICheckListRepositoryTest {

    private val headerSharedPreferencesDatasource = mock<HeaderSharedPreferencesDatasource>()
    private val headerRoomDatasource = mock<HeaderRoomDatasource>()
    private val repository = ICheckListRepository(
        headerSharedPreferencesDatasource = headerSharedPreferencesDatasource,
        headerRoomDatasource = headerRoomDatasource
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

    @Test
    fun `setIdOSHeader - Check return failure if have error in HeaderSharedPreferencesDatasource setIdOS`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setIdOS(1)
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.setIdOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.setIdOSHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.setIdOSHeader -> IHeaderSharedPreferencesDatasource.setIdOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdOSHeader - Check return failure if have error in HeaderSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setIdOS(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.get",
                    "-",
                    Exception()
                )
            )
            val result = repository.setIdOSHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.setIdOSHeader -> IHeaderSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdOSHeader - Check return failure if have error in HeaderRoomDatasource save`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setIdOS(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    HeaderSharedPreferencesModel(
                        idColab = 1,
                        idFactorySection = 1,
                        idOS = 1
                    )
                )
            )
            val modelCaptor = argumentCaptor<HeaderRoomModel>().apply {
                whenever(
                    headerRoomDatasource.save(
                        capture()
                    )
                ).thenReturn(
                    resultFailure(
                        "IHeaderRoomDatasource.save",
                        "-",
                        Exception()
                    )
                )
            }
            val result = repository.setIdOSHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.setIdOSHeader -> IHeaderRoomDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.idColab,
                1
            )
            assertEquals(
                model.idFactorySection,
                1
            )
            assertEquals(
                model.idOS,
                1
            )
            assertEquals(
                model.status,
                Status.OPEN
            )
            assertEquals(
                model.statusSend,
                StatusSend.SEND
            )
        }

    @Test
    fun `setIdOSHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setIdOS(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    HeaderSharedPreferencesModel(
                        idColab = 1,
                        idFactorySection = 1,
                        idOS = 1
                    )
                )
            )
            val modelCaptor = argumentCaptor<HeaderRoomModel>().apply {
                whenever(
                    headerRoomDatasource.save(
                        capture()
                    )
                ).thenReturn(
                    Result.success(true)
                )
            }
            val result = repository.setIdOSHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.idColab,
                1
            )
            assertEquals(
                model.idFactorySection,
                1
            )
            assertEquals(
                model.idOS,
                1
            )
            assertEquals(
                model.status,
                Status.OPEN
            )
            assertEquals(
                model.statusSend,
                StatusSend.SEND
            )
        }

    @Test
    fun `getIdOSHeaderOpen - Check return failure if have error in HeaderSharedPreferencesDatasource getIdOS`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.getIdOS()
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.getIdOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.getIdOSHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.getIdOSHeaderOpen -> IHeaderSharedPreferencesDatasource.getIdOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getIdOSHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.getIdOS()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdOSHeaderOpen()
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