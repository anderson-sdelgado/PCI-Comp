package br.com.usinasantafe.pci.presenter.view.note.questionresp

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pci.MainCoroutineRule
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.usecases.note.GetDescItem
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
class QuestionRespNoteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getDescItem = mock<GetDescItem>()
    private val setRespItem = mock<SetRespItem>()
    private fun createViewModel(
        idPlant: Int = 1,
        idItem: Int = 1
    ) = QuestionRespNoteViewModel(
        SavedStateHandle(
            mapOf(
                ID_PLANT_ARG to idPlant,
                ID_ITEM_ARG to idItem
            )
        ),
        getDescItem = getDescItem,
        setRespItem = setRespItem
    )

    @Test
    fun `recover - Check return failure if have error in GetItem`() =
        runTest {
            whenever(
                getDescItem(2)
            ).thenReturn(
                resultFailure(
                    context = "GetItem",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel(
                idItem = 2
            )
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

    @Test
    fun `recover - Check return true if GetItem execute successfully`() =
        runTest {
            whenever(
                getDescItem(1)
            ).thenReturn(
                Result.success(
                    "Test"
                )
            )
            val viewModel = createViewModel()
            viewModel.recover()
            assertEquals(
                viewModel.uiState.value.desc,
                "Test"
            )
        }

    @Test
    fun `setResp - Check return failure if have error in SetRespItem`() =
        runTest {
            whenever(
                setRespItem(
                    id = 1,
                    option = OptionResp.ACCORDING,
                )
            ).thenReturn(
                resultFailure(
                    context = "SetRespItem",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.setResp(
                option = OptionResp.ACCORDING
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "QuestionRespNoteViewModel.setResp -> SetRespItem -> java.lang.Exception"
            )
        }

    @Test
    fun `setResp - Check return true if SetRespItem execute successfully and option is ACCORDING`() =
        runTest {
            whenever(
                setRespItem(
                    id = 1,
                    option = OptionResp.ACCORDING,
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = createViewModel()
            viewModel.setResp(OptionResp.ACCORDING)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `setResp - Check return FALSE if SetRespItem execute successfully and option is NON CONFORMING`() =
        runTest {
            val viewModel = createViewModel()
            viewModel.setResp(OptionResp.NON_CONFORMING)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }
}