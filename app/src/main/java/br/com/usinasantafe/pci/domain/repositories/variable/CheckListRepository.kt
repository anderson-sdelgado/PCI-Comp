package br.com.usinasantafe.pci.domain.repositories.variable

import br.com.usinasantafe.pci.domain.entities.variable.Header
import br.com.usinasantafe.pci.domain.entities.variable.Resp

interface CheckListRepository {
    suspend fun setIdColabAndIdFactorySectionHeader(
        idColab: Int,
        idFactorySection: Int
    ): Result<Boolean>
    suspend fun getIdFactorySectionHeaderOpen(): Result<Int>
    suspend fun setIdOSHeader(idOS: Int): Result<Boolean>
    suspend fun getIdOSHeaderOpen(): Result<Int>
    suspend fun closeHeaders(): Result<Boolean>
    suspend fun finishHeader(): Result<Boolean>
    suspend fun listHeaderByIdOSList(ids: List<Int>): Result<List<Header>>
    suspend fun getIdColabHeaderOpen(): Result<Int>
    suspend fun saveResp(resp: Resp): Result<Boolean>
    suspend fun listRespByIdItems(idItemList: List<Int>): Result<List<Resp>>
    suspend fun getRespByIdItem(idItem: Int): Result<Resp>
    suspend fun finishItems(idPlant: Int): Result<Boolean>
    suspend fun listRespByIdPlantAndHeaderOpen(idPlant: Int): Result<List<Resp>>
    suspend fun listRespByHeaderOpen(): Result<List<Resp>>
    suspend fun checkRespSend(): Result<Boolean>
    suspend fun sendNote(
        token: String,
        number: Long,
    ): Result<Boolean>
}