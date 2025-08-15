package br.com.usinasantafe.pci.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pci.domain.entities.variable.Header
import br.com.usinasantafe.pci.utils.Status
import br.com.usinasantafe.pci.utils.StatusSend
import br.com.usinasantafe.pci.utils.TB_HEADER
import java.util.Date

@Entity(tableName = TB_HEADER)
data class HeaderRoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val idColab: Int,
    val idFactorySection: Int,
    val idOS: Int,
    val dateHour: Date,
    var status: Status = Status.OPEN,
    var idServ: Int? = null,
)

fun HeaderRoomModel.roomModelToEntity(): Header {
    return with(this) {
        Header(
            id = id,
            idColab = idColab,
            idFactorySection = idFactorySection,
            idOS = idOS,
            status = status,
            dateHour = dateHour
        )
    }
}

fun Header.entityToRoomModel(): HeaderRoomModel {
    return with(this) {
        HeaderRoomModel(
            id = id,
            idColab = idColab!!,
            idFactorySection = idFactorySection!!,
            idOS = idOS!!,
            status = status,
            dateHour = dateHour
        )
    }
}
