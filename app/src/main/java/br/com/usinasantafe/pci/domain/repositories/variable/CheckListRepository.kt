package br.com.usinasantafe.pci.domain.repositories.variable

interface CheckListRepository {
    suspend fun setIdColabHeader(idColab: Int): Result<Boolean>
}