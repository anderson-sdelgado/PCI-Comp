package br.com.usinasantafe.pci.domain.entities.variable

import br.com.usinasantafe.pci.utils.FlagUpdate
import br.com.usinasantafe.pci.utils.StatusSend

data class Config(
    var number: Long? = null,
    var password: String? = null,
    var idBD: Int? = null,
    var version: String? = null,
    var statusSend: StatusSend = StatusSend.STARTED,
    val flagUpdate: FlagUpdate = FlagUpdate.OUTDATED,
)
