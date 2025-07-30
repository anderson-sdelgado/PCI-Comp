package br.com.usinasantafe.pci.infra.models.retrofit.stable

import br.com.usinasantafe.pci.domain.entities.stable.Item

data class ItemRetrofitModel(
    val idItem: Int,
    val seqItem: Int,
    val idOSItem: Int,
    val idPlantItem: Int,
    val idComponentItem: Int,
    val idServiceItem: Int,
)

fun ItemRetrofitModel.retrofitModelToEntity(): Item {
    return with(this) {
        Item(
            idItem = idItem,
            seqItem = seqItem,
            idOSItem = idOSItem,
            idPlantItem = idPlantItem,
            idComponentItem = idComponentItem,
            idServiceItem = idServiceItem,
        )
    }
}