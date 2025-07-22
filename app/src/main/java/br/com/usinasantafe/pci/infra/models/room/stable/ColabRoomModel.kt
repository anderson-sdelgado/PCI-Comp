package br.com.usinasantafe.pci.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pci.utils.TB_COLAB

@Entity(tableName = TB_COLAB)
data class ColabRoomModel (
    @PrimaryKey
    val regColab: Long,
    val nameColab: String
)

//fun ColabRoomModel.roomModelToEntity(): Colab {
//    return with(this){
//        Colab(
//            regColab = this.regColab,
//            nameColab = this.nameColab,
//        )
//    }
//}
//
//fun Colab.entityToRoomModel(): ColabRoomModel {
//    return with(this){
//        ColabRoomModel(
//            regColab = this.regColab,
//            nameColab = this.nameColab,
//        )
//    }
//}
