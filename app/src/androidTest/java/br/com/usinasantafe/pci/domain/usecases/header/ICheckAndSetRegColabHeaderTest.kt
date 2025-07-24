package br.com.usinasantafe.pci.domain.usecases.header

import br.com.usinasantafe.pci.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.pci.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ICheckAndSetRegColabHeaderTest {

    private val resultColabInvalid = """
          {"idColab":0,"regColab":0,"nameColab":"INVALID","idFactorySectionColab":0}
    """.trimIndent()

    private val resultColab = """
        {"idColab":1,"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO","idFactorySectionColab":1}
    """.trimIndent()


    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ICheckAndSetRegColabHeader

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var colabDao: ColabDao

    @Test
    fun check_return_failure_if_not_have_data_in_config_internal() =
        runTest {
            hiltRule.inject()
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckAndSetRegColabHeader -> IGetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_web_service_return_nothing() =
        runTest {
            hiltRule.inject()
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    version = "1.00",
                    idBD = 1
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckAndSetRegColabHeader -> IColabRepository.getByRegColab -> IColabRetrofitDatasource.getByReg"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080"
            )
        }

    @Test
    fun check_return_failure_if_web_service_return_incorrect() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            BaseUrlModuleTest.url = server.url("/").toString()
            hiltRule.inject()
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    version = "1.00",
                    idBD = 1
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckAndSetRegColabHeader -> IColabRepository.getByRegColab -> IColabRetrofitDatasource.getByReg"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 4 path \$."
            )
        }

    @Test
    fun check_return_failure_if_reg_is_invalid() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultColabInvalid)
            )
            BaseUrlModuleTest.url = server.url("/").toString()
            hiltRule.inject()
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    version = "1.00",
                    idBD = 1
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
            val list = colabDao.all()
            assertEquals(
                list.size,
                0
            )
        }

    @Test
    fun check_return_true_if_process_execute_success() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultColab)
            )
            BaseUrlModuleTest.url = server.url("/").toString()
            hiltRule.inject()
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    version = "1.00",
                    idBD = 1
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val list = colabDao.all()
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.idColab,
                1
            )
            assertEquals(
                entity.regColab,
                19759L
            )
            assertEquals(
                entity.nameColab,
                "ANDERSON DA SILVA DELGADO"
            )
            assertEquals(
                entity.idFactorySectionColab,
                1
            )
        }

}