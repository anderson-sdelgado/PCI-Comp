package br.com.usinasantafe.pci.external.room.datasource.stable

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.external.room.dao.stable.ItemDao
import br.com.usinasantafe.pci.infra.datasource.room.stable.ItemRoomDatasource
import br.com.usinasantafe.pci.infra.models.room.stable.ItemRoomModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IItemRoomDatasource @Inject constructor(
    private val itemDao: ItemDao
): ItemRoomDatasource {

    override suspend fun addAll(list: List<ItemRoomModel>): Result<Boolean> {
        try {
            itemDao.insertAll(list)
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
            itemDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listAll(): Result<List<ItemRoomModel>> {
        return try {
            Result.success(itemDao.all())
        } catch (e: Exception) {
            resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }

    }

}