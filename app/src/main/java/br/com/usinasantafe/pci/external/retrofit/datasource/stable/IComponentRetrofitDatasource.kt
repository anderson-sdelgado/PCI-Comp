package br.com.usinasantafe.pci.external.retrofit.datasource.stable

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.external.retrofit.api.stable.ComponentApi
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ComponentRetrofitDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ComponentRetrofitModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IComponentRetrofitDatasource @Inject constructor(
    private val componentApi: ComponentApi
): ComponentRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<ComponentRetrofitModel>> {
        try {
            val response = componentApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}