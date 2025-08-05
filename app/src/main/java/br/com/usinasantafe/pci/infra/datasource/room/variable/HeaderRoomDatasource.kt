package br.com.usinasantafe.pci.infra.datasource.room.variable

import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.utils.Status

interface HeaderRoomDatasource {
    suspend fun save(model: HeaderRoomModel): Result<Boolean>
    suspend fun getByStatus(status: Status): Result<HeaderRoomModel>
}