package br.com.usinasantafe.pci.infra.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Component
import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ComponentRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.stable.ComponentRoomDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pci.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IComponentRepository @Inject constructor(
    private val componentRoomDatasource: ComponentRoomDatasource,
    private val componentRetrofitDatasource: ComponentRetrofitDatasource
): ComponentRepository {

    override suspend fun addAll(list: List<Component>): Result<Boolean> {
        try {
            val modelList = list.map { it.entityToRoomModel() }
            val result = componentRoomDatasource.addAll(modelList)
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
        val result = componentRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listAll(token: String): Result<List<Component>> {
        try {
            val result = componentRetrofitDatasource.listAll(token)
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

}