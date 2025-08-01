package br.com.usinasantafe.pci.domain.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Item

interface ItemRepository {
    suspend fun addAll(list: List<Item>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByIdOS(
        token: String,
        idOS: Int
    ): Result<List<Item>>
    suspend fun listAll(): Result<List<Item>>
    suspend fun listByIdOSAndIdPlant(
        idOS: Int,
        idPlant: Int
    ): Result<List<Item>>
}