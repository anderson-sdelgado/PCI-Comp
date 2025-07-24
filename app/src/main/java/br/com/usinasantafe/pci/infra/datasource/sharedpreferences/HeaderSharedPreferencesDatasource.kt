package br.com.usinasantafe.pci.infra.datasource.sharedpreferences

interface HeaderSharedPreferencesDatasource {
    suspend fun setIdColab(idColab: Int): Result<Boolean>
}