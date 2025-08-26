package br.com.usinasantafe.pci.domain.usecases.config

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pci.utils.StatusSend
import br.com.usinasantafe.pci.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetStatusSend {
    suspend operator fun invoke(): Flow<Result<StatusSend>>
}

class IGetStatusSend @Inject constructor(
    private val configRepository: ConfigRepository,
    private val checkListRepository: CheckListRepository
): GetStatusSend {

    override suspend fun invoke(): Flow<Result<StatusSend>> = flow {
        try{
            val resultHasConfig = configRepository.hasConfig()
            if (resultHasConfig.isFailure) {
                emit(resultFailure(
                    context = getClassAndMethod(),
                    cause = resultHasConfig.exceptionOrNull()!!
                ))
                return@flow
            }
            val hasConfig = resultHasConfig.getOrNull()!!
            if (!hasConfig) {
                emit(Result.success(StatusSend.STARTED))
                return@flow
            }
            val resultCheck = checkListRepository.checkRespSend()
            if (resultCheck.isFailure) {
                emit(resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheck.exceptionOrNull()!!
                ))
                return@flow
            }
            val statusCheck = resultCheck.getOrNull()!!
            if (!statusCheck) {
                emit(Result.success(StatusSend.SENT))
            } else {
                emit(Result.success(StatusSend.SEND))
            }
        } catch (e: Exception) {
            emit(resultFailure(
                context = getClassAndMethod(),
                cause = e
            ))
        }
    }

}