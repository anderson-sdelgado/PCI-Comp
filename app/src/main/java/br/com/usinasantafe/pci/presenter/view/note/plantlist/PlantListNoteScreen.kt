package br.com.usinasantafe.pci.presenter.view.note.plantlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.usinasantafe.pci.presenter.theme.TitleDesign
import br.com.usinasantafe.pci.presenter.theme.PCITheme

@Composable
fun PlantListNoteScreen(
    viewModel: PlantListNoteViewModel = hiltViewModel(),
    onNavOSList: () -> Unit
) {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlantListContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun PlantListContent(
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
fun PlantListPagePreview() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlantListContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}