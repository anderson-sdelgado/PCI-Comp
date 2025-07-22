package br.com.usinasantafe.pci.domain.usecases.config

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pci.presenter.model.ConfigModel
import br.com.usinasantafe.pci.presenter.model.toConfigModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject
import kotlin.text.get

interface GetConfigInternal {
    suspend operator fun invoke(): Result<ConfigModel?>
}

class IGetConfigInternal @Inject constructor(
    private val configRepository: ConfigRepository
): GetConfigInternal {

    override suspend fun invoke(): Result<ConfigModel?> {
        try {
            val resultHasConfig = configRepository.hasConfig()
            if (resultHasConfig.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultHasConfig.exceptionOrNull()!!
                )
            }
            val hasConfig = resultHasConfig.getOrNull()!!
            if (!hasConfig)
                return Result.success(null)
            val resultConfig = configRepository.get()
            if (resultConfig.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultConfig.exceptionOrNull()!!
                )
            }
            val config = resultConfig.getOrNull()!!.toConfigModel()
            return Result.success(config)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}