package br.com.usinasantafe.pci.infra.models.retrofit.stable

import br.com.usinasantafe.pci.domain.entities.stable.Colab

data class ColabRetrofitModel(
    val idColab: Int,
    val regColab: Long,
    val nameColab: String,
    val idFactorySectionColab: Int,
)

fun ColabRetrofitModel.retrofitModelToEntity(): Colab {
    return with(this) {
        Colab(
            idColab = idColab,
            regColab = regColab,
            nameColab = nameColab,
            idFactorySectionColab = idFactorySectionColab
        )
    }
}
