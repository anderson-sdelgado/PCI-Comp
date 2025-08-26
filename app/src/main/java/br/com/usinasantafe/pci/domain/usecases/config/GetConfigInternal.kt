package br.com.usinasantafe.pci.domain.usecases.config

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pci.presenter.model.ConfigScreenModel
import br.com.usinasantafe.pci.presenter.model.toConfigModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface GetConfigInternal {
    suspend operator fun invoke(): Result<ConfigScreenModel?>
}

class IGetConfigInternal @Inject constructor(
    private val configRepository: ConfigRepository
): GetConfigInternal {

    override suspend fun invoke(): Result<ConfigScreenModel?> {
        try {
            val resultHasConfig = configRepository.hasConfig()
            if (resultHasConfig.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultHasConfig.exceptionOrNull()!!
                )
            }
            val hasConfig = resultHasConfig.getOrNull()!!
            if (!hasConfig) return Result.success(null)
            val resultConfig = configRepository.get()
            if (resultConfig.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultConfig.exceptionOrNull()!!
                )
            }
            val config = resultConfig.getOrNull()!!.toConfigModel()
            return Result.success(config)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}