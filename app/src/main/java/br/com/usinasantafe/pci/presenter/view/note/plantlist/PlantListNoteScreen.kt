package br.com.usinasantafe.pci.presenter.view.note.plantlist

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
import br.com.usinasantafe.pci.presenter.model.PlantScreenModel
import br.com.usinasantafe.pci.presenter.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pci.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pci.presenter.theme.ItemListPlantDesign
import br.com.usinasantafe.pci.presenter.theme.TitleDesign
import br.com.usinasantafe.pci.presenter.theme.PCITheme
import br.com.usinasantafe.pci.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate

@Composable
fun PlantListNoteScreen(
    viewModel: PlantListNoteViewModel = hiltViewModel(),
    onNavQuestionList: (Int) -> Unit,
    onNavSplash: () -> Unit
) {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recoverAndUpdateData()
            }

            PlantListContent(
                plantList = uiState.plantList,
                recoverList = viewModel::recoverList,
                flagMsgUpdate = uiState.flagMsgUpdate,
                recoverAndUpdateData = viewModel::recoverAndUpdateData,
                setFlagMsgUpdate = viewModel::setFlagMsgUpdate,
                setCloseDialog = viewModel::setCloseDialog,
                flagProgress = uiState.flagProgress,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                currentProgress = uiState.currentProgress,
                levelUpdate = uiState.levelUpdate,
                tableUpdate = uiState.tableUpdate,
                errors = uiState.errors,
                onNavQuestionList = onNavQuestionList,
                onNavSplash = onNavSplash,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun PlantListContent(
    plantList: List<PlantScreenModel>,
    recoverList: () -> Unit,
    flagMsgUpdate: Boolean,
    recoverAndUpdateData: () -> Unit,
    setFlagMsgUpdate: () -> Unit,
    setCloseDialog: () -> Unit,
    flagProgress: Boolean,
    flagDialog: Boolean,
    failure: String,
    currentProgress: Float,
    levelUpdate: LevelUpdate?,
    tableUpdate: String,
    errors: Errors,
    onNavQuestionList: (Int) -> Unit,
    onNavSplash: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_plant
            )
        )
        Spacer(
            modifier = Modifier.padding(vertical = 4.dp)
        )
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
                items(plantList) { plant ->
                    ItemListPlantDesign(
                        id = plant.id,
                        cod = plant.cod,
                        desc = plant.desc,
                        status = plant.status,
                        setActionItem = { onNavQuestionList(plant.id) },
                        font = 24,
                        padding = 6
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = setFlagMsgUpdate,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_update)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onNavSplash,
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
    }

    if(flagMsgUpdate) {
        AlertDialogCheckDesign(
            text = stringResource(id = R.string.text_question_update),
            setCloseDialog = setCloseDialog,
            setActionButtonYes = {
                recoverAndUpdateData()
            }
        )
    }

    LaunchedEffect(levelUpdate) {
        if(levelUpdate == LevelUpdate.FINISH_UPDATE_COMPLETED){
            recoverList()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PlantListPagePreviewUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlantListContent(
                flagProgress = true,
                plantList = listOf(),
                recoverList = {},
                recoverAndUpdateData = {},
                setFlagMsgUpdate = {},
                flagMsgUpdate = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0.0f,
                errors = Errors.FIELD_EMPTY,
                onNavQuestionList = {},
                onNavSplash = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantListPagePreviewDataUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlantListContent(
                flagProgress = true,
                plantList = listOf(),
                recoverList = {},
                recoverAndUpdateData = {},
                setFlagMsgUpdate = {},
                flagMsgUpdate = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                levelUpdate = LevelUpdate.CLEAN,
                tableUpdate = "tb_item",
                currentProgress = 0.25555f,
                errors = Errors.FIELD_EMPTY,
                onNavQuestionList = {},
                onNavSplash = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantListPagePreviewFinishUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlantListContent(
                flagProgress = true,
                plantList = listOf(),
                recoverList = {},
                recoverAndUpdateData = {},
                setFlagMsgUpdate = {},
                flagMsgUpdate = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "tb_plant",
                currentProgress = 1f,
                errors = Errors.FIELD_EMPTY,
                onNavQuestionList = {},
                onNavSplash = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantListPagePreviewFailureUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlantListContent(
                flagProgress = true,
                plantList = listOf(),
                recoverList = {},
                recoverAndUpdateData = {},
                setFlagMsgUpdate = {},
                flagMsgUpdate = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "tb_os",
                currentProgress = 1f,
                errors = Errors.UPDATE,
                onNavQuestionList = {},
                onNavSplash = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantListPagePreviewFailure() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlantListContent(
                flagProgress = false,
                plantList = listOf(),
                recoverList = {},
                recoverAndUpdateData = {},
                setFlagMsgUpdate = {},
                flagMsgUpdate = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "tb_os",
                currentProgress = 1f,
                errors = Errors.UPDATE,
                onNavQuestionList = {},
                onNavSplash = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantListPagePreviewList() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlantListContent(
                flagProgress = false,
                plantList = listOf(
                    PlantScreenModel(
                        id = 1,
                        cod = "1",
                        desc = "Plant 1",
                        status = true
                    ),
                    PlantScreenModel(
                        id = 2,
                        cod = "2",
                        desc = "Plant 2",
                        status = false
                    )
                ),
                recoverList = {},
                recoverAndUpdateData = {},
                setFlagMsgUpdate = {},
                flagMsgUpdate = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "Failure",
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "tb_os",
                currentProgress = 1f,
                errors = Errors.UPDATE,
                onNavQuestionList = {},
                onNavSplash = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}