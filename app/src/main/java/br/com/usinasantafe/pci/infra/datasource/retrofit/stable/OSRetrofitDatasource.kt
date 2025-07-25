package br.com.usinasantafe.pci.infra.datasource.retrofit.stable

import br.com.usinasantafe.pci.infra.models.retrofit.stable.OSRetrofitModel

interface OSRetrofitDatasource {
    suspend fun listByIdFactorySection(
        token: String,
        idFactorySection: Int
    ): Result<List<OSRetrofitModel>>
}