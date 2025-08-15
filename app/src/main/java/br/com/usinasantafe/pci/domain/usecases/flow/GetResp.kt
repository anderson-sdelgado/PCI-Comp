package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ServiceRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.presenter.model.RespScreenModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface GetResp {
    suspend operator fun invoke(id: Int): Result<RespScreenModel>
}

class IGetResp @Inject constructor(
    private val itemRepository: ItemRepository,
    private val serviceRepository: ServiceRepository,
    private val componentRepository: ComponentRepository,
    private val checkListRepository: CheckListRepository
): GetResp {

    override suspend fun invoke(id: Int): Result<RespScreenModel> {
        try {
            val resultGetItem = itemRepository.getById(id)
            if (resultGetItem.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetItem.exceptionOrNull()!!
                )
            }
            val item = resultGetItem.getOrNull()!!
            val resultGetService = serviceRepository.getById(item.idServiceItem)
            if (resultGetService.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetService.exceptionOrNull()!!
                )
            }
            val service = resultGetService.getOrNull()!!
            var desc = service.descService
            if(item.idComponentItem != 0) {
                val resultGetComponent = componentRepository.getById(item.idComponentItem)
                if (resultGetComponent.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultGetComponent.exceptionOrNull()!!
                    )
                }
                val component = resultGetComponent.getOrNull()!!
                desc = "$desc\n${component.codComponent} - ${component.descComponent}"
            }
            val resultGetCheckList = checkListRepository.getRespByIdItem(item.idItem)
            if (resultGetCheckList.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetCheckList.exceptionOrNull()!!
                )
            }
            val resp = resultGetCheckList.getOrNull()!!
            return Result.success(
                RespScreenModel(
                    pos = item.seqItem,
                    desc = desc,
                    option = resp.option,
                    obs = resp.obs
                )
            )
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}