package br.com.usinasantafe.pci.infra.models.retrofit.variable

import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HeaderRetrofitModelOutput(
    val id: Int,
    val idColab: Int,
    val idFactorySection: Int,
    val idOS: Int,
    val dateHour: String,
    val respList: List<RespRetrofitModelOutput>,
)

data class HeaderRetrofitModelInput(
    val id: Int,
    val idServ: Int,
    val respList: List<RespRetrofitModelInput>,
)

fun HeaderRoomModel.roomModelToRetrofitModel(
    number: Long,
    respList: List<RespRetrofitModelOutput>,
): HeaderRetrofitModelOutput {
    return with(this) {
        HeaderRetrofitModelOutput(
            id = id!!,
            idColab = idColab,
            idFactorySection = idFactorySection,
            idOS = idOS,
            dateHour = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dateHour),
            respList = respList,
        )
    }
}
