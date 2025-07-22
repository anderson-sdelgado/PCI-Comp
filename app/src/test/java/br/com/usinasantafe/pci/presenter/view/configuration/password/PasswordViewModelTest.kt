package br.com.usinasantafe.pci.presenter.view.configuration.password

import br.com.usinasantafe.pci.MainCoroutineRule
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.usecases.config.CheckPassword
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PasswordViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkPassword = mock<CheckPassword>()
    private val viewModel = PasswordViewModel(
        checkPassword = checkPassword
    )

    @Test
    fun `checkAccess - Check return failure if have error in CheckPassword`() =
        runTest {
            whenever(
                checkPassword(
                    password = viewModel.uiState.value.password
                )
            ).thenReturn(
                resultFailure(
                    "CheckPassword",
                    "-",
                    Exception()
                )
            )
            viewModel.onCheckAccess()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                true
            )
            assertEquals(
                uiState.flagAccess,
                false
            )
            assertEquals(
                uiState.flagFailure,
                true
            )
            assertEquals(
                uiState.failure,
                "PasswordViewModel.checkAccess -> CheckPassword -> java.lang.Exception",
            )
        }

    @Test
    fun `checkAccess - Check return false if function execute successfully and password is incorrect`() =
        runTest {
            whenever(
                checkPassword(
                    password = viewModel.uiState.value.password
                )
            ).thenReturn(
                Result.success(false)
            )
            viewModel.onCheckAccess()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                true
            )
            assertEquals(
                uiState.flagAccess,
                false
            )
            assertEquals(
                uiState.flagFailure,
                false
            )
        }

    @Test
    fun `checkAccess - Check return true if function execute successfully and password is correct`() =
        runTest {
            whenever(
                checkPassword(
                    password = viewModel.uiState.value.password
                )
            ).thenReturn(
                Result.success(true)
            )
            viewModel.onCheckAccess()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                false
            )
            assertEquals(
                uiState.flagAccess,
                true
            )
            assertEquals(
                uiState.flagFailure,
                false
            )
        }
}