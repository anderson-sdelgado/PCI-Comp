package br.com.usinasantafe.pci.presenter.model

import br.com.usinasantafe.pci.domain.entities.variable.Config

data class ConfigScreenModel(
    val number: String,
    val password: String,
)

fun Config.toConfigModel(): ConfigScreenModel {
    return with(this){
        ConfigScreenModel(
            number = this.number!!.toString(),
            password = this.password!!,
        )
    }
}