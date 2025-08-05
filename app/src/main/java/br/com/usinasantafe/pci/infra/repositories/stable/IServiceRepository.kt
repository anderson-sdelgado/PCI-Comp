package br.com.usinasantafe.pci.infra.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Service
import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.stable.ServiceRepository
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ServiceRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.stable.ServiceRoomDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pci.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IServiceRepository @Inject constructor(
    private val serviceRoomDatasource: ServiceRoomDatasource,
    private val serviceRetrofitDatasource: ServiceRetrofitDatasource
): ServiceRepository {

    override suspend fun addAll(list: List<Service>): Result<Boolean> {
        try {
            val modelList = list.map { it.entityToRoomModel() }
            val result = serviceRoomDatasource.addAll(modelList)
            if (result.isFailure) {
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
        val result = serviceRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listAll(token: String): Result<List<Service>> {
        try {
            val result = serviceRetrofitDatasource.listAll(token)
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIds(ids: List<Int>): Result<List<Service>> {
        try {
            val result = serviceRoomDatasource.listByIds(ids)
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entityList = result.getOrNull()!!.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getById(id: Int): Result<Service> {
        try {
            val result = serviceRoomDatasource.getById(id)
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entity = result.getOrNull()!!.roomModelToEntity()
            return Result.success(entity)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}