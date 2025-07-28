package br.com.usinasantafe.pci.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pci.domain.entities.stable.Plant
import br.com.usinasantafe.pci.utils.TB_PLANT

@Entity(tableName = TB_PLANT)
data class PlantRoomModel(
    @PrimaryKey
    val idPlant: Int,
    val codPlant: String,
    val descPlant: String
)

fun PlantRoomModel.roomModelToEntity(): Plant {
    return with(this){
        Plant(
            idPlant = idPlant,
            codPlant = codPlant,
            descPlant = descPlant
        )
    }
}

fun Plant.entityToRoomModel(): PlantRoomModel {
    return with(this){
        PlantRoomModel(
            idPlant = idPlant,
            codPlant = codPlant,
            descPlant = descPlant
        )
    }
}