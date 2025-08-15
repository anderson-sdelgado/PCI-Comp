package br.com.usinasantafe.pci.infra.datasource.retrofit.variable

import br.com.usinasantafe.pci.infra.models.retrofit.variable.HeaderRetrofitModelInput
import br.com.usinasantafe.pci.infra.models.retrofit.variable.HeaderRetrofitModelOutput

interface CheckListRetrofitDatasource {
    suspend fun send(
        token: String,
        retrofitModelOutputList: List<HeaderRetrofitModelOutput>
    ): Result<List<HeaderRetrofitModelInput>>
}