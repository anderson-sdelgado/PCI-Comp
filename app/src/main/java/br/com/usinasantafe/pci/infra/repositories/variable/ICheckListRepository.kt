package br.com.usinasantafe.pci.infra.repositories.variable

import br.com.usinasantafe.pci.domain.entities.variable.Header
import br.com.usinasantafe.pci.domain.entities.variable.Resp
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.pci.infra.datasource.room.variable.RespRoomDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pci.infra.models.sharedpreferences.sharedPreferencesModelToRoomModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class ICheckListRepository @Inject constructor(
    private val headerSharedPreferencesDatasource: HeaderSharedPreferencesDatasource,
    private val headerRoomDatasource: HeaderRoomDatasource,
    private val respRoomDatasource: RespRoomDatasource
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
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun getIdFactorySectionHeaderOpen(): Result<Int> {
        val result = headerSharedPreferencesDatasource.getIdFactorySection()
        if (result.isFailure)
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun setIdOSHeader(idOS: Int): Result<Boolean> {
        try {
            val resultSetId = headerSharedPreferencesDatasource.setIdOS(idOS)
            if (resultSetId.isFailure)
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSetId.exceptionOrNull()!!
                )
            val resultGet = headerSharedPreferencesDatasource.get()
            if (resultGet.isFailure)
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            val modelSharedPreferences = resultGet.getOrNull()!!
            val resultSave = headerRoomDatasource.save(
                model = modelSharedPreferences.sharedPreferencesModelToRoomModel()
            )
            if (resultSave.isFailure)
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            return resultSave
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdOSHeaderOpen(): Result<Int> {
        val result = headerSharedPreferencesDatasource.getIdOS()
        if (result.isFailure)
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun closeHeaders(): Result<Boolean> {
        val result = headerRoomDatasource.close()
        if (result.isFailure)
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun finishHeader(): Result<Boolean> {
        val result = headerRoomDatasource.finish()
        if (result.isFailure)
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun listHeaderByIdOSList(ids: List<Int>): Result<List<Header>> {
        try {
            val result = headerRoomDatasource.listByIdOSList(ids)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val list = result.getOrNull()!!.map { it.roomModelToEntity() }
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdColabHeaderOpen(): Result<Int> {
        val result = headerSharedPreferencesDatasource.getIdColab()
        if (result.isFailure)
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun saveResp(resp: Resp): Result<Boolean> {
        try {
            val resultGetOpen = headerRoomDatasource.getByStatusOpenDefault()
            if (resultGetOpen.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetOpen.exceptionOrNull()!!
                )
            }
            val headerModel = resultGetOpen.getOrNull()!!
            val resultSaveResp = respRoomDatasource.save(
                RespRoomModel(
                    idHeader = headerModel.id!!,
                    idPlant = resp.idPlant,
                    idItem = resp.idItem,
                    option = resp.option,
                    obs = resp.obs
                )
            )
            if (resultSaveResp.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSaveResp.exceptionOrNull()!!
                )
            }
            return resultSaveResp
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listRespByIdItems(idItemList: List<Int>): Result<List<Resp>> {
        try {
            val result = respRoomDatasource.listByIdItems(idItemList)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val list = result.getOrNull()!!.map { it.roomModelToEntity() }
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getRespByIdItem(idItem: Int): Result<Resp> {
        try {
            val result = respRoomDatasource.getByIdItem(idItem)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entity = result.getOrNull()!!.roomModelToEntity()
            return Result.success(entity)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun finishItems(idPlant: Int): Result<Boolean> {
        try {
            val resultGetOpen = headerRoomDatasource.getByStatusOpenDefault()
            if (resultGetOpen.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetOpen.exceptionOrNull()!!
                )
            }
            val headerModel = resultGetOpen.getOrNull()!!
            val resultClose = respRoomDatasource.finishItems(
                idHeader = headerModel.id!!,
                idPlant = idPlant
            )
            if (resultClose.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultClose.exceptionOrNull()!!
                )
            }
            return resultClose
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listRespByIdPlantAndHeaderOpen(idPlant: Int): Result<List<Resp>> {
        try {
            val resultGetOpen = headerRoomDatasource.getByStatusOpenDefault()
            if (resultGetOpen.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetOpen.exceptionOrNull()!!
                )
            }
            val headerModel = resultGetOpen.getOrNull()!!
            val result = respRoomDatasource.listByIdHeaderAndIdPlant(
                idHeader = headerModel.id!!,
                idPlant = idPlant
            )
            if(result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return Result.success(
                result.getOrNull()!!.map {
                    it.roomModelToEntity()
                }
            )
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listRespByHeaderOpen(): Result<List<Resp>> {
        try {
            val resultGetOpen = headerRoomDatasource.getByStatusOpenDefault()
            if (resultGetOpen.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetOpen.exceptionOrNull()!!
                )
            }
            val headerModel = resultGetOpen.getOrNull()!!
            val resultList = respRoomDatasource.listByIdHeader(headerModel.id!!)
            if (resultList.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultList.exceptionOrNull()!!
                )
            }
            return Result.success(
                resultList.getOrNull()!!.map {
                    it.roomModelToEntity()
                }
            )
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkRespSend(): Result<Boolean> {
        val result = respRoomDatasource.checkRespSend()
        if (result.isFailure)
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun sendNote(
        token: String,
        number: Long
    ): Result<Boolean> {
        try {
            val resultListResp = respRoomDatasource.listRespSend()
            if (resultListResp.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultListResp.exceptionOrNull()!!
                )
            }
            val listResp = resultListResp.getOrNull()!!
            val idHeaderList = listResp.map { it.idHeader }
            val resultListHeader = headerRoomDatasource.listByIds(idHeaderList)
            if (resultListHeader.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultListHeader.exceptionOrNull()!!
                )
            }
            val headerList = resultListHeader.getOrNull()!!
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}