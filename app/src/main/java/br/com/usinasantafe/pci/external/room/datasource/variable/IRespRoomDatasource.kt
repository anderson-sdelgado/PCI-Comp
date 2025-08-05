package br.com.usinasantafe.pci.external.room.datasource.variable

import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.datasource.room.variable.RespRoomDatasource
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import javax.inject.Inject

class IRespRoomDatasource  @Inject constructor(
    private val respDao: RespDao
): RespRoomDatasource {
    override suspend fun save(model: RespRoomModel): Result<Boolean> {
        TODO("Not yet implemented")
    }
}