package br.com.usinasantafe.pci.external.room.datasource.stable

import br.com.usinasantafe.pci.infra.datasource.room.stable.ItemRoomDatasource
import br.com.usinasantafe.pci.infra.models.room.stable.ItemRoomModel
import javax.inject.Inject

class IItemRoomDatasource @Inject constructor(
): ItemRoomDatasource {

    override suspend fun addAll(list: List<ItemRoomModel>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}