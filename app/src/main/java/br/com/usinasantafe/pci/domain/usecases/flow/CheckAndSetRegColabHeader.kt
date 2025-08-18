package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface CheckAndSetRegColabHeader {
    suspend operator fun invoke(regColab: String): Result<Boolean>
}

class ICheckAndSetRegColabHeader @Inject constructor(
    private val getToken: GetToken,
    private val colabRepository: ColabRepository,
    private val checkListRepository: CheckListRepository
): CheckAndSetRegColabHeader {

    override suspend fun invoke(regColab: String): Result<Boolean> {
        try {
            val resultGetToken = getToken()
            if (resultGetToken.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetToken.exceptionOrNull()!!
                )
            }
            val token = resultGetToken.getOrNull()!!
            val resultGet = colabRepository.getByRegColab(
                token = token,
                regColab = regColab.toInt()
            )
            if (resultGet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val entity = resultGet.getOrNull()!!
            if (entity.idColab == 0) return Result.success(false)
            val resultDeleteAll = colabRepository.deleteAll()
            if (resultDeleteAll.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultDeleteAll.exceptionOrNull()!!
                )
            }
            val resultAdd = colabRepository.add(entity)
            if (resultAdd.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultAdd.exceptionOrNull()!!
                )
            }
            val resultSet = checkListRepository.setIdColabAndIdFactorySectionHeader(
                idColab = entity.idColab,
                idFactorySection = entity.idFactorySectionColab
            )
            if (resultSet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSet.exceptionOrNull()!!
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