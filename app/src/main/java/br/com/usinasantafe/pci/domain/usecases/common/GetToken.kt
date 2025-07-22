package br.com.usinasantafe.pci.domain.usecases.common

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pci.utils.getClassAndMethod
import br.com.usinasantafe.pci.utils.token
import javax.inject.Inject

interface GetToken {
    suspend operator fun invoke(): Result<String>
}

class IGetToken @Inject constructor(
    private val configRepository: ConfigRepository
): GetToken {

    override suspend fun invoke(): Result<String> {
        try {
            val resultGet = configRepository.get()
            if (resultGet.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val entity = resultGet.getOrNull()!!
            val token = token(
                idBD = entity.idBD!!,
                number = entity.number!!,
                version = entity.version!!
            )
            return Result.success(token)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}