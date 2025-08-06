package br.com.usinasantafe.pci.external.room.datasource.variable

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.datasource.room.variable.RespRoomDatasource
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IRespRoomDatasource  @Inject constructor(
    private val respDao: RespDao
): RespRoomDatasource {
    override suspend fun save(model: RespRoomModel): Result<Boolean> {
        try {
            respDao.save(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdItems(idItemList: List<Int>): Result<List<RespRoomModel>> {
        try {
            val list = respDao.listByIdItems(idItemList)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}