package br.com.usinasantafe.pci.infra.models.retrofit.variable

import br.com.usinasantafe.pci.domain.entities.variable.Config

data class ConfigRetrofitModelOutput(
    var number: Long,
    var version: String,
)

data class ConfigRetrofitModelInput(
    val idBD: Int
)

fun Config.entityToRetrofitModel(): ConfigRetrofitModelOutput {
    require(number != 0L) { "The field 'number' cannot is null." }
    return ConfigRetrofitModelOutput(
        number = number!!,
        version = version!!
    )
}

fun ConfigRetrofitModelInput.retrofitToEntity(): Config {
    require(idBD != 0) { "The field 'idBD' cannot is null." }
    return Config(
        idBD = idBD,
    )
}