package br.com.usinasantafe.pci

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pci.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.pci.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.presenter.MainActivity
import br.com.usinasantafe.pci.presenter.view.configuration.config.TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.pci.presenter.view.configuration.config.TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.pci.utils.FlagUpdate
import br.com.usinasantafe.pci.utils.StatusSend
import br.com.usinasantafe.pci.utils.WEB_ALL_COLAB
import br.com.usinasantafe.pci.utils.WEB_SAVE_TOKEN
import br.com.usinasantafe.pci.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class ConfigFlowTest {
    companion object {

        private lateinit var server: MockWebServer

        @BeforeClass
        @JvmStatic
        fun setupClass() {

            val resultToken = """{"idBD":1,"idEquip":1}""".trimIndent()

            val resultColabRetrofit = """
                [{"idColab":1,"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO","idFactorySectionColab":1}]
            """.trimIndent()

            val dispatcherSuccess: Dispatcher = object : Dispatcher() {

                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                        "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabRetrofit)
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

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun flow_config() =
        runTest(
            timeout = 10.minutes
        ) {

            Log.d("TestDebug", "Position 1")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
            composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
            composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

            Log.d("TestDebug", "Position 2")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("APONTAMENTO")
                .performClick()

            Log.d("TestDebug", "Position 3")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("OK")
                .performClick()

            Log.d("TestDebug", "Position 4")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CONFIGURAÇÃO")
                .performClick()

            Log.d("TestDebug", "Position 5")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("SENHA").assertIsDisplayed()

            Log.d("TestDebug", "Position 6")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CANCELAR")
                .performClick()

            Log.d("TestDebug", "Position 7")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CONFIGURAÇÃO")
                .performClick()

            Log.d("TestDebug", "Position 8")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("SENHA").assertIsDisplayed()
            composeTestRule.onNodeWithText("OK")
                .performClick()

            Log.d("TestDebug", "Position 9")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CANCELAR")
                .performClick()

            Log.d("TestDebug", "Position 10")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CONFIGURAÇÃO")
                .performClick()

            Log.d("TestDebug", "Position 11")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("OK")
                .performClick()

            Log.d("TestDebug", "Position 12")

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            Log.d("TestDebug", "Position 13")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CONFIGURAÇÃO REALIZADA COM SUCESSO!")

            Log.d("TestDebug", "Position 14")

            composeTestRule.waitUntilTimeout(3_000)

            val resultGet = configSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val entityConfig = resultGet.getOrNull()!!
            assertEquals(
                entityConfig,
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    idBD = 1,
                    version = "1.0",
                    statusSend = StatusSend.STARTED,
                    flagUpdate = FlagUpdate.UPDATED
                )
            )

            val colabList = colabDao.all()
            assertEquals(
                colabList.size,
                1
            )
            val entityColab = colabList[0]
            assertEquals(
                entityColab.idColab,
                1
            )
            assertEquals(
                entityColab.regColab,
                19759
            )
            assertEquals(
                entityColab.nameColab,
                "ANDERSON DA SILVA DELGADO"
            )
            assertEquals(
                entityColab.idFactorySectionColab,
                1
            )

            Log.d("TestDebug", "Position 15")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("OK")
                .performClick()

            Log.d("TestDebug", "Position 16")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("APONTAMENTO")
                .performClick()

            Log.d("TestDebug", "Position 17")

            composeTestRule.waitUntilTimeout(3_000)

            Log.d("TestDebug", "Position Finish")

        }
}