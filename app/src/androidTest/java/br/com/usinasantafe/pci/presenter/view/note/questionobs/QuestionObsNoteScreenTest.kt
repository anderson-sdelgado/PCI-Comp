package br.com.usinasantafe.pci.presenter.view.note.questionobs

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pci.HiltTestActivity
import br.com.usinasantafe.pci.domain.usecases.note.SetRespItem
import br.com.usinasantafe.pci.presenter.Args.ID_ITEM_ARG
import br.com.usinasantafe.pci.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class QuestionObsNoteScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var setRespItem: SetRespItem

    @Test
    fun check_open_screen_and_msg_if_field_is_empty() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("OK")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA O CAMPO \"OBSERVAÇÃO\" PARA DAR CONTINUIDADE AO APONTAMENTO.")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @SuppressLint("ViewModelConstructorInComposable")
    private fun setContent(){
        composeTestRule.setContent {
            QuestionObsNoteScreen(
                viewModel = QuestionObsNoteViewModel(
                    saveStateHandle = SavedStateHandle(
                        mapOf(
                            ID_ITEM_ARG to 1
                        )
                    ),
                    setRespItem = setRespItem
                ),
                onNavQuestionList = {},
                onNavQuestionResp = {}
            )
        }
    }

}