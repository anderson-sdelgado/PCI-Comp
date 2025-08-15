package br.com.usinasantafe.pci.infra.models.sharedpreferences

import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import java.util.Date

data class HeaderSharedPreferencesModel(
    var idColab: Int? = null,
    var idFactorySection: Int? = null,
    var idOS: Int? = null
)

fun HeaderSharedPreferencesModel.sharedPreferencesModelToRoomModel(): HeaderRoomModel {
    return with(this) {
        HeaderRoomModel(
            idColab = idColab!!,
            idFactorySection = idFactorySection!!,
            idOS = idOS!!,
            dateHour = Date()
        )
    }
}