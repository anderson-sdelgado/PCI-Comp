package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ServiceRepository
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface GetItem {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetItem @Inject constructor(
    private val itemRepository: ItemRepository,
    private val serviceRepository: ServiceRepository,
    private val componentRepository: ComponentRepository
): GetItem {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultGetItem = itemRepository.getById(id)
            if (resultGetItem.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGetItem.exceptionOrNull()!!
                )
            }
            val item = resultGetItem.getOrNull()!!
            val resultGetService = serviceRepository.getById(item.idServiceItem)
            if (resultGetService.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGetService.exceptionOrNull()!!
                )
            }
            val service = resultGetService.getOrNull()!!
            if(item.idComponentItem == 0) return Result.success(service.descService)
            val resultGetComponent = componentRepository.getById(item.idComponentItem)
            if (resultGetComponent.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGetComponent.exceptionOrNull()!!
                )
            }
            val component = resultGetComponent.getOrNull()!!
            val desc = "${service.descService}\n${component.codComponent} - ${component.descComponent}"
            return Result.success(desc)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}