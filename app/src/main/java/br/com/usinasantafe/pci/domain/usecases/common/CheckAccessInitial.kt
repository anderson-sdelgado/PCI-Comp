package br.com.usinasantafe.pci.domain.usecases.common

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pci.utils.FlagUpdate
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface CheckAccessInitial {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckAccessInitial @Inject constructor(
    private val configRepository: ConfigRepository
): CheckAccessInitial {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultCheckHasConfig = configRepository.hasConfig()
            if (resultCheckHasConfig.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultCheckHasConfig.exceptionOrNull()!!
                )
            }
            val hasConfig = resultCheckHasConfig.getOrNull()!!
            if (!hasConfig)
                return Result.success(false)
            val resultGetFlagUpdate = configRepository.getFlagUpdate()
            if (resultGetFlagUpdate.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGetFlagUpdate.exceptionOrNull()!!
                )
            }
            val flagUpdate = resultGetFlagUpdate.getOrNull()!!
            val check = flagUpdate == FlagUpdate.UPDATED
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}