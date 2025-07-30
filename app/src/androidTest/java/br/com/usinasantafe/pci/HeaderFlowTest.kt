package br.com.usinasantafe.pci

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.pci.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.pci.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.presenter.MainActivity
import br.com.usinasantafe.pci.utils.FlagUpdate
import br.com.usinasantafe.pci.utils.WEB_GET_COLAB_BY_REG
import br.com.usinasantafe.pci.utils.WEB_LIST_OS_BY_ID_FACTORY_SECTION
import br.com.usinasantafe.pci.utils.WEB_LIST_PLANT_BY_ID_FACTORY_SECTION
import br.com.usinasantafe.pci.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class HeaderFlowTest {

    companion object {

        private lateinit var server: MockWebServer

        @BeforeClass
        @JvmStatic
        fun setupClass() {

            val resultColabByReg = """
                {"idColab":1,"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO","idFactorySectionColab":1}
            """.trimIndent()

            val resultOSList = """
                [
                    {"idOS":1,"nroOS":1,"idPlantOS":1,"qtdDayOS":1,"descPeriodOS":"DIARIO","idFactorySectionOS":1},
                    {"idOS":2,"nroOS":2,"idPlantOS":2,"qtdDayOS":2,"descPeriodOS":"SEMANAL","idFactorySectionOS":1}
                ]
            """.trimIndent()

            val resultPlantList = """
                [
                    {"idPlant":1,"codPlant":"01","descPlant":"PLANTA 01","idFactorySectionPlant":1},
                    {"idPlant":2,"codPlant":"02","descPlant":"PLANTA 02","idFactorySectionPlant":1}
                ]
            """.trimIndent()

            val dispatcherSuccess: Dispatcher = object : Dispatcher() {

                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_GET_COLAB_BY_REG" -> MockResponse().setBody(resultColabByReg)
                        "/$WEB_LIST_OS_BY_ID_FACTORY_SECTION" -> MockResponse().setBody(resultOSList)
                        "/$WEB_LIST_PLANT_BY_ID_FACTORY_SECTION" -> MockResponse().setBody(resultPlantList)
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }

            val server = MockWebServer()
            server.dispatcher = dispatcherSuccess
            server.start()

            BaseUrlModuleTest.url = server.url("/").toString()


        }

        @AfterClass
        @JvmStatic
        fun tearDownClass() {
            server.shutdown()
        }
    }

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var colabDao: ColabDao

    @Inject
    lateinit var headerSharedPreferencesDatasource: HeaderSharedPreferencesDatasource

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun flow_header() = runTest(
        timeout = 10.minutes
    ) {

        initialRegister()

        Log.d("TestDebug", "Position 1")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1")
            .performClick()
        composeTestRule.onNodeWithText("9")
            .performClick()
        composeTestRule.onNodeWithText("7")
            .performClick()
        composeTestRule.onNodeWithText("5")
            .performClick()
        composeTestRule.onNodeWithText("9")
            .performClick()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

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

        val resultGetHeaderIdColabAndIdFactorySection = headerSharedPreferencesDatasource.get()
        assertEquals(
            resultGetHeaderIdColabAndIdFactorySection.isSuccess,
            true
        )
        val headerIdColabAndIdFactorySection = resultGetHeaderIdColabAndIdFactorySection.getOrNull()!!
        assertEquals(
            headerIdColabAndIdFactorySection.idColab,
            1
        )
        assertEquals(
            headerIdColabAndIdFactorySection.idFactorySection,
            1
        )

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("RETORNAR")
            .performClick()

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1")
            .performClick()
        composeTestRule.onNodeWithText("9")
            .performClick()
        composeTestRule.onNodeWithText("7")
            .performClick()
        composeTestRule.onNodeWithText("5")
            .performClick()
        composeTestRule.onNodeWithText("9")
            .performClick()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_1")
            .performClick()

        Log.d("TestDebug", "Position 10")

        composeTestRule.waitUntilTimeout(10_000)

    }

    private suspend fun initialRegister() {
        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                idBD = 1,
                version = "1.0",
                flagUpdate = FlagUpdate.UPDATED
            )
        )
    }
}