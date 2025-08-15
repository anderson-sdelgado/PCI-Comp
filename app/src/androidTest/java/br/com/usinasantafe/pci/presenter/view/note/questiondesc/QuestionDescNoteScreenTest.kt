package br.com.usinasantafe.pci.presenter.view.note.questiondesc

import android.annotation.SuppressLint
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pci.HiltTestActivity
import br.com.usinasantafe.pci.domain.usecases.flow.GetResp
import br.com.usinasantafe.pci.external.room.dao.stable.ComponentDao
import br.com.usinasantafe.pci.external.room.dao.stable.ItemDao
import br.com.usinasantafe.pci.external.room.dao.stable.ServiceDao
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.ItemRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.ServiceRoomModel
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.presenter.Args.ID_ITEM_ARG
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class QuestionDescNoteScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var getResp: GetResp

    @Inject
    lateinit var itemDao: ItemDao

    @Inject
    lateinit var serviceDao: ServiceDao

    @Inject
    lateinit var componentDao: ComponentDao

    @Inject
    lateinit var respDao : RespDao

    @Test
    fun check_open_screen_and_msg_if_item_table_is_empty() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. QuestionDescNoteViewModel.recover -> IGetResp -> IItemRepository.getById -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_service_table_is_empty() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. QuestionDescNoteViewModel.recover -> IGetResp -> IServiceRepository.getById -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_resp_table_is_empty_and_id_component_is_0() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. QuestionDescNoteViewModel.recover -> IGetResp -> ICheckListRepository.getRespByIdItem -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_success_execute_successfully_and_id_component_is_0() =
        runTest {

            hiltRule.inject()

            initialRegister(4)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)


        }

    @Test
    fun check_open_screen_and_msg_if_component_table_is_empty_and_id_component_is_not_0() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            setContent(2)

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. QuestionDescNoteViewModel.recover -> IGetResp -> IComponentRepository.getById -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_resp_table_is_empty_and_id_component_is_not_0() =
        runTest {

            hiltRule.inject()

            initialRegister(3)

            setContent(2)

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. QuestionDescNoteViewModel.recover -> IGetResp -> ICheckListRepository.getRespByIdItem -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_success_execute_successfully_and_id_component_is_not_0() =
        runTest {

            hiltRule.inject()

            initialRegister(4)

            setContent(2)

            composeTestRule.waitUntilTimeout(10_000)

        }

    @SuppressLint("ViewModelConstructorInComposable")
    private fun setContent(idItem: Int = 1){
        composeTestRule.setContent {
            QuestionDescNoteScreen(
                viewModel = QuestionDescNoteViewModel(
                    saveStateHandle = SavedStateHandle(
                        mapOf(
                            ID_ITEM_ARG to idItem
                        )
                    ),
                    getResp = getResp
                ),
                onNavQuestionList = {},
                onNavQuestionResp = {}
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

        if (level == 2) return

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

        if (level == 3) return

        respDao.insert(
            RespRoomModel(
                idHeader = 1,
                idPlant = 1,
                idItem = 1,
                option = OptionResp.ACCORDING
            )
        )
        respDao.insert(
            RespRoomModel(
                idHeader = 1,
                idPlant = 1,
                idItem = 2,
                option = OptionResp.NON_CONFORMING,
                obs = "OBS TEST"
            )
        )

        if (level == 4) return

    }

}