package br.com.usinasantafe.pci.domain.entities.variable

import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.Status

data class Resp(
    val id: Int? = null,
    val idHeader: Int? = null,
    val idItem: Int,
    val idPlant: Int,
    val option: OptionResp,
    val obs: String? = null,
    val status: Status = Status.OPEN
)