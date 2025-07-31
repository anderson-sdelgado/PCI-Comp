package br.com.usinasantafe.pci.infra.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Item
import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ItemRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.stable.ItemRoomDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pci.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IItemRepository @Inject constructor(
    private val itemRoomDatasource: ItemRoomDatasource,
    private val itemRetrofitDatasource: ItemRetrofitDatasource
): ItemRepository {

    override suspend fun addAll(list: List<Item>): Result<Boolean> {
        try {
            val modelList = list.map { it.entityToRoomModel() }
            val result = itemRoomDatasource.addAll(modelList)
            if(result.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = itemRoomDatasource.deleteAll()
        if(result.isFailure){
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listByIdOS(
        token: String,
        idOS: Int
    ): Result<List<Item>> {
        try {
            val result = itemRetrofitDatasource.listByIdOS(
                token = token,
                idOS = idOS
            )
            if(result.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listAll(): Result<List<Item>> {
        try {
            val result = itemRoomDatasource.listAll()
            if(result.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entityList = result.getOrNull()!!.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}