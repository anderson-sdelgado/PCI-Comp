package br.com.usinasantafe.pci.presenter.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.usinasantafe.pci.R
import br.com.usinasantafe.pci.utils.TypeButton
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun addTextField(text: String, char: String): String {
    return text + char
}

fun addTextFieldComma(text: String, digit: String): String {

    val cleanText = text.filter { it.isDigit() } + digit
    val valueInTenths = cleanText.toLongOrNull() ?: 0L
    val value = valueInTenths / 10.0

    val format = DecimalFormat("#,##0.0")
    val localePtBr = Locale.forLanguageTag("pt-BR")
    format.decimalFormatSymbols = DecimalFormatSymbols(localePtBr)
    return format.format(value)
}


fun clearTextField(text: String): String {
    return if (text.length > 1) text.substring(0, text.length - 1) else ""
}

fun clearTextFieldComma(text: String): String {
    val cleanText = text.filter { it.isDigit() }
    val reducedText = if (cleanText.length > 1) cleanText.dropLast(1) else ""
    val valueInTenths = reducedText.toLongOrNull() ?: 0L
    val value = valueInTenths / 10.0

    val format = DecimalFormat("#,##0.0")
    val localePtBr = Locale.forLanguageTag("pt-BR")
    format.decimalFormatSymbols = DecimalFormatSymbols(localePtBr)
    return format.format(value)
}

@Composable
fun ButtonsGenericNumeric(
    setActionButton: (
        text: String,
        typeButton: TypeButton,
    ) -> Unit,
    flagUpdate: Boolean = true,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val text_number_1 = stringResource(id = R.string.text_number_1)
        val text_number_2 = stringResource(id = R.string.text_number_2)
        val text_number_3 = stringResource(id = R.string.text_number_3)
        val text_number_4 = stringResource(id = R.string.text_number_4)
        val text_number_5 = stringResource(id = R.string.text_number_5)
        val text_number_6 = stringResource(id = R.string.text_number_6)
        val text_number_7 = stringResource(id = R.string.text_number_7)
        val text_number_8 = stringResource(id = R.string.text_number_8)
        val text_number_9 = stringResource(id = R.string.text_number_9)
        val text_number_0 = stringResource(id = R.string.text_number_0)
        val text_clean = stringResource(id = R.string.text_pattern_clean)
        val text_ok = stringResource(id = R.string.text_pattern_ok)
        val text_update = stringResource(id = R.string.text_pattern_update)
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            ButtonNumericDesign(
                {
                    TextButtonNumericDesign(
                        text = text_number_1
                    )
                },
                {
                    setActionButton(
                        text_number_1,
                        TypeButton.NUMERIC
                    )
                },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                {
                    TextButtonNumericDesign(
                        text = text_number_2
                    )
                },
                {
                    setActionButton(
                        text_number_2,
                        TypeButton.NUMERIC
                    )
                },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                {
                    TextButtonNumericDesign(
                        text = text_number_3
                    )
                },
                {
                    setActionButton(
                        text_number_3,
                        TypeButton.NUMERIC
                    )
                },
                Modifier
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ButtonNumericDesign(
                {
                    TextButtonNumericDesign(
                        text = text_number_4
                    )
                },
                {
                    setActionButton(
                        text_number_4,
                        TypeButton.NUMERIC
                    )
                },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                {
                    TextButtonNumericDesign(
                        text = text_number_5
                    )
                },
                {
                    setActionButton(
                        text_number_5,
                        TypeButton.NUMERIC
                    )
                },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                {
                    TextButtonNumericDesign(
                        text = text_number_6
                    )
                },
                {
                    setActionButton(
                        text_number_6,
                        TypeButton.NUMERIC
                    )
                },
                Modifier
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ButtonNumericDesign(
                {
                    TextButtonNumericDesign(
                        text = text_number_7
                    )
                },
                {
                    setActionButton(
                        text_number_7,
                        TypeButton.NUMERIC
                    )
                },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                {
                    TextButtonNumericDesign(
                        text = text_number_8
                    )
                },
                {
                    setActionButton(
                        text_number_8,
                        TypeButton.NUMERIC
                    )
                },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                {
                    TextButtonNumericDesign(
                        text = text_number_9
                    )
                },
                {
                    setActionButton(
                        text_number_9,
                        TypeButton.NUMERIC
                    )
                },
                Modifier
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ButtonNumericDesign(
                text = {
                    TextButtonCleanDesign(
                        text = text_clean
                    )
                },
                {
                    setActionButton(
                        text_clean,
                        TypeButton.CLEAN
                    )
                },
                modifier = Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                {
                    TextButtonNumericDesign(
                        text = text_number_0
                    )
                },
                {
                    setActionButton(
                        text_number_0,
                        TypeButton.NUMERIC
                    )
                },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                {
                    TextButtonNumericDesign(
                        text = text_ok
                    )
                },
                {
                    setActionButton(
                        text_ok,
                        TypeButton.OK
                    )
                },
                Modifier
                    .weight(1f)
            )
        }
        if(flagUpdate){
            Row(
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ButtonNumericDesign(
                    {
                        TextButtonNumericDesign(
                            text = text_update
                        )
                    },
                    {
                        setActionButton(
                            text_update,
                            TypeButton.UPDATE
                        )
                    },
                    Modifier
                        .weight(1f)
                )
            }
        }
    }
}
