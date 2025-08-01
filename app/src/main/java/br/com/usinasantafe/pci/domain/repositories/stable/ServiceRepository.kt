package br.com.usinasantafe.pci.domain.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Service

interface ServiceRepository {
    suspend fun addAll(list: List<Service>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<Service>>
    suspend fun listByIds(ids: List<Int>): Result<List<Service>>
}