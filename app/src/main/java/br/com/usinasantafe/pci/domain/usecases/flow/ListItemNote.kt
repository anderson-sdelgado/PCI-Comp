package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ServiceRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.presenter.model.ItemScreenModel
import br.com.usinasantafe.pci.utils.Status // Assume que Status está em utils
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
                return resultFailure(
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
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resulListItem.exceptionOrNull()!!
                )
            }
            val itemList = resulListItem.getOrNull()!!
            val idComponentList = itemList.map { it.idComponentItem }.distinct().filter { it != 0 }
            val resultComponentList = componentRepository.listByIds(
                ids = idComponentList
            )
            if(resultComponentList.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultComponentList.exceptionOrNull()!!
                )
            }
            val componentList = resultComponentList.getOrNull()!!
            val idServiceList = itemList.map { it.idServiceItem }.distinct()
            val resultServiceList = serviceRepository.listByIds(
                ids = idServiceList
            )
            if(resultServiceList.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultServiceList.exceptionOrNull()!!
                )
            }
            val serviceList = resultServiceList.getOrNull()!!
            val idItemList = itemList.map { it.idItem }.distinct()
            val resultRespList = checkListRepository.listRespByIdItems(
                idItemList = idItemList
            )
            if(resultRespList.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultRespList.exceptionOrNull()!!
                )
            }
            val respList = resultRespList.getOrNull()!!
            val itemOpenList = itemList.filter { item ->
                val resp = respList.firstOrNull { r -> r.idItem == item.idItem }
                resp == null || resp.status != Status.FINISH
            }
            val list = itemOpenList.map { item ->
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
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}