package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.PlantRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.presenter.model.PlantScreenModel
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
            val result = itemRepository.listAll()
            if(result.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val itemList = result.getOrNull()!!
            val idPlantList = itemList.map { it.idPlantItem }.distinct()
            val resultPlantList = plantRepository.listByIds(
                ids = idPlantList
            )
            if(resultPlantList.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultPlantList.exceptionOrNull()!!
                )
            }
            val plantList = resultPlantList.getOrNull()!!
            val idItemList = itemList.map { it.idItem }.distinct()
            val resultRespList = checkListRepository.listRespByIdItems(
                idItemList = idItemList
            )
            if(resultRespList.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultRespList.exceptionOrNull()!!
                )
            }
            val respList = resultRespList.getOrNull()!!
            val itemListResp = respList.map { resp ->
                itemList.first { i -> i.idItem == resp.idItem }
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
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}