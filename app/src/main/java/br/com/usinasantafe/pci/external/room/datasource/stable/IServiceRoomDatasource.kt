package br.com.usinasantafe.pci.external.room.datasource.stable

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.external.room.dao.stable.ServiceDao
import br.com.usinasantafe.pci.infra.datasource.room.stable.ServiceRoomDatasource
import br.com.usinasantafe.pci.infra.models.room.stable.ServiceRoomModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IServiceRoomDatasource @Inject constructor(
    private val serviceDao: ServiceDao
): ServiceRoomDatasource {

    override suspend fun addAll(list: List<ServiceRoomModel>): Result<Boolean> {
        try {
            serviceDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            serviceDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIds(ids: List<Int>): Result<List<ServiceRoomModel>> {
        try {
            val list = serviceDao.listByIds(ids)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}