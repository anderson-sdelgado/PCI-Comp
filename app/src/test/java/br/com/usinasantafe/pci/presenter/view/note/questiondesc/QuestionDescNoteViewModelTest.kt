package br.com.usinasantafe.pci.presenter.view.note.questiondesc

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pci.MainCoroutineRule
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.usecases.flow.GetResp
import br.com.usinasantafe.pci.presenter.Args.ID_ITEM_ARG
import br.com.usinasantafe.pci.presenter.Args.ID_PLANT_ARG
import br.com.usinasantafe.pci.presenter.model.RespScreenModel
import br.com.usinasantafe.pci.utils.OptionResp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class QuestionDescNoteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getResp = mock<GetResp>()
    private fun createViewModel(
        idPlant: Int = 1,
        idItem: Int = 1
    ) = QuestionDescNoteViewModel(SavedStateHandle(
            mapOf(
                ID_PLANT_ARG to idPlant,
                ID_ITEM_ARG to idItem
            )
        ),
        getResp = getResp
    )

    @Test
    fun `recover - Check return failure if have error in GetResp`() =
        runTest {
            whenever(
                getResp(2)
            ).thenReturn(
                resultFailure(
                    context = "GetResp",
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
                "QuestionDescNoteViewModel.recover -> GetResp -> java.lang.Exception"
            )
        }

    @Test
    fun `recover - Check return true if GetResp execute successfully`() =
        runTest {
            whenever(
                getResp(2)
            ).thenReturn(
                Result.success(
                    RespScreenModel(
                        pos = 2,
                        desc = "Test",
                        option = OptionResp.ACCORDING,
                        obs = null
                    )
                )
            )
            val viewModel = createViewModel(
                idItem = 2
            )
            viewModel.recover()
            assertEquals(
                viewModel.uiState.value.resp,
                RespScreenModel(
                    pos = 2,
                    desc = "Test",
                    option = OptionResp.ACCORDING,
                    obs = null
                )
            )
        }

}