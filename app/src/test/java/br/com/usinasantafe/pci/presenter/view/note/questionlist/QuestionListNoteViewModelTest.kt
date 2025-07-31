package br.com.usinasantafe.pci.presenter.view.note.questionlist

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pci.MainCoroutineRule
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.usecases.note.CheckItemNote
import br.com.usinasantafe.pci.domain.usecases.note.ListItemNote
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableComponent
import br.com.usinasantafe.pci.domain.usecases.update.UpdateTableService
import br.com.usinasantafe.pci.presenter.Args.ID_PLANT_ARG
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class QuestionListNoteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkItemNote = mock<CheckItemNote>()
    private val updateTableComponent = mock<UpdateTableComponent>()
    private val updateTableService = mock<UpdateTableService>()
    private val listItemNote = mock<ListItemNote>()
    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
               ID_PLANT_ARG to 0
            )
        )
    ) = QuestionListNoteViewModel(
        savedStateHandle,
        checkItemNote = checkItemNote,
        updateTableComponent = updateTableComponent,
        updateTableService = updateTableService,
        listItemNote = listItemNote
    )

    @Test
    fun `checkAndUpdateData - Check return failure if have error in CheckItemNote`() =
        runTest {
            whenever(
                checkItemNote()
            ).thenReturn(
                resultFailure(
                    context = "CheckItemNote",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.checkAndUpdateData()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "QuestionListNoteViewModel.checkAndUpdateData -> CheckItemNote -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `checkAndUpdateData - Check return correct if have error in CheckItemNote`() =
        runTest {
            whenever(
                checkItemNote()
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = createViewModel()
            viewModel.checkAndUpdateData()
            assertEquals(
                viewModel.uiState.value.flagProgress,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                false
            )
            assertEquals(
                viewModel.uiState.value.levelUpdate,
                LevelUpdate.FINISH_UPDATE_COMPLETED
            )
            assertEquals(
                viewModel.uiState.value.currentProgress,
                1f
            )
        }



}