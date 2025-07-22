package br.com.usinasantafe.pci.infra.models.sharedpreferences

import br.com.usinasantafe.pci.domain.entities.variable.Config

data class ConfigSharedPreferencesModel(
    var number: Long? = null,
    var password: String? = null,
)

fun ConfigSharedPreferencesModel.sharedPreferencesModelToEntity(): Config {
    return with(this) {
        Config(
            password = password,
            number = number,
        )
    }
}

fun Config.entityToSharedPreferencesModel(): ConfigSharedPreferencesModel {
    return with(this) {
        ConfigSharedPreferencesModel(
            password = password,
            number = number,
        )
    }
}
