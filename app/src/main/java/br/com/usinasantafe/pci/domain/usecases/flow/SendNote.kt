package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pci.domain.usecases.config.GetToken
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface SendNote {
    suspend operator fun invoke(): Result<Boolean>
}

class ISendNote @Inject constructor(
    private val getToken: GetToken,
    private val configRepository: ConfigRepository,
    private val checkListRepository: CheckListRepository
): SendNote {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetToken = getToken()
            if (resultGetToken.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetToken.exceptionOrNull()!!
                )
            }
            val token = resultGetToken.getOrNull()!!
            val resultGetConfig = configRepository.get()
            if (resultGetConfig.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetConfig.exceptionOrNull()!!
                )
            }
            val config = resultGetConfig.getOrNull()!!
            val resultSend = checkListRepository.sendNote(
                token = token,
                number = config.number!!
            )
            if (resultSend.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSend.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}