package br.com.usinasantafe.pci.infra.datasource.room.variable

import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel

interface RespRoomDatasource {
    suspend fun save(model: RespRoomModel): Result<Boolean>
}