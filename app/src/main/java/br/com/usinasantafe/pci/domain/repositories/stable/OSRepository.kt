package br.com.usinasantafe.pci.domain.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.OS

interface OSRepository {
    suspend fun addAll(list: List<OS>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByIdFactorySection(
        token: String,
        idFactorySection: Int
    ): Result<List<OS>>
}