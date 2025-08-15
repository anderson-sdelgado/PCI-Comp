package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.entities.variable.Resp
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface SetRespItem {
    suspend operator fun invoke(
        id: Int,
        idPlant: Int,
        option: OptionResp,
        obs: String? = null
    ): Result<Boolean>
}

class ISetRespItem @Inject constructor(
    private val checkListRepository: CheckListRepository
): SetRespItem {

    override suspend fun invoke(
        id: Int,
        idPlant: Int,
        option: OptionResp,
        obs: String?
    ): Result<Boolean> {
        val result = checkListRepository.saveResp(
            Resp(
                idItem = id,
                idPlant = idPlant,
                option = option,
                obs = obs
            )
        )
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}