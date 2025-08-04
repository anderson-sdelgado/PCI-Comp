package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.utils.OptionResp
import javax.inject.Inject

interface SetRespItem {
    suspend operator fun invoke(
        id: Int,
        option: OptionResp
    ): Result<Boolean>
}

class ISetRespItem @Inject constructor(
): SetRespItem {

    override suspend fun invoke(
        id: Int,
        option: OptionResp
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}