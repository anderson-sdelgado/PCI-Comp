package br.com.usinasantafe.pci.presenter.view.note.questiondesc

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
fun QuestionDescNoteScreen() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionDescNoteContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun QuestionDescNoteContent(
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
fun QuestionDescNotePagePreview() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            QuestionDescNoteContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
