package br.com.usinasantafe.pci.domain.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Plant

interface PlantRepository {
    suspend fun addAll(list: List<Plant>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByIdFactorySection(
        token: String,
        idFactorySection: Int
    ): Result<List<Plant>>
    suspend fun listByIdFactorySection(idFactorySection: Int): Result<List<Plant>>
    suspend fun listByIdList(ids: List<Int>): Result<List<Plant>>

}