package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface CheckRespSend {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckRespSend @Inject constructor(
    private val checkListRepository: CheckListRepository
): CheckRespSend {

    override suspend fun invoke(): Result<Boolean> {
        val result = checkListRepository.checkRespSend()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}