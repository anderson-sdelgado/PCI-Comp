package br.com.usinasantafe.pci.infra.datasource.retrofit.stable

import br.com.usinasantafe.pci.domain.entities.stable.Item
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ItemRetrofitModel

interface ItemRetrofitDatasource {
    suspend fun listByIdOS(
        token: String,
        idOS: Int
    ): Result<List<ItemRetrofitModel>>
}