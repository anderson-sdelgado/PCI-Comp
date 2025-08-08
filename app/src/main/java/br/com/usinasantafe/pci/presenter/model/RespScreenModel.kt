package br.com.usinasantafe.pci.presenter.model

import br.com.usinasantafe.pci.utils.OptionResp

data class RespScreenModel(
    val pos: Int,
    val desc: String,
    val option: OptionResp,
    val obs: String?,
)
