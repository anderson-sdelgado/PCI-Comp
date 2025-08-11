package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface CloseItemsNote {
    suspend operator fun invoke(
        idPlant: Int,
    ): Result<Boolean>
}

class ICloseItemsNote @Inject constructor(
    private val checkListRepository: CheckListRepository,
    private val itemRepository: ItemRepository,
): CloseItemsNote {

    override suspend fun invoke(
        idPlant: Int,
    ): Result<Boolean> {
        try {
            val resultCloseItems = checkListRepository.closeItems(idPlant)
            if (resultCloseItems.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCloseItems.exceptionOrNull()!!
                )
            }
            val resultGetIdOS = checkListRepository.getIdOSHeaderOpen()
            if (resultGetIdOS.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdOS.exceptionOrNull()!!
                )
            }
            val idOS = resultGetIdOS.getOrNull()!!
            val resultItemList = itemRepository.listByIdOSAndIdPlant(
                idOS = idOS,
                idPlant = idPlant
            )
            if (resultItemList.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultItemList.exceptionOrNull()!!
                )
            }
            val itemList = resultItemList.getOrNull()!!
            val resultRespList = checkListRepository.listRespByIdPlantAndHeaderOpen(idPlant)
            if (resultRespList.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultRespList.exceptionOrNull()!!
                )
            }
            val respList = resultRespList.getOrNull()!!
            val check = itemList.all { item ->
                respList.any { resp -> resp.idItem == item.idItem }
            }
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}