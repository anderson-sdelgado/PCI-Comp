package br.com.usinasantafe.pci.domain.entities.variable

import br.com.usinasantafe.pci.utils.OptionResp

data class Resp(
    val id: Int? = null,
    val idHeader: Int? = null,
    val idItem: Int,
    val option: OptionResp,
    val obs: String? = null,
)