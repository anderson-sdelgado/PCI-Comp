package br.com.usinasantafe.pci.infra.models.retrofit.variable

import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.utils.OptionResp

data class RespRetrofitModelOutput(
    val id: Int,
    val idHeader: Int,
    val idItem: Int,
    val idPlant: Int,
    var option: OptionResp,
    var obs: String?,
    val idServ: Int?
)

data class RespRetrofitModelInput(
    val id: Int,
    val idServ: Int,
)

fun RespRoomModel.respRoomModelToRespRetrofitModel(): RespRetrofitModelOutput {
    return with(this) {
        RespRetrofitModelOutput(
            id = id!!,
            idHeader = idHeader,
            idItem = idItem,
            idPlant = idPlant,
            option = option,
            obs = obs,
            idServ = idServ
        )
    }
}
