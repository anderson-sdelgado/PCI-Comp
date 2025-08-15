package br.com.usinasantafe.pci.external.retrofit.datasource.variable

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.external.retrofit.api.variable.CheckListApi
import br.com.usinasantafe.pci.infra.datasource.retrofit.variable.CheckListRetrofitDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.variable.HeaderRetrofitModelInput
import br.com.usinasantafe.pci.infra.models.retrofit.variable.HeaderRetrofitModelOutput
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class ICheckListRetrofitDatasource @Inject constructor(
    private val checkListApi: CheckListApi
): CheckListRetrofitDatasource {

    override suspend fun send(
        token: String,
        retrofitModelOutputList: List<HeaderRetrofitModelOutput>
    ): Result<List<HeaderRetrofitModelInput>> {
        try {
            val response = checkListApi.send(
                token,
                retrofitModelOutputList
            )
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}