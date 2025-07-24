package br.com.usinasantafe.pci.infra.datasource.retrofit.stable

import br.com.usinasantafe.pci.infra.models.retrofit.stable.ColabRetrofitModel

interface ColabRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ColabRetrofitModel>>
    suspend fun getByReg(
        token: String,
        regColab: Int
    ): Result<ColabRetrofitModel>
}