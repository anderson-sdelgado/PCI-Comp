package br.com.usinasantafe.pci.infra.datasource.retrofit.stable

import br.com.usinasantafe.pci.infra.models.retrofit.stable.ComponentRetrofitModel

interface ComponentRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ComponentRetrofitModel>>
}