package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.errors.resultFailure
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
): CloseItemsNote {

    override suspend fun invoke(
        idPlant: Int,
    ): Result<Boolean> {
        val result = checkListRepository.closeItems(idPlant)
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}