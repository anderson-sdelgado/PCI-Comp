package br.com.usinasantafe.pci.external.retrofit.datasource.stable

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.external.retrofit.api.stable.ServiceApi
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ServiceRetrofitDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ServiceRetrofitModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IServiceRetrofitDatasource @Inject constructor(
    private val serviceApi: ServiceApi
): ServiceRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<ServiceRetrofitModel>> {
        try {
            val response = serviceApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}