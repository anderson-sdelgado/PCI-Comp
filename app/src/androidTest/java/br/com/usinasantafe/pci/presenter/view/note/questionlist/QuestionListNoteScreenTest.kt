package br.com.usinasantafe.pci.presenter.view.note.questionlist

import android.annotation.SuppressLint
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pci.HiltTestActivity
import br.com.usinasantafe.pci.domain.usecases.note.CheckItemNote
import br.com.usinasantafe.pci.domain.usecases.note.ListItemNote
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableComponent
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableService
import br.com.usinasantafe.pci.presenter.Args.ID_PLANT_ARG
import br.com.usinasantafe.pci.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

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

    @Test
    fun check_open_screen() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

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
                onNavPlantList = {}
            )
        }
    }
}