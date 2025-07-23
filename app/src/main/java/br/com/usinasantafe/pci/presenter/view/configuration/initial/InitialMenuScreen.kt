package br.com.usinasantafe.pci.presenter.view.configuration.initial

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pci.BuildConfig
import br.com.usinasantafe.pci.R
import br.com.usinasantafe.pci.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pci.presenter.theme.ItemListDesign
import br.com.usinasantafe.pci.presenter.theme.TitleDesign
import br.com.usinasantafe.pci.presenter.theme.PCITheme

@Composable
fun InitialMenuScreen(
    viewModel: InitialMenuViewModel = hiltViewModel(),
    onNavPassword: () -> Unit,
    onNavColab: () -> Unit
) {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            InitialMenuContent(
                onCheckAccess = viewModel::onCheckAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                flagFailure = uiState.flagFailure,
                onNavPassword = onNavPassword,
                onNavColab = onNavColab,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun InitialMenuContent(
    onCheckAccess: () -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    flagFailure: Boolean,
    onNavPassword: () -> Unit,
    onNavColab: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as? Activity)
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_initial_menu,
                BuildConfig.VERSION_NAME
            )
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                ItemListDesign(
                    text = stringResource(
                        id = R.string.text_item_note
                    ),
                    setActionItem = onCheckAccess,
                    font = 26
                )
            }
            item {
                ItemListDesign(
                    text = stringResource(
                        id = R.string.text_item_config
                    ),
                    setActionItem = onNavPassword,
                    font = 26
                )
            }
            item {
                ItemListDesign(
                    text = stringResource(
                        id = R.string.text_item_out
                    ),
                    setActionItem = {
                        activity?.finish()
                    },
                    font = 26
                )
            }
        }
        BackHandler {}

        if (flagDialog) {
            val text =
                if (!flagFailure) {
                    stringResource(id = R.string.text_blocked_access_app)
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
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavColab()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialMenuPagePreview() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            InitialMenuContent(
                onCheckAccess = {},
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                flagFailure = false,
                onNavPassword = {},
                onNavColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}