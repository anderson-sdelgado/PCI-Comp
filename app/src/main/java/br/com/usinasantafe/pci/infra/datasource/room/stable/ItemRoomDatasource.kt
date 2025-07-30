package br.com.usinasantafe.pci.infra.datasource.room.stable

import br.com.usinasantafe.pci.infra.models.room.stable.ItemRoomModel

interface ItemRoomDatasource {
    suspend fun addAll(list: List<ItemRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}