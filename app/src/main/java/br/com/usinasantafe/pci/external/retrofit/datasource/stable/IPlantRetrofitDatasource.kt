package br.com.usinasantafe.pci.external.retrofit.datasource.stable

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.external.retrofit.api.stable.PlantApi
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.PlantRetrofitDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.PlantRetrofitModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IPlantRetrofitDatasource @Inject constructor(
    private val plantApi: PlantApi
): PlantRetrofitDatasource {
    override suspend fun listByIdFactorySection(
        token: String,
        idFactorySection: Int
    ): Result<List<PlantRetrofitModel>> {
        try {
            val response = plantApi.listByIdFactorySection(
                auth = token,
                idFactorySection = idFactorySection
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