package br.com.usinasantafe.pci.infra.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Plant
import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.stable.PlantRepository
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.PlantRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.stable.PlantRoomDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pci.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IPlantRepository @Inject constructor(
    private val plantRoomDatasource: PlantRoomDatasource,
    private val plantRetrofitDatasource: PlantRetrofitDatasource
): PlantRepository {

    override suspend fun addAll(list: List<Plant>): Result<Boolean> {
        try {
            val modelList = list.map { it.entityToRoomModel() }
            val result = plantRoomDatasource.addAll(modelList)
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
        val result = plantRoomDatasource.deleteAll()
        if(result.isFailure){
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listByIdFactorySection(
        token: String,
        idFactorySection: Int
    ): Result<List<Plant>> {
        try {
            val result = plantRetrofitDatasource.listByIdFactorySection(
                token = token,
                idFactorySection = idFactorySection
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

    override suspend fun listAll(): Result<List<Plant>> {
        TODO("Not yet implemented")
    }

}