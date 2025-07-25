package br.com.usinasantafe.pci.infra.models.retrofit.stable

import br.com.usinasantafe.pci.domain.entities.stable.OS
import java.util.Date

data class OSRetrofitModel(
    val idOS: Int,
    val nroOS: Int,
    val idPlantOS: Int,
    val qtdDayOS: Int,
    val descPeriodOS: String
)

fun OSRetrofitModel.retrofitModelToEntity(): OS {
    return with(this) {
        OS(
            idOS = idOS,
            nroOS = nroOS,
            idPlantOS = idPlantOS,
            qtdDayOS = qtdDayOS,
            descPeriodOS = descPeriodOS
        )
    }
}


