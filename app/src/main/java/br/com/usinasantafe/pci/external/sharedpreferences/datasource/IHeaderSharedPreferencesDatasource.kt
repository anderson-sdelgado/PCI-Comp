package br.com.usinasantafe.pci.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.sharedpreferences.HeaderSharedPreferencesModel
import br.com.usinasantafe.pci.utils.BASE_SHARE_PREFERENCES_TABLE_HEADER
import br.com.usinasantafe.pci.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject

class IHeaderSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): HeaderSharedPreferencesDatasource {

    fun get(): Result<HeaderSharedPreferencesModel> {
        try {
            val config = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_HEADER,
                null
            )
            if(config.isNullOrEmpty())
                return Result.success(
                    HeaderSharedPreferencesModel()
                )
            return Result.success(
                Gson().fromJson(
                    config,
                    HeaderSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    fun save(model: HeaderSharedPreferencesModel): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_HEADER,
                    Gson().toJson(model)
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setIdColab(idColab: Int): Result<Boolean> {
        try {
            val resultConfig = get()
            if (resultConfig.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultConfig.exceptionOrNull()!!
                )
            }
            val model = resultConfig.getOrNull()!!
            model.idColab = idColab
            val resultSave = save(model)
            if (resultSave.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}