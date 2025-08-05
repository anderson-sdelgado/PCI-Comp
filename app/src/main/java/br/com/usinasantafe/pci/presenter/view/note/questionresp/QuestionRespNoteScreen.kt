package br.com.usinasantafe.pci.presenter.view.note.questionresp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import br.com.usinasantafe.pci.utils.OptionResp

@Composable
fun QuestionRespNoteScreen(
    viewModel: QuestionRespNoteViewModel = hiltViewModel(),
    onNavQuestionList: () -> Unit,
    onNavQuestionObs: () -> Unit
) {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            QuestionRespNoteContent(
                desc = uiState.desc,
                setResp = viewModel::setResp,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onNavQuestionList = onNavQuestionList,
                onNavQuestionObs = onNavQuestionObs,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun QuestionRespNoteContent(
    desc: String,
    setResp: (OptionResp) -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean?,
    flagDialog: Boolean,
    failure: String,
    onNavQuestionList: () -> Unit,
    onNavQuestionObs: () -> Unit,
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = desc,
                fontSize = 28.sp,
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Button(
            onClick = { setResp(OptionResp.ACCORDING) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_button_according
                )
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Button(
            onClick = { setResp(OptionResp.NON_CONFORMING) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_button_non_compliant
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
                    id = R.string.text_pattern_return
                )
            )
        }
        BackHandler {
        }
    }

    if(flagDialog) {
        AlertDialogSimpleDesign(
            text = stringResource(id = R.string.text_failure, failure),
            setCloseDialog = setCloseDialog
        )
    }

    LaunchedEffect(flagAccess) {
        flagAccess?.let {
            if(flagAccess){
                onNavQuestionList()
            } else {
                onNavQuestionObs()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun QuestionRespNotePagePreview() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionRespNoteContent(
                desc = "Item 1",
                setResp = {},
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                onNavQuestionList = {},
                onNavQuestionObs = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionRespNotePagePreviewFailure() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionRespNoteContent(
                desc = "Item 1",
                setResp = {},
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                onNavQuestionList = {},
                onNavQuestionObs = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}