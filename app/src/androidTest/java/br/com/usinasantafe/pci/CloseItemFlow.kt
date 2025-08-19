package br.com.usinasantafe.pci

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.pci.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.pci.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pci.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.presenter.MainActivity
import br.com.usinasantafe.pci.presenter.theme.TAG_BUTTON_YES_ALERT_DIALOG_CHECK
import br.com.usinasantafe.pci.utils.FlagUpdate
import br.com.usinasantafe.pci.utils.WEB_ALL_COMPONENT
import br.com.usinasantafe.pci.utils.WEB_ALL_SERVICE
import br.com.usinasantafe.pci.utils.WEB_GET_COLAB_BY_REG
import br.com.usinasantafe.pci.utils.WEB_LIST_ITEM_BY_ID_OS
import br.com.usinasantafe.pci.utils.WEB_LIST_OS_BY_ID_FACTORY_SECTION
import br.com.usinasantafe.pci.utils.WEB_LIST_PLANT_BY_ID_FACTORY_SECTION
import br.com.usinasantafe.pci.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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
class CloseItemFlow {

    companion object {

        private lateinit var server: MockWebServer

        @BeforeClass
        @JvmStatic
        fun setupClass() {

            val resultColabByReg = """
                {"idColab":2,"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO","idFactorySectionColab":1}
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
                    {"idPlant":2,"codPlant":"02","descPlant":"PLANTA 02","idFactorySectionPlant":1},
                    {"idPlant":3,"codPlant":"03","descPlant":"PLANTA 03","idFactorySectionPlant":1}
                ]
            """.trimIndent()

            val resultItemList = """
                [
                    {"idItem":1,"seqItem":1,"idOSItem":1,"idPlantItem":1,"idComponentItem":0,"idServiceItem":1},
                    {"idItem":2,"seqItem":2,"idOSItem":1,"idPlantItem":1,"idComponentItem":1,"idServiceItem":1},
                    {"idItem":3,"seqItem":3,"idOSItem":1,"idPlantItem":1,"idComponentItem":1,"idServiceItem":1},
                    {"idItem":4,"seqItem":4,"idOSItem":1,"idPlantItem":1,"idComponentItem":2,"idServiceItem":2},
                    {"idItem":5,"seqItem":5,"idOSItem":1,"idPlantItem":1,"idComponentItem":3,"idServiceItem":2},
                    {"idItem":6,"seqItem":6,"idOSItem":1,"idPlantItem":2,"idComponentItem":4,"idServiceItem":3},
                    {"idItem":7,"seqItem":7,"idOSItem":1,"idPlantItem":2,"idComponentItem":5,"idServiceItem":4},
                    {"idItem":8,"seqItem":8,"idOSItem":1,"idPlantItem":2,"idComponentItem":5,"idServiceItem":5},
                    {"idItem":9,"seqItem":9,"idOSItem":1,"idPlantItem":2,"idComponentItem":6,"idServiceItem":5},
                    {"idItem":10,"seqItem":10,"idOSItem":1,"idPlantItem":2,"idComponentItem":6,"idServiceItem":5},
                    {"idItem":11,"seqItem":11,"idOSItem":1,"idPlantItem":3,"idComponentItem":7,"idServiceItem":5},
                    {"idItem":12,"seqItem":12,"idOSItem":1,"idPlantItem":3,"idComponentItem":8,"idServiceItem":5},
                    {"idItem":13,"seqItem":13,"idOSItem":1,"idPlantItem":3,"idComponentItem":0,"idServiceItem":6}
                ]
            """.trimIndent()

            val resultServiceRetrofit = """
                [
                    {"idService":1,"codService":1,"descService":"SERVICE 1"},
                    {"idService":2,"codService":2,"descService":"SERVICE 2"},
                    {"idService":3,"codService":3,"descService":"SERVICE 3"},
                    {"idService":4,"codService":4,"descService":"SERVICE 4"},
                    {"idService":5,"codService":5,"descService":"SERVICE 5"},
                    {"idService":6,"codService":6,"descService":"SERVICE 6"}
                ]
            """.trimIndent()

            val resultComponentRetrofit = """
                [
                    {"idComponent":1,"codComponent":"01","descComponent":"COMPONENT 1"},
                    {"idComponent":2,"codComponent":"02","descComponent":"COMPONENT 2"},
                    {"idComponent":3,"codComponent":"03","descComponent":"COMPONENT 3"},
                    {"idComponent":4,"codComponent":"04","descComponent":"COMPONENT 4"},
                    {"idComponent":5,"codComponent":"05","descComponent":"COMPONENT 5"},
                    {"idComponent":6,"codComponent":"06","descComponent":"COMPONENT 6"},
                    {"idComponent":7,"codComponent":"07","descComponent":"COMPONENT 7"},
                    {"idComponent":8,"codComponent":"08","descComponent":"COMPONENT 8"}
                ]
            """.trimIndent()


            val dispatcherSuccess: Dispatcher = object : Dispatcher() {

                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_GET_COLAB_BY_REG" -> MockResponse().setBody(resultColabByReg)
                        "/$WEB_LIST_OS_BY_ID_FACTORY_SECTION" -> MockResponse().setBody(resultOSList)
                        "/$WEB_LIST_PLANT_BY_ID_FACTORY_SECTION" -> MockResponse().setBody(resultPlantList)
                        "/$WEB_LIST_ITEM_BY_ID_OS" -> MockResponse().setBody(resultItemList)
                        "/$WEB_ALL_COMPONENT" -> MockResponse().setBody(resultComponentRetrofit)
                        "/$WEB_ALL_SERVICE" -> MockResponse().setBody(resultServiceRetrofit)
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

    @Inject
    lateinit var headerDao: HeaderDao

    @Inject
    lateinit var respDao: RespDao

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

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("APONTAMENTO")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout()

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

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithTag("item_list_1")
            .performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithTag("item_list_2")
            .performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithTag("item_list_6")
            .performClick()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("CONFORME")
            .performClick()

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithTag("item_list_7")
            .performClick()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("CONFORME")
            .performClick()

        Log.d("TestDebug", "Position 10")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("RETORNAR")
            .performClick()

        Log.d("TestDebug", "Position 11")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("RETORNAR")
            .performClick()

        Log.d("TestDebug", "Position 12")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("RETORNAR")
            .performClick()

        Log.d("TestDebug", "Position 13")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
            .performClick()

        Log.d("TestDebug", "Position 14")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 15")

        composeTestRule.waitUntilTimeout()

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

        Log.d("TestDebug", "Position 16")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithTag("item_list_1")
            .performClick()

        Log.d("TestDebug", "Position 17")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithTag("item_list_2")
            .performClick()

        Log.d("TestDebug", "Position 18")

        composeTestRule.waitUntilTimeout()

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