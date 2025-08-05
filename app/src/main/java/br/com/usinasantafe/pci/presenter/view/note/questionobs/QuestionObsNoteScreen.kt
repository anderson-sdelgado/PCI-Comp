package br.com.usinasantafe.pci.presenter.view.note.questionobs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
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
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.OptionResp

const val TAG_OBS_TEXT_FIELD = "tag_obs_text_field"

@Composable
fun QuestionObsNoteScreen(
    viewModel: QuestionObsNoteViewModel = hiltViewModel(),
    onNavQuestionList: () -> Unit,
    onNavQuestionResp: () -> Unit
) {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            QuestionObsNoteContent(
                obs = uiState.obs,
                setResp = viewModel::setResp,
                onObsChanged = viewModel::onObsChanged,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                onNavQuestionList = onNavQuestionList,
                onNavQuestionResp = onNavQuestionResp,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun QuestionObsNoteContent(
    obs: String,
    setResp: () -> Unit,
    onObsChanged: (String) -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    onNavQuestionList: () -> Unit,
    onNavQuestionResp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_question_obs
            )
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = obs,
            onValueChange = onObsChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag(TAG_OBS_TEXT_FIELD),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 28.sp
            ),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = onNavQuestionResp,
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(
                        id = R.string.text_pattern_cancel
                    )
                )
            }
            Button(
                onClick = setResp,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(
                    text = stringResource(
                        id = R.string.text_pattern_ok
                    )
                )
            }
        }
        BackHandler {}
    }

    if(flagDialog) {
        val text = when (errors) {
            Errors.FIELD_EMPTY -> stringResource(
                id = R.string.text_field_empty,
                stringResource(id = R.string.text_title_colab)
            )
            Errors.EXCEPTION -> stringResource(
                id = R.string.text_failure,
                failure
            )
            else -> stringResource(
                id = R.string.text_failure,
                failure
            )
        }
        AlertDialogSimpleDesign(
            text = text,
            setCloseDialog = setCloseDialog
        )
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess){
            onNavQuestionList()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun QuestionObsNotePagePreview() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionObsNoteContent(
                obs = "Teste",
                setResp = {},
                onObsChanged = {},
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavQuestionList = {},
                onNavQuestionResp = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionObsNotePagePreviewFailureFieldEmpty() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionObsNoteContent(
                obs = "Teste",
                setResp = {},
                onObsChanged = {},
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                errors = Errors.FIELD_EMPTY,
                onNavQuestionList = {},
                onNavQuestionResp = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuestionObsNotePagePreviewFailure() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionObsNoteContent(
                obs = "Teste",
                setResp = {},
                onObsChanged = {},
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                errors = Errors.EXCEPTION,
                onNavQuestionList = {},
                onNavQuestionResp = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}