package br.com.usinasantafe.pci.presenter.view.header.oslist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pci.R
import br.com.usinasantafe.pci.presenter.model.OSScreenModel
import br.com.usinasantafe.pci.presenter.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pci.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pci.presenter.theme.ItemListOSDesign
import br.com.usinasantafe.pci.presenter.theme.TitleDesign
import br.com.usinasantafe.pci.presenter.theme.PCITheme
import br.com.usinasantafe.pci.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate

@Composable
fun OSListHeaderScreen(
    viewModel: OSListHeaderViewModel = hiltViewModel(),
    onNavColab: () -> Unit,
    onNavPlantList: () -> Unit
) {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recoverAndUpdateData()
            }

            OSListHeaderContent(
                flagProgress = uiState.flagProgress,
                osList = uiState.osList,
                recoverList = viewModel::recoverList,
                setId = viewModel::setId,
                flagMsgUpdate = uiState.flagMsgUpdate,
                recoverAndUpdateData = viewModel::recoverAndUpdateData,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                currentProgress = uiState.currentProgress,
                levelUpdate = uiState.levelUpdate,
                tableUpdate = uiState.tableUpdate,
                errors = uiState.errors,
                onNavColab = onNavColab,
                onNavPlantList = onNavPlantList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun OSListHeaderContent(
    flagProgress: Boolean,
    osList: List<OSScreenModel>,
    recoverList: () -> Unit,
    setId: (Int) -> Unit,
    flagMsgUpdate: Boolean,
    recoverAndUpdateData: () -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    currentProgress: Float,
    levelUpdate: LevelUpdate?,
    tableUpdate: String,
    errors: Errors,
    onNavColab: () -> Unit,
    onNavPlantList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_os
            )
        )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        if (flagProgress) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LinearProgressIndicator(
                    progress = { currentProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                )
                Spacer(
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                val msgProgress = when(levelUpdate){
                    LevelUpdate.RECOVERY -> stringResource(id = R.string.text_msg_recovery, tableUpdate)
                    LevelUpdate.CLEAN -> stringResource(id = R.string.text_msg_clean, tableUpdate)
                    LevelUpdate.SAVE -> stringResource(id = R.string.text_msg_save, tableUpdate)
                    LevelUpdate.GET_TOKEN -> stringResource(id = R.string.text_msg_get_token)
                    LevelUpdate.SAVE_TOKEN -> stringResource(id = R.string.text_msg_save_token)
                    LevelUpdate.FINISH_UPDATE_INITIAL -> stringResource(id = R.string.text_msg_finish_update_initial)
                    LevelUpdate.FINISH_UPDATE_COMPLETED -> stringResource(id = R.string.text_msg_finish_update_completed)
                    null -> stringResource(
                        id = R.string.text_update_failure,
                        failure
                    )
                    else -> stringResource(
                        id = R.string.text_flow_inexistent,
                    )
                }
                Text(
                    text = msgProgress,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(osList) { os ->
                    ItemListOSDesign(
                        id = os.id,
                        period = os.period,
                        os = os.os,
                        codPlant = os.codPlant,
                        descPlant = os.descPlant,
                        setActionItem = { setId(os.id) },
                        font = 24,
                        padding = 6
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = onNavColab,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_update)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onNavColab,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_return)
            )
        }
        BackHandler {}

        if (flagDialog) {
            val text =
                when(errors) {
                    Errors.UPDATE -> stringResource(
                        id = R.string.text_update_failure,
                        failure
                    )
                    else -> stringResource(
                        id = R.string.text_failure,
                        failure
                    )
                }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if(flagMsgUpdate) {
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_question_update),
                setCloseDialog = setCloseDialog,
                setActionButtonOK = {
                    recoverAndUpdateData()
                }
            )

        }

    }

    LaunchedEffect(levelUpdate) {
        if(levelUpdate == LevelUpdate.FINISH_UPDATE_COMPLETED){
            recoverList()
        }
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavPlantList()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun OSHeaderPagePreviewUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSListHeaderContent(
                flagProgress = true,
                osList = listOf(),
                recoverList = {},
                setId = {},
                recoverAndUpdateData = {},
                flagMsgUpdate = false,
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0.0f,
                errors = Errors.FIELD_EMPTY,
                onNavColab = {},
                onNavPlantList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSHeaderPagePreviewDataUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSListHeaderContent(
                flagProgress = true,
                osList = listOf(),
                recoverList = {},
                setId = {},
                recoverAndUpdateData = {},
                flagMsgUpdate = false,
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                levelUpdate = LevelUpdate.CLEAN,
                tableUpdate = "tb_os",
                currentProgress = 0.25555f,
                errors = Errors.FIELD_EMPTY,
                onNavColab = {},
                onNavPlantList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSHeaderPagePreviewFinishUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSListHeaderContent(
                flagProgress = true,
                osList = listOf(),
                recoverList = {},
                setId = {},
                recoverAndUpdateData = {},
                flagMsgUpdate = false,
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "tb_os",
                currentProgress = 1f,
                errors = Errors.FIELD_EMPTY,
                onNavColab = {},
                onNavPlantList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSHeaderPagePreviewFailureUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSListHeaderContent(
                flagProgress = true,
                osList = listOf(),
                recoverList = {},
                setId = {},
                recoverAndUpdateData = {},
                flagMsgUpdate = false,
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "tb_os",
                currentProgress = 1f,
                errors = Errors.UPDATE,
                onNavColab = {},
                onNavPlantList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSHeaderPagePreviewFailure() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSListHeaderContent(
                flagProgress = false,
                osList = listOf(),
                recoverList = {},
                setId = {},
                recoverAndUpdateData = {},
                flagMsgUpdate = false,
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "tb_os",
                currentProgress = 1f,
                errors = Errors.EXCEPTION,
                onNavColab = {},
                onNavPlantList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSHeaderPagePreviewList() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSListHeaderContent(
                flagProgress = false,
                osList = listOf(
                    OSScreenModel(
                        id = 1,
                        os = "OS 99975",
                        period = "DIÁRIO",
                        codPlant = "1.04.01.04",
                        descPlant = "PRÉDIO"
                    ),
                    OSScreenModel(
                        id = 1,
                        os = "OS 99976",
                        period = "DIÁRIO",
                        codPlant = "1.04.01.05",
                        descPlant = "PATIO"
                    ),
                ),
                recoverList = {},
                setId = {},
                recoverAndUpdateData = {},
                flagMsgUpdate = false,
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0.0f,
                errors = Errors.FIELD_EMPTY,
                onNavColab = {},
                onNavPlantList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}