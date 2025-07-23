package br.com.usinasantafe.pci.domain.repositories.stable

import br.com.usinasantafe.pci.domain.entities.stable.Colab

interface ColabRepository {
    suspend fun addAll(list: List<Colab>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<Colab>>
    suspend fun getByRegColab(regColab: Int): Result<Colab>
}