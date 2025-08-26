package br.com.usinasantafe.pci.presenter.view.configuration.initial

import br.com.usinasantafe.pci.MainCoroutineRule
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.usecases.config.GetStatusSend
import br.com.usinasantafe.pci.domain.usecases.flow.CheckAccessInitial
import br.com.usinasantafe.pci.utils.StatusSend
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class InitialMenuViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkAccessInitial = mock<CheckAccessInitial>()
    private val getStatusSend = mock<GetStatusSend>()
    private val viewModel = InitialMenuViewModel(
        checkAccessInitial = checkAccessInitial,
        getStatusSend = getStatusSend
    )

//    @Test
//    fun `recoverStatusSend - Check return failure if have error in GetStatusSend`() =
//        runTest {
//            whenever(
//                getStatusSend()
//            ).thenReturn(
//                resultFailure(
//                    "GetStatusSend",
//                    "-",
//                    Exception()
//                )
//            )
//            viewModel.recoverStatusSend()
//            val uiState = viewModel.uiState.value
//            assertEquals(
//                uiState.failure,
//                "InitialMenuViewModel.recoverStatusSend -> GetStatusSend -> java.lang.Exception",
//            )
//        }
//
//    @Test
//    fun `recoverStatusSend - Check return correct if function execute successfully`() =
//        runTest {
//            whenever(
//                getStatusSend()
//            ).thenReturn(
//                Result.success(
//                    StatusSend.SENT
//                )
//            )
//            viewModel.recoverStatusSend()
//            val uiState = viewModel.uiState.value
//            assertEquals(
//                uiState.failure,
//                ""
//            )
//            assertEquals(
//                uiState.statusSend,
//                StatusSend.SENT
//            )
//        }

    @Test
    fun `onCheckAccess - Check return failure if have error in CheckAccessInitial`() =
        runTest {
            whenever(
                checkAccessInitial()
            ).thenReturn(
                resultFailure(
                    context = "CheckAccessInitial",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.onCheckAccess()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "InitialMenuViewModel.onCheckAccess -> CheckAccessInitial -> java.lang.Exception"
            )
        }

    @Test
    fun `onCheckAccess - Check return true if CheckAccessInitial execute successfully`() =
        runTest {
            whenever(
                checkAccessInitial()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.onCheckAccess()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                false
            )
        }
}