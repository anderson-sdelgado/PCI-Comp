package br.com.usinasantafe.pci.infra.repositories.variable

import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import javax.inject.Inject

class ICheckListRepository @Inject constructor(

): CheckListRepository {

    override suspend fun setIdColabHeader(idColab: Int): Result<Boolean> {
        TODO("Not yet implemented")
    }

}