package br.com.usinasantafe.pci.infra.repositories.variable

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.sharedpreferences.sharedPreferencesModelToRoomModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class ICheckListRepository @Inject constructor(
    private val headerSharedPreferencesDatasource: HeaderSharedPreferencesDatasource,
    private val headerRoomDatasource: HeaderRoomDatasource
): CheckListRepository {

    override suspend fun setIdColabAndIdFactorySectionHeader(
        idColab: Int,
        idFactorySection: Int
    ): Result<Boolean> {
        val result = headerSharedPreferencesDatasource.setIdColabAndIdFactorySection(
            idColab = idColab,
            idFactorySection = idFactorySection
        )
        if (result.isFailure)
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun getIdFactorySectionHeaderOpen(): Result<Int> {
        val result = headerSharedPreferencesDatasource.getIdFactorySection()
        if (result.isFailure)
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun setIdOSHeader(idOS: Int): Result<Boolean> {
        try {
            val resultSetId = headerSharedPreferencesDatasource.setIdOS(idOS)
            if (resultSetId.isFailure)
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSetId.exceptionOrNull()!!
                )
            val resultGet = headerSharedPreferencesDatasource.get()
            if (resultGet.isFailure)
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            val modelSharedPreferences = resultGet.getOrNull()!!
            val resultSave = headerRoomDatasource.save(
                model = modelSharedPreferences.sharedPreferencesModelToRoomModel()
            )
            if (resultSave.isFailure)
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            return resultSave
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}