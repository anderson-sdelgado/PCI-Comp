package br.com.usinasantafe.pci.infra.datasource.room.variable

import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel

interface RespRoomDatasource {
    suspend fun save(model: RespRoomModel): Result<Boolean>
    suspend fun listByIdItems(idItemList: List<Int>): Result<List<RespRoomModel>>
    suspend fun getByIdItem(idItem: Int): Result<RespRoomModel>
    suspend fun closeItems(
        idHeader: Int,
        idPlant: Int
    ): Result<Boolean>

}