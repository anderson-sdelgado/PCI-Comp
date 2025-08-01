package br.com.usinasantafe.pci.infra.models.retrofit.stable

import br.com.usinasantafe.pci.domain.entities.stable.Component

data class ComponentRetrofitModel(
    val idComponent: Int,
    val codComponent: String,
    val descComponent: String,
)

fun ComponentRetrofitModel.retrofitModelToEntity(): Component {
    return with(this) {
        Component(
            idComponent = idComponent,
            codComponent = codComponent,
            descComponent = descComponent,
        )
    }
}

