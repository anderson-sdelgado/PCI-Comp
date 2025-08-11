package br.com.usinasantafe.pci.presenter.view.note.questionlist

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
import br.com.usinasantafe.pci.presenter.model.ItemScreenModel
import br.com.usinasantafe.pci.presenter.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pci.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pci.presenter.theme.ItemListItemDesign
import br.com.usinasantafe.pci.presenter.theme.TitleDesign
import br.com.usinasantafe.pci.presenter.theme.PCITheme
import br.com.usinasantafe.pci.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import br.com.usinasantafe.pci.utils.OptionResp

@Composable
fun QuestionListNoteScreen(
    viewModel: QuestionListNoteViewModel = hiltViewModel(),
    onNavPlantList: () -> Unit,
    onNavQuestionResp: (Int) -> Unit,
    onNavQuestionDesc: (Int) -> Unit
) {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.checkAndUpdateData()
            }

            QuestionListNoteContent(
                itemList = uiState.itemList,
                recoverList = viewModel::recoverList,
                closeItem = viewModel::closeItem,
                flagDialogCheck = uiState.flagDialogCheck,
                setDialogCheck = viewModel::setDialogCheck,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagProgress = uiState.flagProgress,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                currentProgress = uiState.currentProgress,
                levelUpdate = uiState.levelUpdate,
                tableUpdate = uiState.tableUpdate,
                errors = uiState.errors,
                onNavPlantList = onNavPlantList,
                onNavQuestionResp = onNavQuestionResp,
                onNavQuestionDesc = onNavQuestionDesc,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun QuestionListNoteContent(
    itemList: List<ItemScreenModel>,
    recoverList: () -> Unit,
    closeItem: () -> Unit,
    flagDialogCheck: Boolean,
    setDialogCheck: (Boolean) -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    flagProgress: Boolean,
    flagDialog: Boolean,
    failure: String,
    currentProgress: Float,
    levelUpdate: LevelUpdate?,
    tableUpdate: String,
    errors: Errors,
    onNavPlantList: () -> Unit,
    onNavQuestionResp: (Int) -> Unit,
    onNavQuestionDesc: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_list_question
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
                    LevelUpdate.CHECK -> stringResource(id = R.string.text_msg_check_data)
                    LevelUpdate.RECOVERY -> stringResource(id = R.string.text_msg_recovery, tableUpdate)
                    LevelUpdate.CLEAN -> stringResource(id = R.string.text_msg_clean, tableUpdate)
                    LevelUpdate.SAVE -> stringResource(id = R.string.text_msg_save, tableUpdate)
                    LevelUpdate.GET_TOKEN -> stringResource(id = R.string.text_msg_get_token)
                    LevelUpdate.SAVE_TOKEN -> stringResource(id = R.string.text_msg_save_token)
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
                items(itemList) { item ->
                    ItemListItemDesign(
                        id = item.id,
                        pos = item.pos,
                        descService = item.descService,
                        descComponent = item.descComponent,
                        option = item.option,
                        setActionItem = {
                            if(item.option == null) onNavQuestionResp(item.id) else onNavQuestionDesc(item.id)
                        },
                        font = 24,
                        padding = 6
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = { setDialogCheck(true) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_button_close)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = onNavPlantList,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_return)
            )
        }
        BackHandler {}

    }

    if (flagDialogCheck) {
        AlertDialogCheckDesign(
            text = stringResource(id = R.string.text_question_close_item),
            setCloseDialog = { setDialogCheck(false) },
            setActionButtonYes = closeItem
        )
    }

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

    LaunchedEffect(levelUpdate) {
        if(levelUpdate == LevelUpdate.FINISH_UPDATE_COMPLETED){
            recoverList()
        }
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess){
            onNavPlantList()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun QuestionListNotePagePreview() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionListNoteContent(
                itemList = listOf(),
                recoverList = {},
                closeItem = {},
                flagDialogCheck = false,
                setDialogCheck = {},
                flagAccess = false,
                setCloseDialog = {},
                flagProgress = true,
                flagDialog = false,
                failure = "",
                levelUpdate = null,
                tableUpdate = "",
                currentProgress = 0.0f,
                errors = Errors.FIELD_EMPTY,
                onNavPlantList = {},
                onNavQuestionResp = {},
                onNavQuestionDesc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionListNotePagePreviewFinishUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionListNoteContent(
                itemList = listOf(),
                recoverList = {},
                closeItem = {},
                flagDialogCheck = false,
                setDialogCheck = {},
                flagAccess = false,
                setCloseDialog = {},
                flagProgress = true,
                flagDialog = false,
                failure = "",
                levelUpdate = LevelUpdate.CLEAN,
                tableUpdate = "tb_item",
                currentProgress = 0.25555f,
                errors = Errors.FIELD_EMPTY,
                onNavPlantList = {},
                onNavQuestionResp = {},
                onNavQuestionDesc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionListNotePagePreviewDataUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionListNoteContent(
                itemList = listOf(),
                recoverList = {},
                closeItem = {},
                flagDialogCheck = false,
                setDialogCheck = {},
                flagAccess = false,
                setCloseDialog = {},
                flagProgress = true,
                flagDialog = false,
                failure = "",
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "tb_plant",
                currentProgress = 1f,
                errors = Errors.FIELD_EMPTY,
                onNavPlantList = {},
                onNavQuestionResp = {},
                onNavQuestionDesc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionListNotePagePreviewFailureUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionListNoteContent(
                itemList = listOf(),
                recoverList = {},
                closeItem = {},
                flagDialogCheck = false,
                setDialogCheck = {},
                flagAccess = false,
                setCloseDialog = {},
                flagProgress = true,
                flagDialog = true,
                failure = "Failure",
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "tb_os",
                currentProgress = 1f,
                errors = Errors.UPDATE,
                onNavPlantList = {},
                onNavQuestionResp = {},
                onNavQuestionDesc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionListNotePagePreviewUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionListNoteContent(
                itemList = listOf(),
                recoverList = {},
                closeItem = {},
                flagDialogCheck = false,
                setDialogCheck = {},
                flagAccess = false,
                setCloseDialog = {},
                flagProgress = false,
                flagDialog = true,
                failure = "Failure",
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "tb_os",
                currentProgress = 1f,
                errors = Errors.UPDATE,
                onNavPlantList = {},
                onNavQuestionResp = {},
                onNavQuestionDesc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionListNotePagePreviewData() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionListNoteContent(
                itemList = listOf(
                    ItemScreenModel(
                        id = 1,
                        pos = 1,
                        descService = "Service 1",
                        descComponent = "Component 1",
                        option = null
                    ),
                    ItemScreenModel(
                        id = 2,
                        pos = 2,
                        descService = "Service 2",
                        descComponent = "Component 2",
                        option = null
                    ),
                    ItemScreenModel(
                        id = 3,
                        pos = 3,
                        descService = "Service 3",
                        descComponent = "Component 3",
                        option = OptionResp.ACCORDING
                    ),
                    ItemScreenModel(
                        id = 4,
                        pos = 4,
                        descService = "Service 4",
                        descComponent = "Component 4",
                        option = OptionResp.NON_CONFORMING
                    )
                ),
                recoverList = {},
                closeItem = {},
                flagDialogCheck = false,
                setDialogCheck = {},
                flagAccess = false,
                setCloseDialog = {},
                flagProgress = false,
                flagDialog = false,
                failure = "Failure",
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "tb_os",
                currentProgress = 1f,
                errors = Errors.UPDATE,
                onNavPlantList = {},
                onNavQuestionResp = {},
                onNavQuestionDesc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuestionListNotePagePreviewDataMsgClose() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionListNoteContent(
                itemList = listOf(
                    ItemScreenModel(
                        id = 1,
                        pos = 1,
                        descService = "Service 1",
                        descComponent = "Component 1",
                        option = null
                    ),
                    ItemScreenModel(
                        id = 2,
                        pos = 2,
                        descService = "Service 2",
                        descComponent = "Component 2",
                        option = null
                    ),
                    ItemScreenModel(
                        id = 3,
                        pos = 3,
                        descService = "Service 3",
                        descComponent = "Component 3",
                        option = OptionResp.ACCORDING
                    ),
                    ItemScreenModel(
                        id = 4,
                        pos = 4,
                        descService = "Service 4",
                        descComponent = "Component 4",
                        option = OptionResp.NON_CONFORMING
                    )
                ),
                recoverList = {},
                closeItem = {},
                flagDialogCheck = true,
                setDialogCheck = {},
                flagAccess = false,
                setCloseDialog = {},
                flagProgress = false,
                flagDialog = false,
                failure = "Failure",
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                tableUpdate = "tb_os",
                currentProgress = 1f,
                errors = Errors.UPDATE,
                onNavPlantList = {},
                onNavQuestionResp = {},
                onNavQuestionDesc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}