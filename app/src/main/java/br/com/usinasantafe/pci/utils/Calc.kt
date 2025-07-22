package br.com.usinasantafe.pci.utils

fun updatePercentage(pos: Float, count: Float, sizeAll: Float): Float {
    return percentage(pos + ((count - 1) * 3), sizeAll)
}

fun percentage(count: Float, size: Float): Float {
    return (count / size)
}

fun sizeUpdate(qtdTable: Float): Float {
    return (qtdTable * 3) + 1f
}