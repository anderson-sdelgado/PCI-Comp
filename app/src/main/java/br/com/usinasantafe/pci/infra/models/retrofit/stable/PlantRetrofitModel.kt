package br.com.usinasantafe.pci.infra.models.retrofit.stable

import br.com.usinasantafe.pci.domain.entities.stable.Plant

data class PlantRetrofitModel(
    val idPlant: Int,
    val codPlant: String,
    val descPlant: String,
    val idFactorySectionPlant: Int
)

fun PlantRetrofitModel.retrofitModelToEntity(): Plant {
    return with(this) {
        Plant(
            idPlant = idPlant,
            codPlant = codPlant,
            descPlant = descPlant,
            idFactorySectionPlant = idFactorySectionPlant
        )
    }
}