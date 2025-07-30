package br.com.usinasantafe.pci.infra.models.retrofit.stable

import br.com.usinasantafe.pci.domain.entities.stable.Service

data class ServiceRetrofitModel(
    val idService: Int,
    val codService: Int,
    val descService: String,
)

fun ServiceRetrofitModel.retrofitModelToEntity(): Service {
    return with(this) {
        Service(
            idService = idService,
            codService = codService,
            descService = descService,
        )
    }
}
