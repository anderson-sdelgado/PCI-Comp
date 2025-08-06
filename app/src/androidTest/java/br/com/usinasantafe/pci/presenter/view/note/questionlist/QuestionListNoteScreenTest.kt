package br.com.usinasantafe.pci.presenter.view.note.questionlist

import android.annotation.SuppressLint
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pci.HiltTestActivity
import br.com.usinasantafe.pci.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.pci.domain.usecases.note.CheckItemNote
import br.com.usinasantafe.pci.domain.usecases.note.ListItemNote
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableComponent
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableService
import br.com.usinasantafe.pci.external.room.dao.stable.ComponentDao
import br.com.usinasantafe.pci.external.room.dao.stable.ItemDao
import br.com.usinasantafe.pci.external.room.dao.stable.ServiceDao
import br.com.usinasantafe.pci.external.sharedpreferences.datasource.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.ItemRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.ServiceRoomModel
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.infra.models.sharedpreferences.HeaderSharedPreferencesModel
import br.com.usinasantafe.pci.presenter.Args.ID_PLANT_ARG
import br.com.usinasantafe.pci.utils.FlagUpdate
import br.com.usinasantafe.pci.utils.WEB_ALL_COMPONENT
import br.com.usinasantafe.pci.utils.WEB_ALL_SERVICE
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
class QuestionListNoteScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var checkItemNote: CheckItemNote

    @Inject
    lateinit var updateTableComponent: UpdateTableComponent

    @Inject
    lateinit var updateTableService: UpdateTableService

    @Inject
    lateinit var listItemNote: ListItemNote

    @Inject
    lateinit var itemDao: ItemDao

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var componentDao: ComponentDao

    @Inject
    lateinit var serviceDao: ServiceDao

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderSharedPreferencesDatasource

    private val resultComponentRetrofit = """
        [{"idComponent":1,"codComponent":"1","descComponent":"TESTE 1"}]
    """.trimIndent()

    private val resultComponentRetrofitFailure = """
        [
            {"idComponent":1,"codComponent":"1","descComponent":"TESTE 1"},
            {"idComponent":1,"codComponent":"1","descComponent":"TESTE 1"}
        ]
    """.trimIndent()

    private val resultServiceRetrofit = """
        [{"idService":1,"codService":1,"descService":"TESTE 1"}]
    """.trimIndent()

    private val resultServiceRetrofitFailure = """
        [
            {"idService":1,"codService":1,"descService":"TESTE 1"},
            {"idService":1,"codService":1,"descService":"TESTE 1"}
        ]
    """.trimIndent()

    private val dispatcherComponentFailure: Dispatcher = object : Dispatcher() {

        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_ALL_COMPONENT" -> MockResponse().setBody("")
                "/$WEB_ALL_SERVICE" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherComponentDuplicate: Dispatcher = object : Dispatcher() {

        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_ALL_COMPONENT" -> MockResponse().setBody(resultComponentRetrofitFailure)
                "/$WEB_ALL_SERVICE" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherServiceFailure: Dispatcher = object : Dispatcher() {

        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_ALL_COMPONENT" -> MockResponse().setBody(resultComponentRetrofit)
                "/$WEB_ALL_SERVICE" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherServiceDuplicate: Dispatcher = object : Dispatcher() {

        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_ALL_COMPONENT" -> MockResponse().setBody(resultComponentRetrofit)
                "/$WEB_ALL_SERVICE" -> MockResponse().setBody(resultServiceRetrofitFailure)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherMissingDataTotal: Dispatcher = object : Dispatcher() {

        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_ALL_COMPONENT" -> MockResponse().setBody(resultComponentRetrofit)
                "/$WEB_ALL_SERVICE" -> MockResponse().setBody(resultServiceRetrofit)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    @Test
    fun check_open_screen_and_failure_if_not_have_data_config() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. QuestionListNoteViewModel.updateComponentAndService -> IUpdateTableComponent -> IGetToken -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_not_returned() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. QuestionListNoteViewModel.updateComponentAndService -> IUpdateTableComponent -> IComponentRepository.listAll -> IComponentRetrofitDatasource.listAll -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_not_return_data_component() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherComponentFailure
            server.start()

            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. QuestionListNoteViewModel.updateComponentAndService -> IUpdateTableComponent -> IComponentRepository.listAll -> IComponentRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_token_correct_and_return_data_component_duplicate() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherComponentDuplicate
            server.start()

            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. QuestionListNoteViewModel.updateComponentAndService -> IUpdateTableComponent -> IComponentRepository.addAll -> IComponentRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_component.idComponent (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_not_return_data_service() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherServiceFailure
            server.start()

            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. QuestionListNoteViewModel.updateComponentAndService -> IUpdateTableService -> IServiceRepository.listAll -> IServiceRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout(3_000)

            val componentList = componentDao.all()
            assertEquals(
                componentList.size,
                1
            )
            val entityComponent = componentList[0]
            assertEquals(
                entityComponent.idComponent,
                1
            )
            assertEquals(
                entityComponent.codComponent,
                "1"
            )
            assertEquals(
                entityComponent.descComponent,
                "TESTE 1"
            )

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_service_duplicate() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherServiceDuplicate
            server.start()

            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. QuestionListNoteViewModel.updateComponentAndService -> IUpdateTableService -> IServiceRepository.addAll -> IServiceRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_service.idService (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout(3_000)

            val componentList = componentDao.all()
            assertEquals(
                componentList.size,
                1
            )
            val entityComponent = componentList[0]
            assertEquals(
                entityComponent.idComponent,
                1
            )
            assertEquals(
                entityComponent.codComponent,
                "1"
            )
            assertEquals(
                entityComponent.descComponent,
                "TESTE 1"
            )

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_not_have_data_in_header_table() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherMissingDataTotal
            server.start()

            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. QuestionListNoteViewModel.recoverList -> IListItemNote -> ICheckListRepository.getIdOSHeaderOpen -> IHeaderSharedPreferencesDatasource.getIdOS -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

            val componentList = componentDao.all()
            assertEquals(
                componentList.size,
                1
            )
            val entityComponent = componentList[0]
            assertEquals(
                entityComponent.idComponent,
                1
            )
            assertEquals(
                entityComponent.codComponent,
                "1"
            )
            assertEquals(
                entityComponent.descComponent,
                "TESTE 1"
            )

            val serviceList = serviceDao.all()
            assertEquals(
                serviceList.size,
                1
            )
            val entityService = serviceList[0]
            assertEquals(
                entityService.idService,
                1
            )
            assertEquals(
                entityService.codService,
                1
            )
            assertEquals(
                entityService.descService,
                "TESTE 1"
            )

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_qtd_data_incorrect_data_service_and_component() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherMissingDataTotal
            server.start()

            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(3)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. QuestionListNoteViewModel.recoverList -> IListItemNote -> java.util.NoSuchElementException: Collection contains no element matching the predicate.")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_return_list_correct_with_update() =
        runTest(
            timeout = 1.minutes
        ) {

            hiltRule.inject()

            initialRegister(4)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @SuppressLint("ViewModelConstructorInComposable")
    private fun setContent() {
        composeTestRule.setContent {
            QuestionListNoteScreen(
                viewModel = QuestionListNoteViewModel(
                    saveStateHandle = SavedStateHandle(
                        mapOf(
                            ID_PLANT_ARG to 1
                        )
                    ),
                    checkItemNote = checkItemNote,
                    updateTableComponent = updateTableComponent,
                    updateTableService = updateTableService,
                    listItemNote = listItemNote
                ),
                onNavPlantList = {},
                onNavQuestionResp = {},
                onNavQuestionDesc = {}
            )
        }
    }

    private suspend fun initialRegister(level: Int) {

        itemDao.insertAll(
            listOf(
                ItemRoomModel(
                    idItem = 1,
                    seqItem = 1,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 0,
                    idServiceItem = 1
                ),
                ItemRoomModel(
                    idItem = 2,
                    seqItem = 2,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                ),
                ItemRoomModel(
                    idItem = 3,
                    seqItem = 3,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                ),
                ItemRoomModel(
                    idItem = 4,
                    seqItem = 4,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 2,
                    idServiceItem = 2
                ),
                ItemRoomModel(
                    idItem = 5,
                    seqItem = 5,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 3,
                    idServiceItem = 2
                ),
                ItemRoomModel(
                    idItem = 6,
                    seqItem = 6,
                    idOSItem = 1,
                    idPlantItem = 2,
                    idComponentItem = 4,
                    idServiceItem = 3
                ),
                ItemRoomModel(
                    idItem = 7,
                    seqItem = 7,
                    idOSItem = 1,
                    idPlantItem = 2,
                    idComponentItem = 5,
                    idServiceItem = 4
                ),
                ItemRoomModel(
                    idItem = 8,
                    seqItem = 8,
                    idOSItem = 1,
                    idPlantItem = 2,
                    idComponentItem = 5,
                    idServiceItem = 5
                ),
                ItemRoomModel(
                    idItem = 9,
                    seqItem = 9,
                    idOSItem = 1,
                    idPlantItem = 2,
                    idComponentItem = 6,
                    idServiceItem = 5
                ),
                ItemRoomModel(
                    idItem = 10,
                    seqItem = 10,
                    idOSItem = 1,
                    idPlantItem = 2,
                    idComponentItem = 6,
                    idServiceItem = 5
                )
            )
        )

        if (level == 1) return

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                idBD = 1,
                version = "1.0",
                flagUpdate = FlagUpdate.UPDATED
            )
        )

        if (level == 2) return

        headerSharedPreferencesDatasource.save(
            HeaderSharedPreferencesModel(
                idColab = 1,
                idFactorySection = 1,
                idOS = 1
            )
        )

        if (level == 3) return

        componentDao.insertAll(
            listOf(
                ComponentRoomModel(
                    idComponent = 1,
                    codComponent = "01",
                    descComponent = "COMPONENT 1"
                ),
                ComponentRoomModel(
                    idComponent = 2,
                    codComponent = "02",
                    descComponent = "COMPONENT 2"
                ),
                ComponentRoomModel(
                    idComponent = 3,
                    codComponent = "03",
                    descComponent = "COMPONENT 3"
                ),
                ComponentRoomModel(
                    idComponent = 4,
                    codComponent = "04",
                    descComponent = "COMPONENT 4"
                ),
                ComponentRoomModel(
                    idComponent = 5,
                    codComponent = "05",
                    descComponent = "COMPONENT 5"
                ),
                ComponentRoomModel(
                    idComponent = 6,
                    codComponent = "06",
                    descComponent = "COMPONENT 6"
                )
            )
        )

        serviceDao.insertAll(
            listOf(
                ServiceRoomModel(
                    idService = 1,
                    codService = 1,
                    descService = "SERVICE 1"
                ),
                ServiceRoomModel(
                    idService = 2,
                    codService = 2,
                    descService = "SERVICE 2"
                ),
                ServiceRoomModel(
                    idService = 3,
                    codService = 3,
                    descService = "SERVICE 3"
                ),
                ServiceRoomModel(
                    idService = 4,
                    codService = 4,
                    descService = "SERVICE 4"
                ),
                ServiceRoomModel(
                    idService = 5,
                    codService = 5,
                    descService = "SERVICE 5"
                )
            )
        )

        if (level == 4) return

    }

}