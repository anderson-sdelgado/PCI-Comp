package br.com.usinasantafe.pci.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pci.domain.entities.stable.Colab
import br.com.usinasantafe.pci.utils.TB_COLAB

@Entity(tableName = TB_COLAB)
data class ColabRoomModel (
    @PrimaryKey
    val idColab: Int,
    val regColab: Long,
    val nameColab: String,
    val idFactorySectionColab: Int,
)

fun ColabRoomModel.roomModelToEntity(): Colab {
    return with(this){
        Colab(
            idColab = idColab,
            regColab = regColab,
            nameColab = nameColab,
            idFactorySectionColab = idFactorySectionColab
        )
    }
}

fun Colab.entityToRoomModel(): ColabRoomModel {
    return with(this){
        ColabRoomModel(
            idColab = idColab,
            regColab = regColab,
            nameColab = nameColab,
            idFactorySectionColab = idFactorySectionColab
        )
    }
}

