package br.com.usinasantafe.pci.presenter.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun ItemListDesign(
    id: Int = 0,
    text: String,
    font: Int = 22,
    padding: Int = 8,
    setActionItem: () -> Unit
) {
    return Text(
        textAlign = TextAlign.Left,
        text = text,
        fontSize = font.sp,
        modifier = Modifier
            .padding(vertical = padding.dp)
            .fillMaxWidth()
            .clickable {
                setActionItem()
            }
            .testTag("item_list_$id")
    )
}

@Composable
fun TitleDesign(
    text: String,
    font: Int = 30,
    padding: Int = 8
) {
    return Text(
        textAlign = TextAlign.Center,
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = font.sp,
        modifier = Modifier
            .padding(vertical = padding.dp)
            .fillMaxWidth()
    )
}


@Composable
fun TextButtonDesign(text: String) {
    return Text(
        textAlign = TextAlign.Center,
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    )
}

const val TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE = "tag_button_ok_alert_dialog_simple"
const val TAG_BUTTON_YES_ALERT_DIALOG_CHECK = "tag_button_yes_alert_dialog_check"
const val TAG_BUTTON_NO_ALERT_DIALOG_CHECK = "tag_button_no_alert_dialog_check"

@Composable
fun AlertDialogSimpleDesign(
    text: String,
    setCloseDialog: () -> Unit
) {
    return AlertDialog(
        title = {
            Text(
                text = "ATENÇÃO",
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = text,
                modifier = Modifier.testTag("text_alert_dialog_simple")
            )
        },
        onDismissRequest = setCloseDialog,
        confirmButton = {
            Button(
                onClick = setCloseDialog,
                modifier = Modifier.testTag(TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE)
            ) {
                Text("OK")
            }
        },
    )
}

@Composable
fun AlertDialogSimpleDesign(
    text: String,
    setCloseDialog: () -> Unit,
    setActionButtonOK: () -> Unit
) {
    return AlertDialog(
        title = {
            Text(
                text = "ATENÇÃO",
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = text,
                modifier = Modifier.testTag("text_alert_dialog_simple")
            )
        },
        onDismissRequest = setCloseDialog,
        confirmButton = {
            Button(
                onClick = setActionButtonOK,
                modifier = Modifier.testTag("button_ok_alert_dialog_simple")
            ) {
                Text("OK")
            }
        },
    )
}

@Composable
fun AlertDialogCheckDesign(
    text: String,
    setCloseDialog: () -> Unit,
    setActionButtonOK: () -> Unit
) {
    return AlertDialog(
        title = {
            Text(
                text = "ATENÇÃO",
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = text,
                modifier = Modifier.testTag("text_alert_dialog_check")
            )
        },
        onDismissRequest = setCloseDialog,
        confirmButton = {
            Button(
                onClick = setActionButtonOK,
                modifier = Modifier.testTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
            ) {
                Text("SIM")
            }
        },
        dismissButton = {
            Button(
                onClick = setCloseDialog,
                modifier = Modifier.testTag(TAG_BUTTON_NO_ALERT_DIALOG_CHECK)
            ) {
                Text("NÃO")
            }
        }
    )
}

@Composable
fun AlertDialogProgressDesign(
    currentProgress: Float,
    msgProgress: String,
) {
    return Dialog(
        onDismissRequest = {}
    ) {
        Card {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "ATENÇÃO",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                LinearProgressIndicator(
                    progress = { currentProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                )
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    text = msgProgress,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun AlertDialogProgressIndeterminateDesign(
    msgProgress: String,
) {
    return Dialog(
        onDismissRequest = {}
    ) {
        Card {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "ATENÇÃO",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                )
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    text = msgProgress,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ButtonNumericDesign(
    text: @Composable () -> Unit,
    setActionButton: () -> Unit,
    modifier: Modifier
) {
    return ElevatedButton(
        onClick = {
            setActionButton()
        },
        modifier = modifier
            .fillMaxHeight(),
        shape = RoundedCornerShape(10.dp)
    ) {
        text()
    }
}

@Composable
fun TextButtonNumericDesign(
    text: String,
) {
    return Text(
        textAlign = TextAlign.Center,
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun TextButtonCleanDesign(
    text: String
) {
    return Text(
        textAlign = TextAlign.Center,
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        modifier = Modifier
            .fillMaxWidth()
    )
}
