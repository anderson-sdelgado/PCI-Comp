package br.com.usinasantafe.pci.external.room.datasource.stable

import br.com.usinasantafe.pci.domain.errors.resultFailure
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
            return resultFailure(
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
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listAll(): Result<List<ItemRoomModel>> {
        return try {
            Result.success(itemDao.all())
        } catch (e: Exception) {
            resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }

    }

    override suspend fun listByIdOSAndIdPlant(
        idOS: Int,
        idPlant: Int
    ): Result<List<ItemRoomModel>> {
        try {
            val list = itemDao.listByIdOSAndIdPlant(idOS, idPlant)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getById(id: Int): Result<ItemRoomModel> {
        try {
            val item = itemDao.getById(id)
            return Result.success(item)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}