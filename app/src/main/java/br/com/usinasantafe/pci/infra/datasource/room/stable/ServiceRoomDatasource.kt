package br.com.usinasantafe.pci.infra.datasource.room.stable

import br.com.usinasantafe.pci.infra.models.room.stable.ServiceRoomModel

interface ServiceRoomDatasource {
    suspend fun addAll(list: List<ServiceRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByIds(ids: List<Int>): Result<List<ServiceRoomModel>>
}