package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.domain.usecases.background.StartWorkManager
import br.com.usinasantafe.pci.utils.Status
import br.com.usinasantafe.pci.utils.dateToDeleteMonth
import br.com.usinasantafe.pci.utils.dateToDeleteYear
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface DeleteNote {
    suspend operator fun invoke(): Result<Boolean>
}

class IDeleteNote @Inject constructor(
    private val checkListRepository: CheckListRepository,
    private val startWorkManager: StartWorkManager
): DeleteNote {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultList = checkListRepository.allHeader()
            if (resultList.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultList.exceptionOrNull()!!
                )
            }
            val list = resultList.getOrNull()!!
            for(header in list) {
                if(header.dateHour < dateToDeleteYear()){
                    val resultDeleteResp = checkListRepository.deleteRespByIdHeader(header.id!!)
                    if (resultDeleteResp.isFailure) {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultDeleteResp.exceptionOrNull()!!
                        )
                    }
                    val resultDeleteHeader = checkListRepository.deleteHeader(header.id)
                    if (resultDeleteHeader.isFailure) {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultDeleteHeader.exceptionOrNull()!!
                        )
                    }
                    continue
                }
                if((header.dateHour < dateToDeleteMonth()) && (header.status == Status.FINISH)) {
                    val resultDeleteResp = checkListRepository.deleteRespByIdHeader(header.id!!)
                    if (resultDeleteResp.isFailure) {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultDeleteResp.exceptionOrNull()!!
                        )
                    }
                    val resultDeleteHeader = checkListRepository.deleteHeader(header.id)
                    if (resultDeleteHeader.isFailure) {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultDeleteHeader.exceptionOrNull()!!
                        )
                    }
                    continue
                }
            }
            startWorkManager()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}