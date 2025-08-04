package br.com.usinasantafe.pci.presenter.view.note.questionresp

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pci.MainCoroutineRule
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.usecases.note.GetItem
import br.com.usinasantafe.pci.domain.usecases.note.SetRespItem
import br.com.usinasantafe.pci.presenter.Args.ID_ITEM_ARG
import br.com.usinasantafe.pci.presenter.Args.ID_PLANT_ARG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class QuestionRespNoteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getItem = mock<GetItem>()
    private val setRespItem = mock<SetRespItem>()
    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                ID_PLANT_ARG to 1,
                ID_ITEM_ARG to 1
            )
        )
    ) = QuestionRespNoteViewModel(
        savedStateHandle,
        getItem = getItem,
        setRespItem = setRespItem
    )

    @Test
    fun `recover - Check return failure if have error in GetItem`() =
        runTest {
            whenever(
                getItem(1)
            ).thenReturn(
                resultFailure(
                    context = "GetItem",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.recover()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "QuestionRespNoteViewModel.recover -> GetItem -> java.lang.Exception"
            )
        }

}