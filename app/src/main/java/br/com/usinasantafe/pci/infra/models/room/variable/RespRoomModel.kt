package br.com.usinasantafe.pci.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pci.domain.entities.variable.Resp
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.Status
import br.com.usinasantafe.pci.utils.StatusSend
import br.com.usinasantafe.pci.utils.TB_RESP

@Entity(tableName = TB_RESP)
data class RespRoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val idHeader: Int,
    val idItem: Int,
    val idPlant: Int,
    var option: OptionResp,
    var obs: String? = null,
    val status: Status = Status.OPEN,
    var statusSend: StatusSend = StatusSend.SEND
)

fun RespRoomModel.roomModelToEntity(): Resp {
    return with(this) {
        Resp(
            id = id,
            idHeader = idHeader,
            idItem = idItem,
            idPlant = idPlant,
            option = option,
            obs = obs,
        )
    }
}

fun Resp.entityToRoomModel(): RespRoomModel {
    return with(this) {
        RespRoomModel(
            id = id,
            idHeader = idHeader!!,
            idItem = idItem,
            idPlant = idPlant,
            option = option,
            obs = obs,
        )
    }
}