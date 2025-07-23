package br.com.usinasantafe.pci.presenter.view.header.colab

import br.com.usinasantafe.pci.MainCoroutineRule
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.usecases.header.CheckAndSetRegColabHeader
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.TypeButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ColabHeaderViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkAndSetRegColabHeader = mock<CheckAndSetRegColabHeader>()
    private val viewModel = ColabHeaderViewModel(
        checkAndSetRegColabHeader = checkAndSetRegColabHeader
    )

    @Test
    fun `setTextField - Check add char`() {
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.regColab,
            "1"
        )
    }

    @Test
    fun `setTextField - Check remover char`() {
        viewModel.setTextField(
            "19759",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.regColab,
            "191"
        )
    }

    @Test
    fun `checkAndSet - Check return failure if have error in CheckAndSetRegColab`() =
        runTest {
            whenever(
                checkAndSetRegColabHeader(regColab = "19759")
            ).thenReturn(
                resultFailure(
                    context = "ICheckAndSetRegColab",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField(
                "19759",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ColabHeaderViewModel.checkAndSet -> ICheckAndSetRegColab -> java.lang.Exception"
            )
        }

    @Test
    fun `checkAndSet - Check return false if regColab is inexistent`() =
        runTest {
            whenever(
                checkAndSetRegColabHeader(regColab = "19759")
            ).thenReturn(
                Result.success(false)
            )
            viewModel.setTextField(
                "19759",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
        }

    @Test
    fun `checkAndSet - Check return true if regColab is existent`() =
        runTest {
            whenever(
                checkAndSetRegColabHeader(regColab = "19759")
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setTextField(
                "19759",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
        }
}