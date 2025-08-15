package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.pci.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.StatusSend
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject

@HiltAndroidTest
class ISendNoteTest {


    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ISendNote

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerDao: HeaderDao

    @Inject
    lateinit var respDao: RespDao

    @Test
    fun check_return_failure_if_not_have_data_in_config_internal() =
        runTest {

            hiltRule.inject()

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendNote -> IGetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_web_service_errors_connection() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendNote -> ICheckListRepository.sendNote -> ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080"
            )
        }

    @Test
    fun check_return_failure_if_have_error_404() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setResponseCode(404))
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendNote -> ICheckListRepository.sendNote -> ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_have_failure_connection() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("Failure Connection BD"))
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendNote -> ICheckListRepository.sendNote -> ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$"
            )
        }

    @Test
    fun check_return_failure_if_sent_data_incorrect() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""{"id":1a}"""))
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendNote -> ICheckListRepository.sendNote -> ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
        }

    @Test
    fun check_return_true_if_process_execute_successfully_and_send_empty_and_return_empty() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""[]"""))
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

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

    @Test
    fun check_return_true_if_process_execute_successfully() =
        runTest {
            val response = """
                [
                    {
                        "id": 2,
                        "idServ": 10000,
                        "respList": [
                            {
                                "id": 3,
                                "idServ": 120000
                            }
                        ]
                    }
                ]
            """.trimIndent()
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody(response))
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )

            val headerRoomModelList = headerDao.all()
            assertEquals(
                headerRoomModelList.size,
                2
            )
            val headerRoomModel1 = headerRoomModelList[0]
            assertEquals(
                headerRoomModel1.id,
                1
            )
            assertEquals(
                headerRoomModel1.idColab,
                1
            )
            assertEquals(
                headerRoomModel1.idFactorySection,
                1
            )
            assertEquals(
                headerRoomModel1.idOS,
                1
            )
            assertEquals(
                headerRoomModel1.idServ,
                null
            )
            val headerRoomModel2 = headerRoomModelList[1]
            assertEquals(
                headerRoomModel2.id,
                2
            )
            assertEquals(
                headerRoomModel2.idColab,
                2
            )
            assertEquals(
                headerRoomModel2.idFactorySection,
                1
            )
            assertEquals(
                headerRoomModel2.idOS,
                2
            )
            assertEquals(
                headerRoomModel2.idServ,
                10000
            )
            val respRoomModelList = respDao.all()
            assertEquals(
                respRoomModelList.size,
                3
            )
            val respRoomModel1 = respRoomModelList[0]
            assertEquals(
                respRoomModel1.id,
                1
            )
            assertEquals(
                respRoomModel1.idHeader,
                1
            )
            assertEquals(
                respRoomModel1.idItem,
                1
            )
            assertEquals(
                respRoomModel1.idPlant,
                1
            )
            assertEquals(
                respRoomModel1.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                respRoomModel1.obs,
                "OK"
            )
            assertEquals(
                respRoomModel1.idServ,
                null
            )
            assertEquals(
                respRoomModel1.statusSend,
                StatusSend.SENT
            )
            val respRoomModel2 = respRoomModelList[1]
            assertEquals(
                respRoomModel2.id,
                2
            )
            assertEquals(
                respRoomModel2.idHeader,
                2
            )
            assertEquals(
                respRoomModel2.idItem,
                2
            )
            assertEquals(
                respRoomModel2.idPlant,
                2
            )
            assertEquals(
                respRoomModel2.option,
                OptionResp.ACCORDING
            )
            assertEquals(
                respRoomModel2.obs,
                null
            )
            assertEquals(
                respRoomModel2.idServ,
                null
            )
            assertEquals(
                respRoomModel2.statusSend,
                StatusSend.SENT
            )
            val respRoomModel3 = respRoomModelList[2]
            assertEquals(
                respRoomModel3.id,
                3
            )
            assertEquals(
                respRoomModel3.idHeader,
                2
            )
            assertEquals(
                respRoomModel3.idItem,
                3
            )
            assertEquals(
                respRoomModel3.idPlant,
                3
            )
            assertEquals(
                respRoomModel3.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                respRoomModel3.obs,
                "OK"
            )
            assertEquals(
                respRoomModel3.idServ,
                120000
            )
        }

    private suspend fun initialRegister(level: Int) {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                idBD = 1,
                number = 16997417840,
                version = "1.0",
                password = "12345",
            )
        )

        if (level == 1) return

        headerDao.insert(
            HeaderRoomModel(
                id = 1,
                idColab = 1,
                idFactorySection = 1,
                idOS = 1,
                dateHour = Date(),
            )
        )
        headerDao.insert(
            HeaderRoomModel(
                id = 2,
                idColab = 2,
                idFactorySection = 1,
                idOS = 2,
                dateHour = Date(),
            )
        )
        respDao.insert(
            RespRoomModel(
                id = 1,
                idHeader = 1,
                idItem = 1,
                idPlant = 1,
                option = OptionResp.NON_CONFORMING,
                obs = "OK",
                idServ = null,
                statusSend = StatusSend.SENT
            )
        )
        respDao.insert(
            RespRoomModel(
                id = 2,
                idHeader = 2,
                idItem = 2,
                idPlant = 2,
                option = OptionResp.ACCORDING,
                obs = null,
                idServ = null,
                statusSend = StatusSend.SENT
            )
        )
        respDao.insert(
            RespRoomModel(
                id = 3,
                idHeader = 2,
                idItem = 3,
                idPlant = 3,
                option = OptionResp.NON_CONFORMING,
                obs = "OK",
                statusSend = StatusSend.SEND
            )
        )

        if (level == 2) return

    }

}