package br.com.usinasantafe.pci.external.room.datasource.stable

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.external.room.dao.stable.PlantDao
import br.com.usinasantafe.pci.infra.datasource.room.stable.PlantRoomDatasource
import br.com.usinasantafe.pci.infra.models.room.stable.PlantRoomModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IPlantRoomDatasource @Inject constructor(
    private val plantDao: PlantDao
): PlantRoomDatasource {

    override suspend fun addAll(list: List<PlantRoomModel>): Result<Boolean> {
        try {
            plantDao.insertAll(list)
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
            plantDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdFactorySection(idFactorySection: Int): Result<List<PlantRoomModel>> {
        try {
            val list = plantDao.listByIdFactorySection(idFactorySection)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIds(ids: List<Int>): Result<List<PlantRoomModel>> {
        try {
            val list = plantDao.listByIds(ids)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}