package br.com.usinasantafe.pci.infra.repositories.variable

import br.com.usinasantafe.pci.domain.entities.variable.Config
import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pci.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.variable.entityToRetrofitModel
import br.com.usinasantafe.pci.infra.models.retrofit.variable.retrofitToEntity
import br.com.usinasantafe.pci.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pci.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pci.utils.FlagUpdate
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject
import kotlin.text.get

class IConfigRepository @Inject constructor(
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource,
    private val configRetrofitDatasource: ConfigRetrofitDatasource
): ConfigRepository {

    override suspend fun hasConfig(): Result<Boolean> {
        val result = configSharedPreferencesDatasource.has()
        if (result.isFailure)
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun getPassword(): Result<String> {
        val result = configSharedPreferencesDatasource.getPassword()
        if (result.isFailure)
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun get(): Result<Config> {
        try {
            val result = configSharedPreferencesDatasource.get()
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.sharedPreferencesModelToEntity())
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun save(entity: Config): Result<Boolean> {
        try {
            val sharedPreferencesModel = entity.entityToSharedPreferencesModel()
            val result = configSharedPreferencesDatasource.save(sharedPreferencesModel)
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun send(entity: Config): Result<Config> {
        try {
            val result = configRetrofitDatasource.recoverToken(
                entity.entityToRetrofitModel()
            )
            if(result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val configRetrofitModel = result.getOrNull()!!
            val entity = configRetrofitModel.retrofitToEntity()
            return Result.success(entity)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean> {
        val result = configSharedPreferencesDatasource.setFlagUpdate(flagUpdate)
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> {
        val result = configSharedPreferencesDatasource.getFlagUpdate()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}