package br.com.usinasantafe.pci.presenter.view.note.questiondesc

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pci.R
import br.com.usinasantafe.pci.presenter.model.RespScreenModel
import br.com.usinasantafe.pci.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pci.presenter.theme.TitleDesign
import br.com.usinasantafe.pci.presenter.theme.PCITheme
import br.com.usinasantafe.pci.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pci.utils.OptionResp

@Composable
fun QuestionDescNoteScreen(
    viewModel: QuestionDescNoteViewModel = hiltViewModel(),
    onNavQuestionList: () -> Unit,
    onNavQuestionResp: () -> Unit
) {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            QuestionDescNoteContent(
                resp = uiState.resp,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onNavQuestionList = onNavQuestionList,
                onNavQuestionResp = onNavQuestionResp,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun QuestionDescNoteContent(
    resp: RespScreenModel?,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
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
                id = R.string.text_title_question
            )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.text_question_pos,
                    resp?.pos ?: ""
                ),
                style = TextStyle(
                    fontSize = 28.sp,
                    lineHeight = 34.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text(
                text = stringResource(
                    R.string.text_question_desc,
                    resp?.desc ?: ""
                ),
                style = TextStyle(
                    fontSize = 28.sp,
                    lineHeight = 34.sp
                )
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text(
                text = when(resp?.option) {
                    OptionResp.ACCORDING -> stringResource(id = R.string.text_option_according)
                    OptionResp.NON_CONFORMING -> stringResource(id = R.string.text_option_non_compliant)
                    null -> ""
                },
                style = TextStyle(
                    fontSize = 28.sp,
                    lineHeight = 34.sp,
                    color =  when(resp?.option) {
                        OptionResp.ACCORDING -> Color.Green
                        OptionResp.NON_CONFORMING -> Color.Red
                        null -> Color.Black
                    }
                )
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text(
                text = stringResource(
                    R.string.text_question_obs,
                    resp?.obs ?: ""
                ),
                style = TextStyle(
                    fontSize = 28.sp,
                    lineHeight = 34.sp
                )
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Button(
            onClick = onNavQuestionResp,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_button_edit
                )
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Button(
            onClick = onNavQuestionList,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_pattern_cancel
                )
            )
        }
        BackHandler {}
    }

    if(flagDialog) {
        AlertDialogSimpleDesign(
            text = stringResource(id = R.string.text_failure, failure),
            setCloseDialog = setCloseDialog
        )
    }

}

@Preview(showBackground = true)
@Composable
fun QuestionDescNotePagePreview() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionDescNoteContent(
                resp = RespScreenModel(
                    pos = 1,
                    desc = "Service 1\n01 - Component 1",
                    option = OptionResp.ACCORDING,
                    obs = null
                ),
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                onNavQuestionList = {},
                onNavQuestionResp = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionDescNotePagePreviewNonConforming() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionDescNoteContent(
                resp = RespScreenModel(
                    pos = 1,
                    desc = "Service 1\n01 - Component 1",
                    option = OptionResp.NON_CONFORMING,
                    obs = "Obs Test"
                ),
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                onNavQuestionList = {},
                onNavQuestionResp = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuestionDescNotePagePreviewNull() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionDescNoteContent(
                resp = null,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                onNavQuestionList = {},
                onNavQuestionResp = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}