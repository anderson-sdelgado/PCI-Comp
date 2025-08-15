package br.com.usinasantafe.pci.infra.datasource.room.variable

import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel

interface HeaderRoomDatasource {
    suspend fun save(model: HeaderRoomModel): Result<Boolean>
    suspend fun getIdByStatusOpen(): Result<Int>
    suspend fun close(): Result<Boolean>
    suspend fun finish(): Result<Boolean>
    suspend fun listByIdOSList(ids: List<Int>): Result<List<HeaderRoomModel>>
    suspend fun listByIds(ids: List<Int>): Result<List<HeaderRoomModel>>
    suspend fun setIdServById(
        id: Int,
        idServ: Int
    ): Result<Boolean>
    suspend fun getIdOSByStatusOpen(): Result<Int>
    suspend fun all(): Result<List<HeaderRoomModel>>
    suspend fun delete(id: Int): Result<Boolean>

}