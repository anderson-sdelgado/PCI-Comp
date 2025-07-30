package br.com.usinasantafe.pci.external.room.datasource.stable

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.external.room.dao.stable.ComponentDao
import br.com.usinasantafe.pci.infra.datasource.room.stable.ComponentRoomDatasource
import br.com.usinasantafe.pci.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IComponentRoomDatasource @Inject constructor(
    private val componentDao: ComponentDao
): ComponentRoomDatasource {

    override suspend fun addAll(list: List<ComponentRoomModel>): Result<Boolean> {
        try {
            componentDao.insertAll(list)
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
            componentDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}