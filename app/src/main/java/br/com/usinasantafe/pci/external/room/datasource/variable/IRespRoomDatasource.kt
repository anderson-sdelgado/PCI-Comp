package br.com.usinasantafe.pci.external.room.datasource.variable

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.datasource.room.variable.RespRoomDatasource
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.utils.StatusSend
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IRespRoomDatasource  @Inject constructor(
    private val respDao: RespDao
): RespRoomDatasource {

    override suspend fun save(model: RespRoomModel): Result<Boolean> {
        try {
            val count = respDao.countByIdItem(model.idItem)
            if (count == 0) {
                respDao.insert(model)
                return Result.success(true)
            }
            val modelBD = respDao.getByIdItem(model.idItem)
            modelBD.option = model.option
            modelBD.obs = model.obs
            modelBD.statusSend = StatusSend.SEND
            respDao.update(modelBD)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
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
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getByIdItem(idItem: Int): Result<RespRoomModel> {
        try {
            val model = respDao.getByIdItem(idItem)
            return Result.success(model)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun finishItems(
        idHeader: Int,
        idPlant: Int
    ): Result<Boolean> {
        try {
            respDao.finishItems(
                idHeader = idHeader,
                idPlant = idPlant
            )
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }

    }

    override suspend fun listByIdHeaderAndIdPlant(
        idHeader: Int,
        idPlant: Int
    ): Result<List<RespRoomModel>> {
        try {
            val list = respDao.listByIdHeaderAndIdPlant(
                idHeader = idHeader,
                idPlant = idPlant
            )
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdHeader(idHeader: Int): Result<List<RespRoomModel>> {
        try {
            val list = respDao.listByIdHeader(
                idHeader = idHeader
            )
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkRespSend(): Result<Boolean> {
        try {
            val count = respDao.countRespSend()
            return Result.success(count > 0)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listRespSend(): Result<List<RespRoomModel>> {
        TODO("Not yet implemented")
    }

}