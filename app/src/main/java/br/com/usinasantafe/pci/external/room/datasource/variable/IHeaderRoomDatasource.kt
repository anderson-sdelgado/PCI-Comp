package br.com.usinasantafe.pci.external.room.datasource.variable

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.pci.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.utils.Status
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IHeaderRoomDatasource @Inject constructor(
    private val headerDao: HeaderDao
): HeaderRoomDatasource {

    override suspend fun save(model: HeaderRoomModel): Result<Boolean> {
        try {
            val list = headerDao.listByIdColabAndIdOS(
                idColab = model.idColab,
                idOS = model.idOS
            )
            if (list.isNotEmpty()) {
                val modelBD = list[0]
                modelBD.status = Status.OPEN
                headerDao.update(modelBD)
                return Result.success(true)
            }
            headerDao.insert(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdByStatusOpen(): Result<Int> {
        try {
            val model = headerDao.getByStatus(Status.OPEN)
            return Result.success(model.id!!)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun close(): Result<Boolean> {
        try {
            val list = headerDao.listByStatus(Status.OPEN)
            for (model in list) {
                model.status = Status.CLOSE
                headerDao.update(model)
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun finish(): Result<Boolean> {
        try {
            val model = headerDao.getByStatus(Status.OPEN)
            model.status = Status.FINISH
            headerDao.update(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdOSList(ids: List<Int>): Result<List<HeaderRoomModel>> {
        try {
            val models = headerDao.listByIdOSList(ids)
            return Result.success(models)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIds(ids: List<Int>): Result<List<HeaderRoomModel>> {
        try {
            val models = headerDao.listByIds(ids)
            return Result.success(models)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setIdServById(
        id: Int,
        idServ: Int
    ): Result<Boolean> {
        try {
            val model = headerDao.getById(id)
            model.idServ = idServ
            headerDao.update(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdOSByStatusOpen(): Result<Int> {
        try {
            val model = headerDao.getByStatus(Status.OPEN)
            return Result.success(model.idOS)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun all(): Result<List<HeaderRoomModel>> {
        try {
            val models = headerDao.all()
            return Result.success(models)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        try {
            headerDao.delete(id)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkOpen(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}