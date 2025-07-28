package br.com.usinasantafe.pci.infra.datasource.retrofit.stable

import br.com.usinasantafe.pci.infra.models.retrofit.stable.PlantRetrofitModel


interface PlantRetrofitDatasource {
    suspend fun listByIdFactorySection(
        token: String,
        idFactorySection: Int
    ): Result<List<PlantRetrofitModel>>
}