package br.com.usinasantafe.pci.infra.datasource.retrofit.stable

import br.com.usinasantafe.pci.infra.models.retrofit.stable.ServiceRetrofitModel

interface ServiceRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ServiceRetrofitModel>>
}