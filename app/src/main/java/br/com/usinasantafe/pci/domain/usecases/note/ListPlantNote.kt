package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.PlantRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.presenter.model.PlantScreenModel
import br.com.usinasantafe.pci.utils.Status
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface ListPlantNote {
    suspend operator fun invoke(): Result<List<PlantScreenModel>>
}

class IListPlantNote @Inject constructor(
    private val itemRepository: ItemRepository,
    private val plantRepository: PlantRepository,
    private val checkListRepository: CheckListRepository,
): ListPlantNote {

    override suspend fun invoke(): Result<List<PlantScreenModel>> {
        try {
            val resultItemList = itemRepository.listAll()
            if(resultItemList.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultItemList.exceptionOrNull()!!
                )
            }
            val itemList = resultItemList.getOrNull()!!
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
            val idPlantList = itemOpenList.map { it.idPlantItem }.distinct()
            val resultPlantList = plantRepository.listByIds(
                ids = idPlantList
            )
            if(resultPlantList.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultPlantList.exceptionOrNull()!!
                )
            }
            val plantList = resultPlantList.getOrNull()!!
            val respListOpen = respList.filter { resp -> resp.status != Status.FINISH }
            val itemListResp = respListOpen.map { resp ->
                itemOpenList.first { i -> i.idItem == resp.idItem }
            }
            val idPlantListResp = itemListResp.map { it.idPlantItem }.distinct()
            val list = plantList.map { it ->
                val status = idPlantListResp.contains(it.idPlant)
                PlantScreenModel(
                    id = it.idPlant,
                    cod = it.codPlant,
                    desc = it.descPlant,
                    status = status
                )
            }
            val listOrder = list.sortedWith(
                compareBy( { it.status }, { it.id } )
            )
            return Result.success(listOrder)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}