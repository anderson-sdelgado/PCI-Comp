package br.com.usinasantafe.pci.infra.models.retrofit.variable

import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.utils.OptionResp

data class RespRetrofitModelOutput(
    val id: Int,
    val idItem: Int,
    val idPlant: Int,
    var option: OptionResp,
    var obs: String? = null,
)

data class RespRetrofitModelInput(
    val id: Int,
    val idServ: Int,
)

fun RespRoomModel.roomModelToRetrofitModel(): RespRetrofitModelOutput {
    return with(this) {
        RespRetrofitModelOutput(
            id = id!!,
            idItem = idItem,
            idPlant = idPlant,
            option = option,
            obs = obs,
        )
    }
}
