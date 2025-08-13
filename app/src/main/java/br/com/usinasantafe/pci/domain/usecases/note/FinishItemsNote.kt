package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface FinishItemsNote {
    suspend operator fun invoke(
        idPlant: Int,
    ): Result<Boolean>
}

class IFinishItemsNote @Inject constructor(
    private val checkListRepository: CheckListRepository,
): FinishItemsNote {

    override suspend fun invoke(
        idPlant: Int,
    ): Result<Boolean> {
        val result = checkListRepository.finishItems(idPlant)
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}