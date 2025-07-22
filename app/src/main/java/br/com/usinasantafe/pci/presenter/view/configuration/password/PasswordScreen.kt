package br.com.usinasantafe.pci.presenter.view.configuration.password

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pci.R
import br.com.usinasantafe.pci.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pci.presenter.theme.TitleDesign
import br.com.usinasantafe.pci.presenter.theme.PCITheme
import br.com.usinasantafe.pci.presenter.theme.TextButtonDesign

const val TAG_PASSWORD_TEXT_FIELD_SCREEN = "tag_password_text_field_screen"

@Composable
fun PasswordScreen(
    viewModel: PasswordViewModel = hiltViewModel(),
    onNavInitialMenu: () -> Unit,
    onNavConfig: () -> Unit
) {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            PasswordContent(
                password = uiState.password,
                onPasswordChanged = viewModel::onPasswordChanged,
                onCheckAccess = viewModel::onCheckAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.flagFailure,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                flagAccess = uiState.flagAccess,
                onNavInitialMenu = onNavInitialMenu,
                onNavConfig = onNavConfig,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun PasswordContent(
    password: String,
    onPasswordChanged: (String) -> Unit,
    onCheckAccess: () -> Unit,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    flagDialog: Boolean,
    failure: String,
    flagAccess: Boolean,
    onNavInitialMenu: () -> Unit,
    onNavConfig: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_password
            )
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            textStyle = TextStyle(
                fontSize = 24.sp
            ),
            visualTransformation = PasswordVisualTransformation(),
            value = password,
            onValueChange = onPasswordChanged,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_PASSWORD_TEXT_FIELD_SCREEN)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        )  {
            Button(
                onClick = onCheckAccess,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_ok)
                )
            }
            Button(
                onClick = onNavInitialMenu,
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_cancel)
                )
            }
        }
        BackHandler {}

        if(flagDialog) {
            val text =
                if(!flagFailure) {
                    stringResource(id = R.string.text_password_invalid)
                } else {
                    stringResource(
                        id = R.string.text_failure,
                        failure
                    )
                }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        LaunchedEffect(flagAccess) {
            if(flagAccess) {
                onNavConfig()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordPagePreview() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PasswordContent(
                password = "",
                onPasswordChanged = {},
                onCheckAccess = {},
                setCloseDialog = {},
                flagDialog = false,
                flagFailure = false,
                failure = "",
                flagAccess = false,
                onNavInitialMenu = {},
                onNavConfig = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordPagePreviewFailure() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PasswordContent(
                password = "",
                onPasswordChanged = {},
                onCheckAccess = {},
                setCloseDialog = {},
                flagDialog = true,
                flagFailure = true,
                failure = "Failure",
                flagAccess = false,
                onNavInitialMenu = {},
                onNavConfig = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordPagePreviewPasswordInvalid() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PasswordContent(
                password = "",
                onPasswordChanged = {},
                onCheckAccess = {},
                setCloseDialog = {},
                flagDialog = true,
                flagFailure = false,
                failure = "",
                flagAccess = false,
                onNavInitialMenu = {},
                onNavConfig = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}