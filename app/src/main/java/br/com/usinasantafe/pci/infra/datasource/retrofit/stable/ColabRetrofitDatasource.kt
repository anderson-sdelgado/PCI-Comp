package br.com.usinasantafe.pci.infra.datasource.retrofit.stable

import br.com.usinasantafe.pci.infra.models.retrofit.stable.ColabRetrofitModel

interface ColabRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<ColabRetrofitModel>>
}