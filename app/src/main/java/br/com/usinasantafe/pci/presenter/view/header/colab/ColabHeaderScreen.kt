package br.com.usinasantafe.pci.presenter.view.header.colab

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pci.R
import br.com.usinasantafe.pci.presenter.theme.AlertDialogProgressIndeterminateDesign
import br.com.usinasantafe.pci.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pci.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.pci.presenter.theme.TitleDesign
import br.com.usinasantafe.pci.presenter.theme.PCITheme
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import br.com.usinasantafe.pci.utils.TypeButton

@Composable
fun ColabHeaderScreen(
    viewModel: ColabHeaderViewModel = hiltViewModel(),
    onNavInitialMenu: () -> Unit,
    onNavOS: () -> Unit
) {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ColabHeaderContent(
                regColab = uiState.regColab,
                setTextField = viewModel::setTextField,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                flagProgress = uiState.flagProgress,
                onNavInitialMenu = onNavInitialMenu,
                onNavOS = onNavOS,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ColabHeaderContent(
    regColab: String,
    setTextField: (String, TypeButton) -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    flagProgress: Boolean,
    onNavInitialMenu: () -> Unit,
    onNavOS: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_colab
            )
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Previous
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 28.sp,
            ),
            readOnly = true,
            value = regColab,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = setTextField,
            flagUpdate = false
        )
        BackHandler {
            onNavInitialMenu()
        }

        if(flagDialog) {
            val text = when (errors) {
                Errors.FIELD_EMPTY -> stringResource(
                    id = R.string.text_field_empty,
                    stringResource(id = R.string.text_title_colab)
                )
                Errors.UPDATE,
                Errors.TOKEN,
                Errors.EXCEPTION -> stringResource(
                    id = R.string.text_failure,
                    failure
                )
                Errors.INVALID -> stringResource(
                    id = R.string.text_input_data_non_existent,
                    stringResource(id = R.string.text_title_colab)
                )
                else -> ""
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagProgress) {
            AlertDialogProgressIndeterminateDesign(
                msgProgress = stringResource(
                    id = R.string.text_check_data,
                    stringResource(id = R.string.text_title_colab)
                )
            )
        }
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavOS()
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ColabHeaderPagePreview() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ColabHeaderContent(
                regColab = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.INVALID,
                flagProgress = false,
                onNavInitialMenu = {},
                onNavOS = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColabHeaderPagePreviewInvalid() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ColabHeaderContent(
                regColab = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "",
                errors = Errors.INVALID,
                flagProgress = false,
                onNavInitialMenu = {},
                onNavOS = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColabHeaderPagePreviewEmpty() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ColabHeaderContent(
                regColab = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                flagProgress = false,
                onNavInitialMenu = {},
                onNavOS = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColabHeaderPagePreviewFailure() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ColabHeaderContent(
                regColab = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                errors = Errors.EXCEPTION,
                flagProgress = false,
                onNavInitialMenu = {},
                onNavOS = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColabHeaderPagePreviewProgress() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ColabHeaderContent(
                regColab = "",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "Failure",
                errors = Errors.EXCEPTION,
                flagProgress = true,
                onNavInitialMenu = {},
                onNavOS = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
