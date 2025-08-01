package br.com.usinasantafe.pci.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pci.domain.entities.stable.Component
import br.com.usinasantafe.pci.utils.TB_COMPONENT

@Entity(tableName = TB_COMPONENT)
data class ComponentRoomModel(
    @PrimaryKey
    val idComponent: Int,
    val codComponent: String,
    val descComponent: String,
)

fun ComponentRoomModel.roomModelToEntity(): Component {
    return with(this){
        Component(
            idComponent = idComponent,
            codComponent = codComponent,
            descComponent = descComponent
        )
    }
}

fun Component.entityToRoomModel(): ComponentRoomModel {
    return with(this){
        ComponentRoomModel(
            idComponent = idComponent,
            codComponent = codComponent,
            descComponent = descComponent
        )
    }
}
