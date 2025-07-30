package br.com.usinasantafe.pci.presenter.view.note.questionresp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.usinasantafe.pci.presenter.theme.TitleDesign
import br.com.usinasantafe.pci.presenter.theme.PCITheme

@Composable
fun QuestionRespNoteScreen() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionRespNoteContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun QuestionRespNoteContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = "")
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionRespNotePagePreview() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionRespNoteContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}