package br.com.usinasantafe.pci.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pci.domain.entities.stable.Service
import br.com.usinasantafe.pci.utils.TB_COMPONENT
import br.com.usinasantafe.pci.utils.TB_SERVICE

@Entity(tableName = TB_SERVICE)
data class ServiceRoomModel(
    @PrimaryKey
    val idService: Int,
    val codService: Int,
    val descService: String,
)

fun ServiceRoomModel.roomModelToEntity(): Service {
    return with(this){
        Service(
            idService = idService,
            codService = codService,
            descService = descService
        )
    }
}

fun Service.entityToRoomModel(): ServiceRoomModel {
    return with(this){
        ServiceRoomModel(
            idService = idService,
            codService = codService,
            descService = descService
        )
    }
}