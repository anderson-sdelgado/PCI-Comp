package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.entities.variable.Resp
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface SetRespItem {
    suspend operator fun invoke(
        id: Int,
        option: OptionResp,
        obs: String? = null
    ): Result<Boolean>
}

class ISetRespItem @Inject constructor(
    private val checkListRepository: CheckListRepository
): SetRespItem {

    override suspend fun invoke(
        id: Int,
        option: OptionResp,
        obs: String?
    ): Result<Boolean> {
        val result = checkListRepository.saveResp(
            Resp(
                idItem = id,
                option = option,
                obs = obs
            )
        )
        if(result.isFailure){
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}