package br.com.usinasantafe.pci.domain.entities.variable

import br.com.usinasantafe.pci.utils.Status
import java.util.Date

data class Header(
    val id: Int? = null,
    val idColab: Int?= null,
    val idFactorySection: Int? = null,
    val idOS: Int? = null,
    val dateHour: Date = Date(),
    val status: Status = Status.OPEN
)