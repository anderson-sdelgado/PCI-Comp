package br.com.usinasantafe.pci.domain.repositories.variable

interface ConfigRepository {
    suspend fun hasConfig(): Result<Boolean>
    suspend fun getPassword(): Result<String>
}