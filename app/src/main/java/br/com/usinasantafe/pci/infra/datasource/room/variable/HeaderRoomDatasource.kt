package br.com.usinasantafe.pci.infra.datasource.room.variable

import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.utils.Status

interface HeaderRoomDatasource {
    suspend fun save(model: HeaderRoomModel): Result<Boolean>
    suspend fun getByStatusOpenDefault(status: Status = Status.OPEN): Result<HeaderRoomModel>
    suspend fun close(): Result<Boolean>
    suspend fun finish(): Result<Boolean>
    suspend fun listByIdOSList(ids: List<Int>): Result<List<HeaderRoomModel>>
    suspend fun listByIds(ids: List<Int>): Result<List<HeaderRoomModel>>
}