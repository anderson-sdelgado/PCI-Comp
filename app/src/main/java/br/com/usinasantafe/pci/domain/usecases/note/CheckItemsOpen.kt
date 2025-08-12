package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.StatusPlant
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface CheckItemsOpen {
    suspend operator fun invoke(
        idPlant: Int,
    ): Result<StatusPlant>
}

class ICheckItemsOpen @Inject constructor(
    private val checkListRepository: CheckListRepository,
    private val itemRepository: ItemRepository,
): CheckItemsOpen {

    override suspend fun invoke(
        idPlant: Int,
    ): Result<StatusPlant> {
        try {
            val resultGetIdOS = checkListRepository.getIdOSHeaderOpen()
            if (resultGetIdOS.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdOS.exceptionOrNull()!!
                )
            }
            val idOS = resultGetIdOS.getOrNull()!!
            val resultItemPlantList = itemRepository.listByIdOSAndIdPlant(
                idOS = idOS,
                idPlant = idPlant
            )
            if (resultItemPlantList.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultItemPlantList.exceptionOrNull()!!
                )
            }
            val itemPlantList = resultItemPlantList.getOrNull()!!
            val resultRespPlantList = checkListRepository.listRespByIdPlantAndHeaderOpen(idPlant)
            if (resultRespPlantList.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultRespPlantList.exceptionOrNull()!!
                )
            }
            val respPlantList = resultRespPlantList.getOrNull()!!
            val checkPlant = itemPlantList.all { item ->
                respPlantList.any { resp -> resp.idItem == item.idItem }
            }
            if (!checkPlant) return Result.success(StatusPlant.OPEN)
            val resultItemList = itemRepository.listByIdOS(idOS)
            if(resultItemList.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultItemList.exceptionOrNull()!!
                )
            }
            val itemList = resultItemList.getOrNull()!!
            val resultRespList = checkListRepository.listRespByHeaderOpen()
            if(resultRespList.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultRespList.exceptionOrNull()!!
                )
            }
            val respList = resultRespList.getOrNull()!!
            val check = itemList.all { item ->
                respList.any { resp -> resp.idItem == item.idItem }
            }
            if (!check) return Result.success(StatusPlant.CLOSE)
            val result = checkListRepository.finishHeader()
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return Result.success(StatusPlant.CLOSE_ALL)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}