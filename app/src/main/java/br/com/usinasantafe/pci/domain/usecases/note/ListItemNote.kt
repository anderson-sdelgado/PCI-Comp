package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ServiceRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.presenter.model.ItemScreenModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface ListItemNote {
    suspend operator fun invoke(idPlant: Int): Result<List<ItemScreenModel>>
}

class IListItemNote @Inject constructor(
    private val itemRepository: ItemRepository,
    private val checkListRepository: CheckListRepository,
    private val componentRepository: ComponentRepository,
    private val serviceRepository: ServiceRepository,
): ListItemNote {

    override suspend fun invoke(idPlant: Int): Result<List<ItemScreenModel>> {
        try {
            val resultGetIdOS = checkListRepository.getIdOSHeaderOpen()
            if (resultGetIdOS.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGetIdOS.exceptionOrNull()!!
                )
            }
            val idOS = resultGetIdOS.getOrNull()!!
            val resulListItem = itemRepository.listByIdOSAndIdPlant(
                idOS = idOS,
                idPlant = idPlant
            )
            if (resulListItem.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resulListItem.exceptionOrNull()!!
                )
            }
            val entityList = resulListItem.getOrNull()!!
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
            val idItemList = entityList.map { it.idItem }.distinct()
            val resultRespList = checkListRepository.listByIdItems(
                idItemList = idItemList
            )
            if(resultRespList.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultRespList.exceptionOrNull()!!
                )
            }
            val respList = resultRespList.getOrNull()!!
            val list = entityList.map { item ->
                val descService = serviceList.first { s -> s.idService == item.idServiceItem }.descService
                var descComponent = ""
                if(item.idComponentItem > 0){
                    val component = componentList.first { c -> c.idComponent == item.idComponentItem }
                    descComponent = "${component.codComponent} - ${component.descComponent}"
                }
                val option = respList.firstOrNull { r -> r.idItem == item.idItem }?.option
                ItemScreenModel(
                    id = item.idItem,
                    pos = item.seqItem,
                    descService = descService,
                    descComponent = descComponent,
                    option = option
                )
            }
            val listOrder = list.sortedWith(
                compareBy({ it.option != null }, { it.pos })
            )
            return Result.success(listOrder)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}