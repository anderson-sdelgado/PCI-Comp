package br.com.usinasantafe.pci.presenter.view.header.oslist

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
fun OSListHeaderScreen() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSListHeaderContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun OSListHeaderContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = "ORDEM SERVIÇO")
    }
}

@Preview(showBackground = true)
@Composable
fun OSHeaderPagePreview() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSListHeaderContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}