package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.errors.resultFailure
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
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}