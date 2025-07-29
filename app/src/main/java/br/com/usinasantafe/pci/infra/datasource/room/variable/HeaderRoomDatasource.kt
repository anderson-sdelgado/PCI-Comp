package br.com.usinasantafe.pci.infra.datasource.room.variable

import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel

interface HeaderRoomDatasource {
    suspend fun save(model: HeaderRoomModel): Result<Boolean>
}