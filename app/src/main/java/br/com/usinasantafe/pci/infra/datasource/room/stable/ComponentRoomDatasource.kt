package br.com.usinasantafe.pci.infra.datasource.room.stable

import br.com.usinasantafe.pci.infra.models.room.stable.ComponentRoomModel

interface ComponentRoomDatasource {
    suspend fun addAll(list: List<ComponentRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByIds(ids: List<Int>): Result<List<ComponentRoomModel>>
    suspend fun getById(id: Int): Result<ComponentRoomModel>
}