package br.com.usinasantafe.pci.domain.usecases.header

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface SetIdOSHeader {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class ISetIdOSHeader @Inject constructor(
    private val checkListRepository: CheckListRepository
): SetIdOSHeader {

    override suspend fun invoke(id: Int): Result<Boolean> {
        try {
            val result = checkListRepository.setIdOSHeader(id)
            if (result.isFailure)
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            return result
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}