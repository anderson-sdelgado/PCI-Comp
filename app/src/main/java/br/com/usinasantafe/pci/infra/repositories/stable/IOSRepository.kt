package br.com.usinasantafe.pci.infra.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.OS
import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.stable.OSRepository
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.OSRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.room.stable.OSRoomDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pci.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IOSRepository @Inject constructor(
    private val osRoomDatasource: OSRoomDatasource,
    private val osRetrofitDatasource: OSRetrofitDatasource
): OSRepository {

    override suspend fun addAll(list: List<OS>): Result<Boolean> {
        try {
            val modelList = list.map { it.entityToRoomModel() }
            val result = osRoomDatasource.addAll(modelList)
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
        val result = osRoomDatasource.deleteAll()
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
    ): Result<List<OS>> {
        try {
            val result = osRetrofitDatasource.listByIdFactorySection(
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

}