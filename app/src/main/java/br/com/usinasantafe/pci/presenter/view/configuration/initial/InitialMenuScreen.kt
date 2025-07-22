package br.com.usinasantafe.pci.presenter.view.configuration.initial

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
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
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
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
                    setActionItem = {},
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
    }
}

@Preview(showBackground = true)
@Composable
fun InitialMenuPagePreview() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            InitialMenuContent(
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                onNavPassword = {},
                onNavColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}