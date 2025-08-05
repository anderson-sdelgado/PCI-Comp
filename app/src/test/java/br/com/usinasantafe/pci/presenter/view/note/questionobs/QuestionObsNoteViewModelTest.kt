package br.com.usinasantafe.pci.presenter.view.note.questionobs

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pci.MainCoroutineRule
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.usecases.note.SetRespItem
import br.com.usinasantafe.pci.presenter.Args.ID_ITEM_ARG
import br.com.usinasantafe.pci.presenter.Args.ID_PLANT_ARG
import br.com.usinasantafe.pci.utils.OptionResp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class QuestionObsNoteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setRespItem = mock<SetRespItem>()
    private fun createViewModel(
        idPlant: Int = 1,
        idItem: Int = 1
    ) = QuestionObsNoteViewModel(
        SavedStateHandle(
            mapOf(
                ID_PLANT_ARG to idPlant,
                ID_ITEM_ARG to idItem
            )
        ),
        setRespItem = setRespItem
    )

    @Test
    fun `setResp - Check return failure if field is empty`() =
        runTest {
            val viewModel = createViewModel()
            viewModel.setResp()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "QuestionObsNoteViewModel.setResp -> Field Empty!"
            )
        }

    @Test
    fun `setResp - Check return failure if have error in SetRespItem`() =
        runTest {
            whenever(
                setRespItem(
                    id = 2,
                    option = OptionResp.NON_CONFORMING,
                    obs = "Test"
                )
            ).thenReturn(
                resultFailure(
                    context = "SetRespItem",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel(
                idItem = 2
            )
            viewModel.onObsChanged("Test")
            viewModel.setResp()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "QuestionObsNoteViewModel.setResp -> SetRespItem -> java.lang.Exception"
            )
        }

    @Test
    fun `setResp - Check return true if SetRespItem execute successfully`() =
        runTest {
            whenever(
                setRespItem(
                    id = 2,
                    option = OptionResp.NON_CONFORMING,
                    obs = "Test"
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = createViewModel(
                idItem = 2
            )
            viewModel.onObsChanged("Test")
            viewModel.setResp()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}