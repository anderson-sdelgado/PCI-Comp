package br.com.usinasantafe.pci.presenter.view.note.plantlist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import br.com.usinasantafe.pci.HiltTestActivity
import br.com.usinasantafe.pci.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.pci.external.room.dao.stable.PlantDao
import br.com.usinasantafe.pci.external.sharedpreferences.datasource.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.room.stable.PlantRoomModel
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.infra.models.sharedpreferences.HeaderSharedPreferencesModel
import br.com.usinasantafe.pci.utils.FlagUpdate
import br.com.usinasantafe.pci.utils.waitUntilTimeout
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
class PlantListNoteScreenTest {

    private val resultItemListRepeated = """
        [
            {"idItem":1,"seqItem":1,"idOSItem":1,"idPlantItem":1,"idComponentItem":1,"idServiceItem":1},
            {"idItem":1,"seqItem":1,"idOSItem":1,"idPlantItem":1,"idComponentItem":1,"idServiceItem":1}
        ]
    """.trimIndent()

    private val resultItemList = """
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
            {"idItem":10,"seqItem":10,"idOSItem":1,"idPlantItem":2,"idComponentItem":6,"idServiceItem":5}
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
    lateinit var plantDao: PlantDao

    @Test
    fun check_open_screen_and_not_have_config_shared_preferences() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. PlantListNoteViewModel.updateItem -> IUpdateTableItemByIdOS -> IGetToken -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_not_have_header_shared_preferences() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. PlantListNoteViewModel.updateItem -> IUpdateTableItemByIdOS -> ICheckListRepository.getIdOSHeaderOpen -> IHeaderSharedPreferencesDatasource.getIdOS -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_web_service_without_return() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. PlantListNoteViewModel.updateItem -> IUpdateTableItemByIdOS -> IItemRepository.listByIdOS -> IItemRetrofitDatasource.listByIdOS -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_msg_failure_if_web_service_return_incorrect() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. PlantListNoteViewModel.updateItem -> IUpdateTableItemByIdOS -> IItemRepository.listByIdOS -> IItemRetrofitDatasource.listByIdOS -> java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_msg_failure_if_web_service_return_data_repeated() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultItemListRepeated)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. PlantListNoteViewModel.updateItem -> IUpdateTableItemByIdOS -> IItemRepository.addAll -> IItemRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_item.idItem (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_list_empty_if_plant_room_is_empty() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultItemList)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_return_list_if_plant_room_is_not_empty() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultItemList)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(3)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    private fun setContent() {
        composeTestRule.setContent {
            PlantListNoteScreen(
                onNavOSList = {},
                onNavQuestionList = {}
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

        plantDao.insertAll(
            listOf(
                PlantRoomModel(
                    idPlant = 1,
                    codPlant = "01",
                    descPlant = "PLANT 1",
                    idFactorySectionPlant = 1
                ),
                PlantRoomModel(
                    idPlant = 2,
                    codPlant = "02",
                    descPlant = "PLANT 2",
                    idFactorySectionPlant = 1
                )
            )
        )

        if (level == 3) return

    }
}