package br.com.usinasantafe.pci.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pci.domain.entities.stable.Item
import br.com.usinasantafe.pci.utils.TB_ITEM

@Entity(tableName = TB_ITEM)
data class ItemRoomModel(
    @PrimaryKey
    val idItem: Int,
    val seqItem: Int,
    val idOSItem: Int,
    val idPlantItem: Int,
    val idComponentItem: Int,
    val idServiceItem: Int,
)

fun ItemRoomModel.roomModelToEntity(): Item {
    return with(this){
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

fun Item.entityToRoomModel(): ItemRoomModel {
    return with(this){
        ItemRoomModel(
            idItem = idItem,
            seqItem = seqItem,
            idOSItem = idOSItem,
            idPlantItem = idPlantItem,
            idComponentItem = idComponentItem,
            idServiceItem = idServiceItem,
        )
    }
}