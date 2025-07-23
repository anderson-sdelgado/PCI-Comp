package br.com.usinasantafe.pci.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.utils.BASE_SHARE_PREFERENCES_TABLE_CONFIG
import br.com.usinasantafe.pci.utils.FlagUpdate
import br.com.usinasantafe.pci.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject


class IConfigSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ConfigSharedPreferencesDatasource {

    override suspend fun get(): Result<ConfigSharedPreferencesModel> {
        try {
            val config = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                null
            )
            if(config.isNullOrEmpty())
                return Result.success(
                    ConfigSharedPreferencesModel()
                )
            return Result.success(
                Gson().fromJson(
                    config,
                    ConfigSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun has(): Result<Boolean> {
        try {
            val result = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                null
            )
            return Result.success(result != null)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getPassword(): Result<String> {
        try {
            val result = get()
            if(result.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.password!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun save(model: ConfigSharedPreferencesModel): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_CONFIG,
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

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean> {
        try {
            val resultConfig = get()
            if (resultConfig.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultConfig.exceptionOrNull()!!
                )
            }
            val config = resultConfig.getOrNull()!!
            config.flagUpdate = flagUpdate
            val resultSave = save(config)
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

    override suspend fun getFlagUpdate(): Result<FlagUpdate> {
        try {
            val result = get()
            if(result.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.flagUpdate)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}