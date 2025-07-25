package br.com.usinasantafe.pci.infra.datasource.sharedpreferences

import br.com.usinasantafe.pci.infra.models.sharedpreferences.HeaderSharedPreferencesModel

interface HeaderSharedPreferencesDatasource {
    suspend fun get(): Result<HeaderSharedPreferencesModel>
    suspend fun setIdColabAndIdFactorySection(
        idColab: Int,
        idFactorySection: Int
    ): Result<Boolean>
    suspend fun getIdFactorySection(): Result<Int>

}