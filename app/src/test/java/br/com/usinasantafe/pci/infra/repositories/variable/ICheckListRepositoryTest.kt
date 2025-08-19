package br.com.usinasantafe.pci.infra.repositories.variable

import br.com.usinasantafe.pci.domain.entities.variable.Header
import br.com.usinasantafe.pci.domain.entities.variable.Resp
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.infra.datasource.retrofit.variable.CheckListRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.pci.infra.datasource.room.variable.RespRoomDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.variable.HeaderRetrofitModelInput
import br.com.usinasantafe.pci.infra.models.retrofit.variable.HeaderRetrofitModelOutput
import br.com.usinasantafe.pci.infra.models.retrofit.variable.RespRetrofitModelInput
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.infra.models.sharedpreferences.HeaderSharedPreferencesModel
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.Status
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.apply
import kotlin.test.Test

class ICheckListRepositoryTest {

    private val headerSharedPreferencesDatasource = mock<HeaderSharedPreferencesDatasource>()
    private val headerRoomDatasource = mock<HeaderRoomDatasource>()
    private val respRoomDatasource = mock<RespRoomDatasource>()
    private val checkListRetrofitDatasource = mock<CheckListRetrofitDatasource>()
    private val repository = ICheckListRepository(
        headerSharedPreferencesDatasource = headerSharedPreferencesDatasource,
        headerRoomDatasource = headerRoomDatasource,
        respRoomDatasource = respRoomDatasource,
        checkListRetrofitDatasource = checkListRetrofitDatasource
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
        }

