package br.com.usinasantafe.pci.presenter.model

data class OSScreenModel(
    val idOS: Int,
    val period: String,
    val nroOS: Int,
    val codPlant: String,
    val descPlant: String,
    val status: Boolean
)
