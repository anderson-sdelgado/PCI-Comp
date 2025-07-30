package br.com.usinasantafe.pci.external.retrofit.datasource.stable

import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ItemRetrofitDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ItemRetrofitModel
import javax.inject.Inject

class IItemRetrofitDatasource @Inject constructor(

): ItemRetrofitDatasource {
    override suspend fun listByIdOS(
        token: String,
        idOS: Int
    ): Result<List<ItemRetrofitModel>> {
        TODO("Not yet implemented")
    }
}