    @Test
    fun `getIdOSHeaderOpen - Check return failure if have error in HeaderSharedPreferencesDatasource getIdOS`() =
        runTest {
            whenever(
                headerRoomDatasource.getIdOSByStatusOpen()
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.getIdOSByStatusOpen",
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
                "ICheckListRepository.getIdOSHeaderOpen -> IHeaderRoomDatasource.getIdOSByStatusOpen"
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
                headerRoomDatasource.getIdOSByStatusOpen()
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

    @Test
    fun `saveResp - Check return failure if have error in HeaderRoomDatasource getByStatus`() =
        runTest {
            whenever(
                headerRoomDatasource.getIdByStatusOpen()
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.getByStatus",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveResp(
                Resp(
                    idItem = 1,
                    idPlant = 1,
                    option = OptionResp.ACCORDING
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.saveResp -> IHeaderRoomDatasource.getByStatus"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveResp - Check return failure if have error in RespRoomDatasource save`() =
        runTest {
            whenever(
                headerRoomDatasource.getIdByStatusOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                respRoomDatasource.save(
                    RespRoomModel(
                        idHeader = 1,
                        idItem = 1,
                        idPlant = 1,
                        option = OptionResp.ACCORDING
                    )
                )
            ).thenReturn(
                resultFailure(
                    "IRespRoomDatasource.save",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveResp(
                Resp(
                    idItem = 1,
                    idPlant = 1,
                    option = OptionResp.ACCORDING
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.saveResp -> IRespRoomDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveResp - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.getIdByStatusOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                respRoomDatasource.save(
                    RespRoomModel(
                        idHeader = 1,
                        idItem = 1,
                        idPlant = 1,
                        option = OptionResp.ACCORDING
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.saveResp(
                Resp(
                    idItem = 1,
                    idPlant = 1,
                    option = OptionResp.ACCORDING
                )
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
    fun `listByIdItems - Check return failure if have error in RespRoomDatasource listByIdItems`() =
        runTest {
            whenever(
                respRoomDatasource.listByIdItems(listOf(1, 2, 3))
            ).thenReturn(
                resultFailure(
                    "IRespRoomDatasource.listByIdItems",
                    "-",
                    Exception()
                )
            )
            val result = repository.listRespByIdItems(listOf(1, 2, 3))
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.listRespByIdItems -> IRespRoomDatasource.listByIdItems"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdItems - Check return correct if function execute successfully and list empty`() =
        runTest {
            whenever(
                respRoomDatasource.listByIdItems(listOf(1, 2, 3))
            ).thenReturn(
                Result.success(listOf())
            )
            val result = repository.listRespByIdItems(listOf(1, 2, 3))
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf<Resp>()
            )
        }

    @Test
    fun `listByIdItems - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                respRoomDatasource.listByIdItems(listOf(1, 2, 3))
            ).thenReturn(
                Result.success(
                    listOf(
                        RespRoomModel(
                            id = 1,
                            idHeader = 1,
                            idItem = 1,
                            idPlant = 1,
                            option = OptionResp.ACCORDING
                        )
                    )
                )
            )
            val result = repository.listRespByIdItems(listOf(1, 2, 3))
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    Resp(
                        id = 1,
                        idHeader = 1,
                        idItem = 1,
                        idPlant = 1,
                        option = OptionResp.ACCORDING
                    )
                )
            )
        }

    @Test
    fun `getRespByIdItem - Check return failure if have error in RespRoomDatasource getByIdItem`() =
        runTest {
            whenever(
                respRoomDatasource.getByIdItem(1)
            ).thenReturn(
                resultFailure(
                    "IRespRoomDatasource.getByIdItem",
                    "-",
                    Exception()
                )
            )
            val result = repository.getRespByIdItem(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.getRespByIdItem -> IRespRoomDatasource.getByIdItem"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getRespByIdItem - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                respRoomDatasource.getByIdItem(1)
            ).thenReturn(
                Result.success(
                    RespRoomModel(
                        id = 1,
                        idHeader = 1,
                        idItem = 1,
                        idPlant = 1,
                        option = OptionResp.ACCORDING
                    )
                )
            )
            val result = repository.getRespByIdItem(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Resp(
                    id = 1,
                    idHeader = 1,
                    idItem = 1,
                    idPlant = 1,
                    option = OptionResp.ACCORDING
                )
            )
        }

    @Test
    fun `finishItems - Check return failure if have error in HeaderRoomDatasource checkOpen`() =
        runTest {
            whenever(
                headerRoomDatasource.checkOpen()
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.checkOpen",
                    "-",
                    Exception()
                )
            )
            val result = repository.finishItems(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.finishItems -> IHeaderRoomDatasource.checkOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `finishItems - Check return true if not have header open`() =
        runTest {
            whenever(
                headerRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            val result = repository.finishItems(1)
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
    fun `finishItems - Check return failure if have error in HeaderRoomDatasource getByStatus`() =
        runTest {
            whenever(
                headerRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerRoomDatasource.getIdByStatusOpen()
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.getByStatus",
                    "-",
                    Exception()
                )
            )
            val result = repository.finishItems(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.finishItems -> IHeaderRoomDatasource.getByStatus"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `finishItems - Check return failure if have error in RespRoomDatasource closeItems`() =
        runTest {
            whenever(
                headerRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerRoomDatasource.getIdByStatusOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                respRoomDatasource.finishItems(
                    idHeader = 1,
                    idPlant = 1
                )
            ).thenReturn(
                resultFailure(
                    "IRespRoomDatasource.finishItems",
                    "-",
                    Exception()
                )
            )
            val result = repository.finishItems(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.finishItems -> IRespRoomDatasource.finishItems"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `finishItems - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerRoomDatasource.getIdByStatusOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                respRoomDatasource.finishItems(
                    idHeader = 1,
                    idPlant = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.finishItems(1)
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
    fun `listRespByIdPlantAndHeaderOpen - Check return failure if have error in HeaderRoomDatasource getByStatus`() =
        runTest {
            whenever(
                headerRoomDatasource.getIdByStatusOpen()
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.getByStatusOpenDefault",
                    "-",
                    Exception()
                )
            )
            val result = repository.listRespByIdPlantAndHeaderOpen(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.listRespByIdPlantAndHeaderOpen -> IHeaderRoomDatasource.getByStatusOpenDefault"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listRespByIdPlantAndHeaderOpen - Check return failure if have error in RespRoomDatasource listByIdHeaderAndIdPlant`() =
        runTest {
            whenever(
                headerRoomDatasource.getIdByStatusOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                respRoomDatasource.listByIdHeaderAndIdPlant(
                    idHeader = 1,
                    idPlant = 1
                )
            ).thenReturn(
                resultFailure(
                    "IRespRoomDatasource.listByIdHeaderAndIdPlant",
                    "-",
                    Exception()
                )
            )
            val result = repository.listRespByIdPlantAndHeaderOpen(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.listRespByIdPlantAndHeaderOpen -> IRespRoomDatasource.listByIdHeaderAndIdPlant"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listRespByIdPlantAndHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.getIdByStatusOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                respRoomDatasource.listByIdHeaderAndIdPlant(
                    idHeader = 1,
                    idPlant = 1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        RespRoomModel(
                            id = 1,
                            idHeader = 1,
                            idItem = 1,
                            idPlant = 1,
                            option = OptionResp.ACCORDING
                        ),
                        RespRoomModel(
                            id = 2,
                            idHeader = 1,
                            idItem = 2,
                            idPlant = 1,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs"
                        )
                    )
                )
            )
            val result = repository.listRespByIdPlantAndHeaderOpen(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1,
                Resp(
                    id = 1,
                    idHeader = 1,
                    idItem = 1,
                    idPlant = 1,
                    option = OptionResp.ACCORDING
                )
            )
            val entity2 = list[1]
            assertEquals(
                entity2,
                Resp(
                    id = 2,
                    idHeader = 1,
                    idItem = 2,
                    idPlant = 1,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs"
                )
            )
        }

    @Test
    fun `listRespByHeaderOpen - Check return failure if have error in HeaderRoomDatasource getByStatusOpenDefault`() =
        runTest {
            whenever(
                headerRoomDatasource.getIdByStatusOpen()
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.getByStatusOpenDefault",
                    "-",
                    Exception()
                )
            )
            val result = repository.listRespByHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.listRespByHeaderOpen -> IHeaderRoomDatasource.getByStatusOpenDefault"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listRespByHeaderOpen - Check return failure if have error in RespRoomDatasource listByIdHeader`() =
        runTest {
            whenever(
                headerRoomDatasource.getIdByStatusOpen()
            ).thenReturn(
                Result.success(2)
            )
            whenever(
                respRoomDatasource.listByIdHeader(2)
            ).thenReturn(
                resultFailure(
                    "IRespRoomDatasource.listByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.listRespByHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.listRespByHeaderOpen -> IRespRoomDatasource.listByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listRespByHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.getIdByStatusOpen()
            ).thenReturn(
                Result.success(2)
            )
            whenever(
                respRoomDatasource.listByIdHeader(2)
            ).thenReturn(
                Result.success(
                    listOf(
                        RespRoomModel(
                            id = 1,
                            idHeader = 2,
                            idItem = 1,
                            idPlant = 1,
                            option = OptionResp.ACCORDING
                        )
                    )
                )
            )
            val result = repository.listRespByHeaderOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    Resp(
                        id = 1,
                        idHeader = 2,
                        idItem = 1,
                        idPlant = 1,
                        option = OptionResp.ACCORDING
                    )
                )
            )
        }

    @Test
    fun `finishHeader - Check return failure if have error in HeaderRoomDatasource finish`() =
        runTest {
            whenever(
                headerRoomDatasource.finish()
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.finish",
                    "-",
                    Exception()
                )
            )
            val result = repository.finishHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.finishHeader -> IHeaderRoomDatasource.finish"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `finishHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.finish()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.finishHeader()
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
    fun `listHeaderByIdOSList - Check return failure if have error in HeaderRoomDatasource listByIdOSList`() =
        runTest {
            val ids = listOf(1, 2, 3)
            whenever(
                headerRoomDatasource.listByIdOSList(ids)
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.listByIdOSList",
                    "-",
                    Exception()
                )
            )
            val result = repository.listHeaderByIdOSList(ids)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.listHeaderByIdOSList -> IHeaderRoomDatasource.listByIdOSList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listHeaderByIdOSList - Check return correct if function execute successfully`() =
        runTest {
            val ids = listOf(1, 2, 3)
            whenever(
                headerRoomDatasource.listByIdOSList(ids)
            ).thenReturn(
                Result.success(
                    listOf(
                        HeaderRoomModel(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date()
                        ),
                        HeaderRoomModel(
                            id = 2,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date()
                        ),
                        HeaderRoomModel(
                            id = 3,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date()
                        )
                    )
                )
            )
            val result = repository.listHeaderByIdOSList(ids)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                3
            )
            val entity1 = list[0]
            assertEquals(
                entity1.id,
                1
            )
            assertEquals(
                entity1.idColab,
                1
            )
            assertEquals(
                entity1.idFactorySection,
                1
            )
            assertEquals(
                entity1.idOS,
                1
            )
            assertEquals(
                entity1.status,
                Status.OPEN
            )
            val entity2 = list[1]
            assertEquals(
                entity2.id,
                2
            )
            assertEquals(
                entity2.idColab,
                1
            )
            assertEquals(
                entity2.idFactorySection,
                1
            )
            assertEquals(
                entity2.idOS,
                1
            )
            assertEquals(
                entity2.status,
                Status.OPEN
            )
            val entity3 = list[2]
            assertEquals(
                entity3.id,
                3
            )
            assertEquals(
                entity3.idColab,
                1
            )
            assertEquals(
                entity3.idFactorySection,
                1
            )
            assertEquals(
                entity3.idOS,
                1
            )
            assertEquals(
                entity3.status,
                Status.OPEN
            )

        }

    @Test
    fun `getIdColabHeaderOpen - Check return failure if have error in HeaderSharedPreferencesDatasource getIdColab`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.getIdColab()
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.getIdColab",
                    "-",
                    Exception()
                )
            )
            val result = repository.getIdColabHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.getIdColabHeaderOpen -> IHeaderSharedPreferencesDatasource.getIdColab"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getIdColabHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.getIdColab()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdColabHeaderOpen()
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
    fun `closeHeaders - Check return failure if have error in HeaderRoomDatasource close`() =
        runTest {
            whenever(
                headerRoomDatasource.close()
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.close",
                    "-",
                    Exception()
                )
            )
            val result = repository.closeHeaders()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.closeHeaders -> IHeaderRoomDatasource.close"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `closeHeaders - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.close()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.closeHeaders()
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
    fun `checkRespSend - Check return failure if have error in RespRoomDatasource checkRespSend`() =
        runTest {
            whenever(
                respRoomDatasource.checkRespSend()
            ).thenReturn(
                resultFailure(
                    "IRespRoomDatasource.checkRespSend",
                    "-",
                    Exception()
                )
            )
            val result = repository.checkRespSend()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.checkRespSend -> IRespRoomDatasource.checkRespSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkRespSend - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                respRoomDatasource.checkRespSend()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkRespSend()
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
    fun `sendNote - Check return failure if have error in RespRoomDatasource listRespSend`() =
        runTest {
            whenever(
                respRoomDatasource.listRespSend()
            ).thenReturn(
                resultFailure(
                    "IRespRoomDatasource.listRespSend",
                    "-",
                    Exception()
                )
            )
            val result = repository.sendNote(
                token = "token",
                number = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.sendNote -> IRespRoomDatasource.listRespSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `sendNote - Check return failure if have error in HeaderRoomDatasource listByIds`() =
        runTest {
            whenever(
                respRoomDatasource.listRespSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        RespRoomModel(
                            id = 1,
                            idHeader = 1,
                            idItem = 1,
                            idPlant = 1,
                            option = OptionResp.ACCORDING
                        ),
                        RespRoomModel(
                            id = 2,
                            idHeader = 2,
                            idItem = 2,
                            idPlant = 2,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs"
                        )
                    )
                )
            )
            whenever(
                headerRoomDatasource.listByIds(listOf(1, 2))
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.listByIds",
                    "-",
                    Exception()
                )
            )
            val result = repository.sendNote(
                token = "token",
                number = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.sendNote -> IHeaderRoomDatasource.listByIds"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `sendNote - Check return failure if have error in CheckListRetrofitDatasource send`() =
        runTest {
            whenever(
                respRoomDatasource.listRespSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        RespRoomModel(
                            id = 1,
                            idHeader = 1,
                            idItem = 1,
                            idPlant = 1,
                            option = OptionResp.ACCORDING
                        ),
                        RespRoomModel(
                            id = 2,
                            idHeader = 2,
                            idItem = 2,
                            idPlant = 2,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs"
                        ),
                        RespRoomModel(
                            id = 3,
                            idHeader = 2,
                            idItem = 3,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs 2"
                        )
                    )
                )
            )
            whenever(
                headerRoomDatasource.listByIds(listOf(1, 2))
            ).thenReturn(
                Result.success(
                    listOf(
                        HeaderRoomModel(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date(1755260418000)
                        ),
                        HeaderRoomModel(
                            id = 2,
                            idColab = 2,
                            idFactorySection = 2,
                            idOS = 2,
                            status = Status.OPEN,
                            dateHour = Date(1755174018000)
                        )
                    )
                )
            )
            val modelCaptor = argumentCaptor<List<HeaderRetrofitModelOutput>>().apply {
                whenever(
                    checkListRetrofitDatasource.send(
                        token = any(),
                        capture()
                    )
                ).thenReturn(
                    resultFailure(
                        "ICheckListRetrofitDatasource.send",
                        "-",
                        Exception()
                    )
                )
            }
            val result = repository.sendNote(
                token = "token",
                number = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.sendNote -> ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val headerList = modelCaptor.firstValue
            assertEquals(
                headerList.size,
                2
            )
            val header1 = headerList[0]
            assertEquals(
                header1.idColab,
                1
            )
            assertEquals(
                header1.idFactorySection,
                1
            )
            assertEquals(
                header1.idOS,
                1
            )
            assertEquals(
                header1.dateHour,
                "15/08/2025 09:20"
            )
            assertEquals(
                header1.number,
                1
            )
            val respListHeader1 = header1.respList
            assertEquals(
                respListHeader1.size,
                1
            )
            val resp1Header1 = respListHeader1[0]
            assertEquals(
                resp1Header1.id,
                1
            )
            assertEquals(
                resp1Header1.idHeader,
                1
            )
            assertEquals(
                resp1Header1.idItem,
                1
            )
            assertEquals(
                resp1Header1.idPlant,
                1
            )
            assertEquals(
                resp1Header1.option,
                OptionResp.ACCORDING
            )
            assertEquals(
                resp1Header1.obs,
                null
            )
            val header2 = headerList[1]
            assertEquals(
                header2.idColab,
                2
            )
            assertEquals(
                header2.idFactorySection,
                2
            )
            assertEquals(
                header2.idOS,
                2
            )
            assertEquals(
                header2.dateHour,
                "14/08/2025 09:20"
            )
            assertEquals(
                header2.number,
                1
            )
            val respListHeader2 = header2.respList
            assertEquals(
                respListHeader2.size,
                2
            )
            val resp1Header2 = respListHeader2[0]
            assertEquals(
                resp1Header2.id,
                2
            )
            assertEquals(
                resp1Header2.idHeader,
                2
            )
            assertEquals(
                resp1Header2.idItem,
                2
            )
            assertEquals(
                resp1Header2.idPlant,
                2
            )
            assertEquals(
                resp1Header2.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                resp1Header2.obs,
                "obs"
            )
            val resp2Header2 = respListHeader2[1]
            assertEquals(
                resp2Header2.id,
                3
            )
            assertEquals(
                resp2Header2.idHeader,
                2
            )
            assertEquals(
                resp2Header2.idItem,
                3
            )
            assertEquals(
                resp2Header2.idPlant,
                3
            )
            assertEquals(
                resp2Header2.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                resp2Header2.obs,
                "obs 2"
            )
        }
    
    @Test
    fun `sendNote - Check return failure if have error in RespRoomDatasource setIdServAndSentById`() =
        runTest {
            whenever(
                respRoomDatasource.listRespSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        RespRoomModel(
                            id = 1,
                            idHeader = 1,
                            idItem = 1,
                            idPlant = 1,
                            option = OptionResp.ACCORDING
                        ),
                        RespRoomModel(
                            id = 2,
                            idHeader = 2,
                            idItem = 2,
                            idPlant = 2,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs"
                        ),
                        RespRoomModel(
                            id = 3,
                            idHeader = 2,
                            idItem = 3,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs 2"
                        )
                    )
                )
            )
            whenever(
                headerRoomDatasource.listByIds(listOf(1, 2))
            ).thenReturn(
                Result.success(
                    listOf(
                        HeaderRoomModel(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date(1755260418000)
                        ),
                        HeaderRoomModel(
                            id = 2,
                            idColab = 2,
                            idFactorySection = 2,
                            idOS = 2,
                            status = Status.OPEN,
                            dateHour = Date(1755174018000)
                        )
                    )
                )
            )
            val modelCaptor = argumentCaptor<List<HeaderRetrofitModelOutput>>().apply {
                whenever(
                    checkListRetrofitDatasource.send(
                        token = any(),
                        capture()
                    )
                ).thenReturn(
                    Result.success(
                        listOf(
                            HeaderRetrofitModelInput(
                                id = 1,
                                idServ = 1,
                                respList = listOf(
                                    RespRetrofitModelInput(
                                        id = 1,
                                        idServ = 1
                                    )
                                )
                            )
                        )
                    )
                )
            }
            whenever(
                respRoomDatasource.setIdServAndSentById(
                    idServ = 1,
                    id = 1
                )
            ).thenReturn(
                resultFailure(
                    "IRespRoomDatasource.setIdServAndSentById",
                    "-",
                    Exception()
                )
            )
            val result = repository.sendNote(
                token = "token",
                number = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.sendNote -> IRespRoomDatasource.setIdServAndSentById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val headerList = modelCaptor.firstValue
            assertEquals(
                headerList.size,
                2
            )
            val header1 = headerList[0]
            assertEquals(
                header1.idColab,
                1
            )
            assertEquals(
                header1.idFactorySection,
                1
            )
            assertEquals(
                header1.idOS,
                1
            )
            assertEquals(
                header1.dateHour,
                "15/08/2025 09:20"
            )
            assertEquals(
                header1.number,
                1
            )
            val respListHeader1 = header1.respList
            assertEquals(
                respListHeader1.size,
                1
            )
            val resp1Header1 = respListHeader1[0]
            assertEquals(
                resp1Header1.id,
                1
            )
            assertEquals(
                resp1Header1.idHeader,
                1
            )
            assertEquals(
                resp1Header1.idItem,
                1
            )
            assertEquals(
                resp1Header1.idPlant,
                1
            )
            assertEquals(
                resp1Header1.option,
                OptionResp.ACCORDING
            )
            assertEquals(
                resp1Header1.obs,
                null
            )
            val header2 = headerList[1]
            assertEquals(
                header2.idColab,
                2
            )
            assertEquals(
                header2.idFactorySection,
                2
            )
            assertEquals(
                header2.idOS,
                2
            )
            assertEquals(
                header2.dateHour,
                "14/08/2025 09:20"
            )
            assertEquals(
                header2.number,
                1
            )
            val respListHeader2 = header2.respList
            assertEquals(
                respListHeader2.size,
                2
            )
            val resp1Header2 = respListHeader2[0]
            assertEquals(
                resp1Header2.id,
                2
            )
            assertEquals(
                resp1Header2.idHeader,
                2
            )
            assertEquals(
                resp1Header2.idItem,
                2
            )
            assertEquals(
                resp1Header2.idPlant,
                2
            )
            assertEquals(
                resp1Header2.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                resp1Header2.obs,
                "obs"
            )
            val resp2Header2 = respListHeader2[1]
            assertEquals(
                resp2Header2.id,
                3
            )
            assertEquals(
                resp2Header2.idHeader,
                2
            )
            assertEquals(
                resp2Header2.idItem,
                3
            )
            assertEquals(
                resp2Header2.idPlant,
                3
            )
            assertEquals(
                resp2Header2.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                resp2Header2.obs,
                "obs 2"
            )
        }

    @Test
    fun `sendNote - Check return failure if have error in HeaderRoomDatasource setIdServById`() =
        runTest {
            whenever(
                respRoomDatasource.listRespSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        RespRoomModel(
                            id = 1,
                            idHeader = 1,
                            idItem = 1,
                            idPlant = 1,
                            option = OptionResp.ACCORDING
                        ),
                        RespRoomModel(
                            id = 2,
                            idHeader = 2,
                            idItem = 2,
                            idPlant = 2,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs"
                        ),
                        RespRoomModel(
                            id = 3,
                            idHeader = 2,
                            idItem = 3,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs 2"
                        )
                    )
                )
            )
            whenever(
                headerRoomDatasource.listByIds(listOf(1, 2))
            ).thenReturn(
                Result.success(
                    listOf(
                        HeaderRoomModel(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date(1755260418000)
                        ),
                        HeaderRoomModel(
                            id = 2,
                            idColab = 2,
                            idFactorySection = 2,
                            idOS = 2,
                            status = Status.OPEN,
                            dateHour = Date(1755174018000)
                        )
                    )
                )
            )
            val modelCaptor = argumentCaptor<List<HeaderRetrofitModelOutput>>().apply {
                whenever(
                    checkListRetrofitDatasource.send(
                        token = any(),
                        capture()
                    )
                ).thenReturn(
                    Result.success(
                        listOf(
                            HeaderRetrofitModelInput(
                                id = 1,
                                idServ = 1,
                                respList = listOf(
                                    RespRetrofitModelInput(
                                        id = 1,
                                        idServ = 1
                                    )
                                )
                            )
                        )
                    )
                )
            }
            whenever(
                respRoomDatasource.setIdServAndSentById(
                    idServ = 1,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerRoomDatasource.setIdServById(
                    idServ = 1,
                    id = 1
                )
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.setIdServById",
                    "-",
                    Exception()
                )
            )
            val result = repository.sendNote(
                token = "token",
                number = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.sendNote -> IHeaderRoomDatasource.setIdServById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val headerList = modelCaptor.firstValue
            assertEquals(
                headerList.size,
                2
            )
            val header1 = headerList[0]
            assertEquals(
                header1.idColab,
                1
            )
            assertEquals(
                header1.idFactorySection,
                1
            )
            assertEquals(
                header1.idOS,
                1
            )
            assertEquals(
                header1.dateHour,
                "15/08/2025 09:20"
            )
            assertEquals(
                header1.number,
                1
            )
            val respListHeader1 = header1.respList
            assertEquals(
                respListHeader1.size,
                1
            )
            val resp1Header1 = respListHeader1[0]
            assertEquals(
                resp1Header1.id,
                1
            )
            assertEquals(
                resp1Header1.idHeader,
                1
            )
            assertEquals(
                resp1Header1.idItem,
                1
            )
            assertEquals(
                resp1Header1.idPlant,
                1
            )
            assertEquals(
                resp1Header1.option,
                OptionResp.ACCORDING
            )
            assertEquals(
                resp1Header1.obs,
                null
            )
            val header2 = headerList[1]
            assertEquals(
                header2.idColab,
                2
            )
            assertEquals(
                header2.idFactorySection,
                2
            )
            assertEquals(
                header2.idOS,
                2
            )
            assertEquals(
                header2.dateHour,
                "14/08/2025 09:20"
            )
            assertEquals(
                header2.number,
                1
            )
            val respListHeader2 = header2.respList
            assertEquals(
                respListHeader2.size,
                2
            )
            val resp1Header2 = respListHeader2[0]
            assertEquals(
                resp1Header2.id,
                2
            )
            assertEquals(
                resp1Header2.idHeader,
                2
            )
            assertEquals(
                resp1Header2.idItem,
                2
            )
            assertEquals(
                resp1Header2.idPlant,
                2
            )
            assertEquals(
                resp1Header2.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                resp1Header2.obs,
                "obs"
            )
            val resp2Header2 = respListHeader2[1]
            assertEquals(
                resp2Header2.id,
                3
            )
            assertEquals(
                resp2Header2.idHeader,
                2
            )
            assertEquals(
                resp2Header2.idItem,
                3
            )
            assertEquals(
                resp2Header2.idPlant,
                3
            )
            assertEquals(
                resp2Header2.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                resp2Header2.obs,
                "obs 2"
            )
        }

    @Test
    fun `sendNote - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                respRoomDatasource.listRespSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        RespRoomModel(
                            id = 1,
                            idHeader = 1,
                            idItem = 1,
                            idPlant = 1,
                            option = OptionResp.ACCORDING
                        ),
                        RespRoomModel(
                            id = 2,
                            idHeader = 2,
                            idItem = 2,
                            idPlant = 2,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs"
                        ),
                        RespRoomModel(
                            id = 3,
                            idHeader = 2,
                            idItem = 3,
                            idPlant = 3,
                            option = OptionResp.NON_CONFORMING,
                            obs = "obs 2"
                        )
                    )
                )
            )
            whenever(
                headerRoomDatasource.listByIds(listOf(1, 2))
            ).thenReturn(
                Result.success(
                    listOf(
                        HeaderRoomModel(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date(1755260418000)
                        ),
                        HeaderRoomModel(
                            id = 2,
                            idColab = 2,
                            idFactorySection = 2,
                            idOS = 2,
                            status = Status.OPEN,
                            dateHour = Date(1755174018000)
                        )
                    )
                )
            )
            val modelCaptor = argumentCaptor<List<HeaderRetrofitModelOutput>>().apply {
                whenever(
                    checkListRetrofitDatasource.send(
                        token = any(),
                        capture()
                    )
                ).thenReturn(
                    Result.success(
                        listOf(
                            HeaderRetrofitModelInput(
                                id = 1,
                                idServ = 1,
                                respList = listOf(
                                    RespRetrofitModelInput(
                                        id = 1,
                                        idServ = 1
                                    )
                                )
                            )
                        )
                    )
                )
            }
            whenever(
                respRoomDatasource.setIdServAndSentById(
                    idServ = 1,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerRoomDatasource.setIdServById(
                    idServ = 1,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.sendNote(
                token = "token",
                number = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val headerList = modelCaptor.firstValue
            assertEquals(
                headerList.size,
                2
            )
            val header1 = headerList[0]
            assertEquals(
                header1.idColab,
                1
            )
            assertEquals(
                header1.idFactorySection,
                1
            )
            assertEquals(
                header1.idOS,
                1
            )
            assertEquals(
                header1.dateHour,
                "15/08/2025 09:20"
            )
            assertEquals(
                header1.number,
                1
            )
            val respListHeader1 = header1.respList
            assertEquals(
                respListHeader1.size,
                1
            )
            val resp1Header1 = respListHeader1[0]
            assertEquals(
                resp1Header1.id,
                1
            )
            assertEquals(
                resp1Header1.idHeader,
                1
            )
            assertEquals(
                resp1Header1.idItem,
                1
            )
            assertEquals(
                resp1Header1.idPlant,
                1
            )
            assertEquals(
                resp1Header1.option,
                OptionResp.ACCORDING
            )
            assertEquals(
                resp1Header1.obs,
                null
            )
            val header2 = headerList[1]
            assertEquals(
                header2.idColab,
                2
            )
            assertEquals(
                header2.idFactorySection,
                2
            )
            assertEquals(
                header2.idOS,
                2
            )
            assertEquals(
                header2.dateHour,
                "14/08/2025 09:20"
            )
            assertEquals(
                header2.number,
                1
            )
            val respListHeader2 = header2.respList
            assertEquals(
                respListHeader2.size,
                2
            )
            val resp1Header2 = respListHeader2[0]
            assertEquals(
                resp1Header2.id,
                2
            )
            assertEquals(
                resp1Header2.idHeader,
                2
            )
            assertEquals(
                resp1Header2.idItem,
                2
            )
            assertEquals(
                resp1Header2.idPlant,
                2
            )
            assertEquals(
                resp1Header2.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                resp1Header2.obs,
                "obs"
            )
            val resp2Header2 = respListHeader2[1]
            assertEquals(
                resp2Header2.id,
                3
            )
            assertEquals(
                resp2Header2.idHeader,
                2
            )
            assertEquals(
                resp2Header2.idItem,
                3
            )
            assertEquals(
                resp2Header2.idPlant,
                3
            )
            assertEquals(
                resp2Header2.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                resp2Header2.obs,
                "obs 2"
            )
        }

    @Test
    fun `allHeader - Check return failure if have error in HeaderRoomDatasource all`() =
        runTest {
            whenever(
                headerRoomDatasource.all()
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.all",
                    "-",
                    Exception()
                )
            )
            val result = repository.allHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.allHeader -> IHeaderRoomDatasource.all"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `allHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.all()
            ).thenReturn(
                Result.success(
                    listOf(
                        HeaderRoomModel(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.OPEN,
                            dateHour = Date()
                        )
                    )
                )
            )
            val result = repository.allHeader()
            assertEquals(
                result.isSuccess,
                true
            )
            val headerList = result.getOrNull()!!
            assertEquals(
                headerList.size,
                1
            )
            val header1 = headerList[0]
            assertEquals(
                header1.id,
                1
            )
            assertEquals(
                header1.idColab,
                1
            )
            assertEquals(
                header1.idFactorySection,
                1
            )
            assertEquals(
                header1.idOS,
                1
            )
            assertEquals(
                header1.status,
                Status.OPEN
            )
        }

    @Test
    fun `deleteRespByIdHeader - Check return failure if have error in RespRoomDatasource deleteRespByIdHeader`() =
        runTest {
            whenever(
                respRoomDatasource.deleteByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IRespRoomDatasource.deleteByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.deleteRespByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.deleteRespByIdHeader -> IRespRoomDatasource.deleteByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteRespByIdHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                respRoomDatasource.deleteByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteRespByIdHeader(1)
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
    fun `deleteHeader - Check return failure if have error in HeaderRoomDatasource delete`() =
        runTest {
            whenever(
                headerRoomDatasource.delete(1)
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.delete",
                    "-",
                    Exception()
                )
            )
            val result = repository.deleteHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.deleteHeader -> IHeaderRoomDatasource.delete"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.delete(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteHeader(1)
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
    fun `checkHeaderOpen - Check return failure if have error in HeaderRoomDatasource checkOpen`() =
        runTest {
            whenever(
                headerRoomDatasource.checkOpen()
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.checkOpen",
                    "-",
                    Exception()
                )
            )
            val result = repository.checkHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRepository.checkHeaderOpen -> IHeaderRoomDatasource.checkOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkHeaderOpen()
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