package br.com.usinasantafe.pci.external.retrofit.datasource.variable

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.external.retrofit.api.variable.ConfigApi
import br.com.usinasantafe.pci.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.pci.infra.models.retrofit.variable.ConfigRetrofitModelOutput
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IConfigRetrofitDatasource @Inject constructor(
    private val configApi: ConfigApi
): ConfigRetrofitDatasource {

    override suspend fun recoverToken(retrofitModelOutput: ConfigRetrofitModelOutput): Result<ConfigRetrofitModelInput> {
        try {
            val response = configApi.send(
                retrofitModelOutput
            )
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}