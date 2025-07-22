package br.com.usinasantafe.pci.infra.datasource.sharedpreferences

import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel

interface ConfigSharedPreferencesDatasource {
    suspend fun get(): Result<ConfigSharedPreferencesModel>
    suspend fun has(): Result<Boolean>
    suspend fun getPassword(): Result<String>
    suspend fun save(model: ConfigSharedPreferencesModel): Result<Boolean>
}