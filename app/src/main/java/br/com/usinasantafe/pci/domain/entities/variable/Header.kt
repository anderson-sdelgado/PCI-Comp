package br.com.usinasantafe.pci.domain.entities.variable

import br.com.usinasantafe.pci.utils.Status

data class Header(
    val id: Int? = null,
    val idColab: Int?= null,
    val idFactorySection: Int? = null,
    val idOS: Int? = null,
    val status: Status = Status.OPEN
)