package br.com.usinasantafe.pci.domain.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Component

interface ComponentRepository {
    suspend fun addAll(list: List<Component>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<Component>>
    suspend fun listByIds(ids: List<Int>): Result<List<Component>>
}