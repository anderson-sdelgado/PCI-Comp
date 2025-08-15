package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface CloseHeaders {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseHeaders @Inject constructor(
    private val checkListRepository: CheckListRepository
): CloseHeaders {

    override suspend fun invoke(): Result<Boolean> {
        val result = checkListRepository.closeHeaders()
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}