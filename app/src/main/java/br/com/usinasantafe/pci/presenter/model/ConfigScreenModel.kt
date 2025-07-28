package br.com.usinasantafe.pci.presenter.model

import br.com.usinasantafe.pci.domain.entities.variable.Config

data class ConfigModel(
    val number: String,
    val password: String,
)

fun Config.toConfigModel(): ConfigModel {
    return with(this){
        ConfigModel(
            number = this.number!!.toString(),
            password = this.password!!,
        )
    }
}