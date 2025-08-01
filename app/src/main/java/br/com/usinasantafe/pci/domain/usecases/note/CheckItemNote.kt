package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ServiceRepository
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface CheckItemNote {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckItemNote @Inject constructor(
    private val itemRepository: ItemRepository,
    private val componentRepository: ComponentRepository,
    private val serviceRepository: ServiceRepository
): CheckItemNote {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val result = itemRepository.listAll()
            if(result.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entityList = result.getOrNull()!!
            val idComponentList = entityList.map { it.idComponentItem }.distinct().filter { it != 0 }
            val resultComponentList = componentRepository.listByIds(
                ids = idComponentList
            )
            if(resultComponentList.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultComponentList.exceptionOrNull()!!
                )
            }
            val componentList = resultComponentList.getOrNull()!!
            if(idComponentList.size != componentList.size) return Result.success(false)
            val idServiceList = entityList.map { it.idServiceItem }.distinct()
            val resultServiceList = serviceRepository.listByIds(
                ids = idServiceList
            )
            if(resultServiceList.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultServiceList.exceptionOrNull()!!
                )
            }
            val serviceList = resultServiceList.getOrNull()!!
            if(idServiceList.size != serviceList.size) return Result.success(false)
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}