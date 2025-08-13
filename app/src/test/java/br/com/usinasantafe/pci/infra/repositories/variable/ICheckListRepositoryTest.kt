package br.com.usinasantafe.pci.infra.repositories.variable

import br.com.usinasantafe.pci.domain.entities.variable.Resp
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.pci.infra.datasource.room.variable.RespRoomDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.infra.models.sharedpreferences.HeaderSharedPreferencesModel
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.Status
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICheckListRepositoryTest {

    private val headerSharedPreferencesDatasource = mock<HeaderSharedPreferencesDatasource>()
    private val headerRoomDatasource = mock<HeaderRoomDatasource>()
    private val respRoomDatasource = mock<RespRoomDatasource>()
    private val repository = ICheckListRepository(
        headerSharedPreferencesDatasource = headerSharedPreferencesDatasource,
        headerRoomDatasource = headerRoomDatasource,
        respRoomDatasource = respRoomDatasource
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

    @Test
    fun `saveResp - Check return failure if have error in HeaderRoomDatasource getByStatus`() =
        runTest {
            whenever(
                headerRoomDatasource.getByStatusOpenDefault(Status.OPEN)
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
                headerRoomDatasource.getByStatusOpenDefault(Status.OPEN)
            ).thenReturn(
                Result.success(
                    HeaderRoomModel(
                        id = 1,
                        idColab = 1,
                        idFactorySection = 1,
                        idOS = 1,
                        status = Status.OPEN,
                    )
                )
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
                headerRoomDatasource.getByStatusOpenDefault(Status.OPEN)
            ).thenReturn(
                Result.success(
                    HeaderRoomModel(
                        id = 1,
                        idColab = 1,
                        idFactorySection = 1,
                        idOS = 1,
                        status = Status.OPEN,
                    )
                )
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
    fun `finishItems - Check return failure if have error in HeaderRoomDatasource getByStatus`() =
        runTest {
            whenever(
                headerRoomDatasource.getByStatusOpenDefault(Status.OPEN)
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
                headerRoomDatasource.getByStatusOpenDefault(Status.OPEN)
            ).thenReturn(
                Result.success(
                    HeaderRoomModel(
                        id = 1,
                        idColab = 1,
                        idFactorySection = 1,
                        idOS = 1,
                        status = Status.OPEN,
                    )
                )
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
                headerRoomDatasource.getByStatusOpenDefault(Status.OPEN)
            ).thenReturn(
                Result.success(
                    HeaderRoomModel(
                        id = 1,
                        idColab = 1,
                        idFactorySection = 1,
                        idOS = 1,
                        status = Status.OPEN,
                    )
                )
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
                headerRoomDatasource.getByStatusOpenDefault()
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
                headerRoomDatasource.getByStatusOpenDefault()
            ).thenReturn(
                Result.success(
                    HeaderRoomModel(
                        id = 1,
                        idColab = 1,
                        idFactorySection = 1,
                        idOS = 1,
                        status = Status.OPEN,
                    )
                )
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
                headerRoomDatasource.getByStatusOpenDefault()
            ).thenReturn(
                Result.success(
                    HeaderRoomModel(
                        id = 1,
                        idColab = 1,
                        idFactorySection = 1,
                        idOS = 1,
                        status = Status.OPEN,
                    )
                )
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
                headerRoomDatasource.getByStatusOpenDefault()
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
                headerRoomDatasource.getByStatusOpenDefault()
            ).thenReturn(
                Result.success(
                    HeaderRoomModel(
                        id = 2,
                        idColab = 1,
                        idFactorySection = 1,
                        idOS = 1,
                        status = Status.OPEN,
                    )
                )
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
                headerRoomDatasource.getByStatusOpenDefault()
            ).thenReturn(
                Result.success(
                    HeaderRoomModel(
                        id = 2,
                        idColab = 1,
                        idFactorySection = 1,
                        idOS = 1,
                        status = Status.OPEN,
                    )
                )
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
}