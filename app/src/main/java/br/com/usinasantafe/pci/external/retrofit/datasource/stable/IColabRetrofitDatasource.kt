package br.com.usinasantafe.pci.external.retrofit.datasource.stable

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.external.retrofit.api.stable.ColabApi
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ColabRetrofitModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IColabRetrofitDatasource @Inject constructor(
    private val colabApi: ColabApi
) : ColabRetrofitDatasource {
    override suspend fun listAll(token: String): Result<List<ColabRetrofitModel>> {
        try {
            val response = colabApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getByReg(
        token: String,
        regColab: Int
    ): Result<ColabRetrofitModel> {
        try {
            val response = colabApi.getByReg(
                auth = token,
                regColab = regColab
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