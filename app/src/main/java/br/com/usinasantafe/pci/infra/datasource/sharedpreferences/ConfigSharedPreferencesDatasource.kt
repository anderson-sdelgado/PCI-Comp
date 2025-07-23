package br.com.usinasantafe.pci.infra.datasource.sharedpreferences

import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.utils.FlagUpdate

interface ConfigSharedPreferencesDatasource {
    suspend fun get(): Result<ConfigSharedPreferencesModel>
    suspend fun has(): Result<Boolean>
    suspend fun getPassword(): Result<String>
    suspend fun save(model: ConfigSharedPreferencesModel): Result<Boolean>
    suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean>
    suspend fun getFlagUpdate(): Result<FlagUpdate>
}