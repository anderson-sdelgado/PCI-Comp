package br.com.usinasantafe.pci.presenter.view.header.oslist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.usinasantafe.pci.R
import br.com.usinasantafe.pci.presenter.model.OSModel
import br.com.usinasantafe.pci.presenter.theme.ItemListDesign
import br.com.usinasantafe.pci.presenter.theme.ItemListOSDesign
import br.com.usinasantafe.pci.presenter.theme.TitleDesign
import br.com.usinasantafe.pci.presenter.theme.PCITheme
import br.com.usinasantafe.pci.presenter.theme.TextButtonDesign

@Composable
fun OSListHeaderScreen(
    osListHeaderViewModel: OSListHeaderViewModel = hiltViewModel(),
    onNavColab: () -> Unit,
) {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSListHeaderContent(
                flagCheckUpdate = true,
                osList = listOf(),
                onNavColab = onNavColab,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun OSListHeaderContent(
    flagCheckUpdate: Boolean,
    osList: List<OSModel>,
    onNavColab: () -> Unit,
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
        if (flagCheckUpdate) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                )
                Spacer(
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "Update in progress...",
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
                        period = os.period,
                        os = os.os,
                        codPlant = os.codPlant,
                        descPlant = os.descPlant,
                        setActionItem = {},
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
                text = stringResource(id = R.string.text_pattern_return)
            )
        }
        BackHandler {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OSHeaderPagePreviewUpdate() {
    PCITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSListHeaderContent(
                flagCheckUpdate = true,
                osList = listOf(),
                onNavColab = {},
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
                flagCheckUpdate = false,
                osList = listOf(
                    OSModel(
                        id = 1,
                        os = "OS 99975",
                        period = "DIÁRIO",
                        codPlant = "1.04.01.04",
                        descPlant = "PRÉDIO"
                    ),
                    OSModel(
                        id = 1,
                        os = "OS 99976",
                        period = "DIÁRIO",
                        codPlant = "1.04.01.05",
                        descPlant = "PATIO"
                    ),
                ),
                onNavColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}