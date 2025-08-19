package br.com.usinasantafe.pci.infra.models.retrofit.variable

import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.utils.Status
import java.text.SimpleDateFormat
import java.util.Locale

data class HeaderRetrofitModelOutput(
    val id: Int,
    val idColab: Int,
    val idFactorySection: Int,
    val idOS: Int,
    val dateHour: String,
    val respList: List<RespRetrofitModelOutput>,
    val number: Long,
    val status: Int,
    val idServ: Int?
)

data class HeaderRetrofitModelInput(
    val id: Int,
    val idServ: Int,
    val respList: List<RespRetrofitModelInput>,
)

fun HeaderRoomModel.headerRoomModelToHeaderRetrofitModel(
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
                Locale.Builder().setLanguage("pt").setRegion("BR").build()
            ).format(this.dateHour),
            respList = respList,
            number = number,
            status = if(status != Status.FINISH) 1 else 2,
            idServ = idServ
        )
    }
}