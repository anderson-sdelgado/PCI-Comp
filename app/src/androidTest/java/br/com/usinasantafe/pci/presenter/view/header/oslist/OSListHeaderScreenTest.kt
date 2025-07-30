package br.com.usinasantafe.pci.presenter.view.header.oslist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.pci.HiltTestActivity
import br.com.usinasantafe.pci.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.pci.external.room.dao.stable.OSDao
import br.com.usinasantafe.pci.external.room.dao.stable.PlantDao
import br.com.usinasantafe.pci.external.sharedpreferences.datasource.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.infra.models.sharedpreferences.HeaderSharedPreferencesModel
import br.com.usinasantafe.pci.utils.FlagUpdate
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
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class OSListHeaderScreenTest {


    private val resultOSListIncorrect = """
        [
            {"idOS":1,"nroOS":1,"idPlantOS":1,"qtdDayOS":1,"descPeriodOS":"DIARIO","idFactorySectionOS":1},
            {"idOS":1,"nroOS":1,"idPlantOS":1,"qtdDayOS":1,"descPeriodOS":"DIARIO","idFactorySectionOS":1}
        ]
    """.trimIndent()

    private val resultOSList = """
        [
            {"idOS":1,"nroOS":1,"idPlantOS":1,"qtdDayOS":1,"descPeriodOS":"DIARIO","idFactorySectionOS":1},
            {"idOS":2,"nroOS":2,"idPlantOS":2,"qtdDayOS":2,"descPeriodOS":"SEMANAL","idFactorySectionOS":1}
        ]
    """.trimIndent()

    private val resultPlantListIncorrect = """
        [
            {"idPlant":1,"codPlant":"01","descPlant":"PLANTA 01","idFactorySectionPlant":1},
            {"idPlant":1,"codPlant":"01","descPlant":"PLANTA 01","idFactorySectionPlant":1}
        ]
    """.trimIndent()

    private val resultPlantList = """
        [
            {"idPlant":1,"codPlant":"01","descPlant":"PLANTA 01","idFactorySectionPlant":1},
            {"idPlant":2,"codPlant":"02","descPlant":"PLANTA 02","idFactorySectionPlant":1}
        ]
    """.trimIndent()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderSharedPreferencesDatasource

    @Inject
    lateinit var osDao: OSDao

    @Inject
    lateinit var plantDao: PlantDao

    @Test
    fun check_open_screen_and_not_have_config_shared_preferences() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OSListHeaderViewModel.updateAllDatabase -> IUpdateTableOSByIdFactorySection -> IGetToken -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_have_header_shared_preferences() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OSListHeaderViewModel.updateAllDatabase -> IUpdateTableOSByIdFactorySection -> ICheckListRepository.getIdFactorySectionHeaderOpen -> IHeaderSharedPreferencesDatasource.getIdFactorySection -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_web_service_failure() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OSListHeaderViewModel.updateAllDatabase -> IUpdateTableOSByIdFactorySection -> IGetToken -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_web_service_return_os_repeated() =
        runTest {
            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_LIST_OS_BY_ID_FACTORY_SECTION" -> MockResponse().setBody(resultOSListIncorrect)
                        "/$WEB_LIST_PLANT_BY_ID_FACTORY_SECTION" -> MockResponse().setBody("")
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherSuccessFlow
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OSListHeaderViewModel.updateAllDatabase -> IUpdateTableOSByIdFactorySection -> IOSRepository.addAll -> IOSRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_os.idOS (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_web_service_return_plant_with_failure() =
        runTest {
            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_LIST_OS_BY_ID_FACTORY_SECTION" -> MockResponse().setBody(resultOSList)
                        "/$WEB_LIST_PLANT_BY_ID_FACTORY_SECTION" -> MockResponse().setBody("")
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherSuccessFlow
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OSListHeaderViewModel.updateAllDatabase -> IUpdateTablePlantByIdFactorySection -> IPlantRepository.listByIdFactorySection -> IPlantRetrofitDatasource.listByIdFactorySection -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_web_service_return_plant_repeated() =
        runTest {
            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_LIST_OS_BY_ID_FACTORY_SECTION" -> MockResponse().setBody(resultOSList)
                        "/$WEB_LIST_PLANT_BY_ID_FACTORY_SECTION" -> MockResponse().setBody(resultPlantListIncorrect)
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherSuccessFlow
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OSListHeaderViewModel.updateAllDatabase -> IUpdateTablePlantByIdFactorySection -> IPlantRepository.addAll -> IPlantRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_plant.idPlant (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout(20_000)

        }

    @Test
    fun check_open_screen_and_web_service_return_correct() =
        runTest(
            timeout = 10.minutes
        ) {
            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_LIST_OS_BY_ID_FACTORY_SECTION" -> MockResponse().setBody(resultOSList)
                        "/$WEB_LIST_PLANT_BY_ID_FACTORY_SECTION" -> MockResponse().setBody(resultPlantList)
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherSuccessFlow
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(25_000)

            val osList = osDao.all()
            assertEquals(
                osList.size,
                2
            )
            val entityOS1 = osList[0]
            assertEquals(
                entityOS1.idOS,
                1
            )
            assertEquals(
                entityOS1.nroOS,
                1
            )
            assertEquals(
                entityOS1.idPlantOS,
                1
            )
            assertEquals(
                entityOS1.qtdDayOS,
                1
            )
            assertEquals(
                entityOS1.descPeriodOS,
                "DIARIO"
            )
            assertEquals(
                entityOS1.idFactorySectionOS,
                1
            )
            val entityOS2 = osList[1]
            assertEquals(
                entityOS2.idOS,
                2
            )
            assertEquals(
                entityOS2.nroOS,
                2
            )
            assertEquals(
                entityOS2.idPlantOS,
                2
            )
            assertEquals(
                entityOS2.qtdDayOS,
                2
            )
            assertEquals(
                entityOS2.descPeriodOS,
                "SEMANAL"
            )
            val plantList = plantDao.all()
            assertEquals(
                plantList.size,
                2
            )
            val entityPlant1 = plantList[0]
            assertEquals(
                entityPlant1.idPlant,
                1
            )
            assertEquals(
                entityPlant1.codPlant,
                "01"
            )
            assertEquals(
                entityPlant1.descPlant,
                "PLANTA 01"
            )
            assertEquals(
                entityPlant1.idFactorySectionPlant,
                1
            )
            val entityPlant2 = plantList[1]
            assertEquals(
                entityPlant2.idPlant,
                2
            )
            assertEquals(
                entityPlant2.codPlant,
                "02"
            )
            assertEquals(
                entityPlant2.descPlant,
                "PLANTA 02"
            )
            assertEquals(
                entityPlant2.idFactorySectionPlant,
                1
            )
        }

    @Test
    fun check_open_screen_and_click() =
        runTest(
            timeout = 10.minutes
        ) {
            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_LIST_OS_BY_ID_FACTORY_SECTION" -> MockResponse().setBody(resultOSList)
                        "/$WEB_LIST_PLANT_BY_ID_FACTORY_SECTION" -> MockResponse().setBody(resultPlantList)
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherSuccessFlow
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("item_list_1")
                .performClick()

            composeTestRule.waitUntilTimeout(10_000)

        }

    private fun setContent() {
        composeTestRule.setContent {
            OSListHeaderScreen(
                onNavColab = {},
                onNavPlantList = {}
            )
        }
    }

    private suspend fun initialRegister(level: Int) {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                idBD = 1,
                version = "1.0",
                flagUpdate = FlagUpdate.UPDATED
            )
        )

        if (level == 1) return

        headerSharedPreferencesDatasource.save(
            HeaderSharedPreferencesModel(
                idColab = 1,
                idFactorySection = 1,
                idOS = 1
            )
        )

        if (level == 2) return

    }
}