package br.com.usinasantafe.pci.infra.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Colab
import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pci.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IColabRepository @Inject constructor(
    private val colabRoomDatasource: ColabRoomDatasource,
    private val colabRetrofitDatasource: ColabRetrofitDatasource
): ColabRepository {

    override suspend fun addAll(list: List<Colab>): Result<Boolean> {
        try {
            val modelList = list.map { it.entityToRoomModel() }
            val result = colabRoomDatasource.addAll(modelList)
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
        val result = colabRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listAll(token: String): Result<List<Colab>> {
        try {
            val result = colabRetrofitDatasource.listAll(token)
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

    override suspend fun getByRegColab(
        token: String,
        regColab: Int
    ): Result<Colab> {
        try {
            val result = colabRetrofitDatasource.getByReg(
                token = token,
                regColab = regColab
            )
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entity = result.getOrNull()!!.retrofitModelToEntity()
            return Result.success(entity)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun add(entity: Colab): Result<Boolean> {
        val result = colabRoomDatasource.add(entity.entityToRoomModel())
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}