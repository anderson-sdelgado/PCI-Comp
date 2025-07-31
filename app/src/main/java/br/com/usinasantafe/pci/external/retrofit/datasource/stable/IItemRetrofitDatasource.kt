package br.com.usinasantafe.pci.external.retrofit.datasource.stable

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.external.retrofit.api.stable.ItemApi
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ItemRetrofitDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ItemRetrofitModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IItemRetrofitDatasource @Inject constructor(
    private val itemApi: ItemApi
): ItemRetrofitDatasource {
    override suspend fun listByIdOS(
        token: String,
        idOS: Int
    ): Result<List<ItemRetrofitModel>> {
        try {
            val response = itemApi.listByIdOS(
                auth = token,
                idOS = idOS
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