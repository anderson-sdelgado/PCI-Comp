package br.com.usinasantafe.pci.infra.models.retrofit.variable

import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.Status
import java.text.SimpleDateFormat
import java.util.Locale

data class RespRetrofitModelOutput(
    val id: Int,
    val idHeader: Int,
    val idItem: Int,
    val idPlant: Int,
    var option: Int,
    var obs: String?,
    val dateHour: String,
    val status: Int,
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
            option = if(option == OptionResp.NON_CONFORMING) 1 else 2,
            obs = obs,
            dateHour = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.Builder().setLanguage("pt").setRegion("BR").build()
            ).format(this.dateHour),
            status = if(status != Status.FINISH) 1 else 2,
            idServ = idServ
        )
    }
}
