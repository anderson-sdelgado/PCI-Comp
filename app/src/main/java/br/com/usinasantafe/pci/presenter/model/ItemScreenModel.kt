package br.com.usinasantafe.pci.presenter.model

import br.com.usinasantafe.pci.utils.OptionResp

data class ItemScreenModel(
    val id: Int,
    val pos: Int,
    val descService: String,
    val descComponent: String,
    val option: OptionResp?,
)
