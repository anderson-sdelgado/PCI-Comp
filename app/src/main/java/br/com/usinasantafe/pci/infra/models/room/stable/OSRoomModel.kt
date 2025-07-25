package br.com.usinasantafe.pci.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pci.domain.entities.stable.OS
import br.com.usinasantafe.pci.utils.TB_OS

@Entity(tableName = TB_OS)
data class OSRoomModel(
    @PrimaryKey
    val idOS: Int,
    val nroOS: Int,
    val idPlantOS: Int,
    val qtdDayOS: Int,
    val descPeriodOS: String
)

fun OSRoomModel.roomModelToEntity(): OS {
    return with(this){
        OS(
            idOS = idOS,
            nroOS = nroOS,
            idPlantOS = idPlantOS,
            qtdDayOS = qtdDayOS,
            descPeriodOS = descPeriodOS
        )
    }
}

fun OS.entityToRoomModel(): OSRoomModel {
    return with(this){
        OSRoomModel(
            idOS = idOS,
            nroOS = nroOS,
            idPlantOS = idPlantOS,
            qtdDayOS = qtdDayOS,
            descPeriodOS = descPeriodOS
        )
    }
}

