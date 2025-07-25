package br.com.usinasantafe.pci.external.retrofit.datasource.stable

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.external.retrofit.api.stable.OSApi
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.OSRetrofitDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IOSRetrofitDatasource @Inject constructor(
    private val osApi: OSApi
): OSRetrofitDatasource {

    override suspend fun listByIdFactorySection(
        token: String,
        idFactorySection: Int
    ): Result<List<OSRetrofitModel>> {
        try {
            val response = osApi.listByIdFactorySection(
                auth = token,
                idFactorySection = idFactorySection
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