package br.com.usinasantafe.pci.domain.repositories.variable

interface CheckListRepository {
    suspend fun setIdColabAndIdFactorySectionHeader(
        idColab: Int,
        idFactorySection: Int
    ): Result<Boolean>
    suspend fun getIdFactorySectionHeaderOpen(): Result<Int>
}