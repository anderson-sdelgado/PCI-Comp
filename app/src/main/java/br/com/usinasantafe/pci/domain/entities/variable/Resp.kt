package br.com.usinasantafe.pci.domain.entities.variable

import br.com.usinasantafe.pci.utils.OptionResp

data class Resp(
    val id: Int? = null,
    val idHeader: Int? = null,
    val idItOSMechanic: Int? = null,
    val option: OptionResp? = null,
    val obs: String? = null,
)