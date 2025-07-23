package br.com.usinasantafe.pci.presenter.view.configuration.config

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pci.HiltTestActivity
import br.com.usinasantafe.pci.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.pci.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
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
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class ConfigScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var colabDao: ColabDao

    private val resultToken = """{"idBD":1,"idEquip":1}""".trimIndent()

    private val resultColabRetrofit = """
        [{"idColab":1,"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO","idFactorySectionColab":1}]
    """.trimIndent()

    private val dispatcherFailureColab: Dispatcher = object : Dispatcher() {

        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherSuccess: Dispatcher = object : Dispatcher() {

        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabRetrofit)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    @Test
    fun check_open_screen() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_msg_if_field_empty() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA TODOS OS CAMPOS PARA SALVAR AS CONFIGURAÇÕES E ATUALIZAR TODAS AS BASES DE DADOS.")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_not_returned() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE RECUPERACAO DE TOKEN! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.token -> ISendDataConfig -> IConfigRepository.send -> IConfigRetrofitDatasource.recoverToken -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_token_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("""{"idBD":1a}""")
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE RECUPERACAO DE TOKEN! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.token -> ISendDataConfig -> IConfigRepository.send -> IConfigRetrofitDatasource.recoverToken -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 9 path \$.idBD")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_token_correct_and_not_return_data_colab() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherFailureColab
            server.start()

            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.recoverAll -> IColabRetrofitDatasource.recoverAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout(3_000)

            val resultGet = configSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val entity = resultGet.getOrNull()!!
            assertEquals(
                entity,
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    idBD = 1,
                    version = "1.0",
                    statusSend = StatusSend.STARTED,
                    flagUpdate = FlagUpdate.OUTDATED
                )
            )
            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_update_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherSuccess
            server.start()

            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CONFIGURAÇÃO REALIZADA COM SUCESSO!")

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

            composeTestRule.waitUntilTimeout(3_000)

        }

    private fun setContent() {
        composeTestRule.setContent {
            ConfigScreen(
                onNavInitialMenu = {}
            )
        }
    }

}