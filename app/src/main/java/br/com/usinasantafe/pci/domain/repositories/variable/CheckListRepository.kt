package br.com.usinasantafe.pci.domain.repositories.variable

import br.com.usinasantafe.pci.domain.entities.variable.Resp

interface CheckListRepository {
    suspend fun setIdColabAndIdFactorySectionHeader(
        idColab: Int,
        idFactorySection: Int
    ): Result<Boolean>
    suspend fun getIdFactorySectionHeaderOpen(): Result<Int>
    suspend fun setIdOSHeader(idOS: Int): Result<Boolean>
    suspend fun getIdOSHeaderOpen(): Result<Int>
    suspend fun saveResp(resp: Resp): Result<Boolean>
    suspend fun listRespByIdItems(idItemList: List<Int>): Result<List<Resp>>
}