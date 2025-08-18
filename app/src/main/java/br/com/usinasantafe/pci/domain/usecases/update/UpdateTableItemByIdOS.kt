package br.com.usinasantafe.pci.domain.usecases.update

import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.domain.usecases.flow.GetToken
import br.com.usinasantafe.pci.presenter.model.ResultUpdateModel
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import br.com.usinasantafe.pci.utils.TB_ITEM
import br.com.usinasantafe.pci.utils.getClassAndMethod
import br.com.usinasantafe.pci.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableItemByIdOS {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableItemByIdOS @Inject constructor(
    private val getToken: GetToken,
    private val checkListRepository: CheckListRepository,
    private val itemRepository: ItemRepository
): UpdateTableItemByIdOS {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(1f, count, sizeAll),
                tableUpdate = TB_ITEM,
                levelUpdate = LevelUpdate.RECOVERY
            )
        )
        val resultGetToken = getToken()
        if (resultGetToken.isFailure) {
            val error = resultGetToken.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
            return@flow
        }
        val token = resultGetToken.getOrNull()!!
        val resultGetIdOS = checkListRepository.getIdOSHeaderOpen()
        if (resultGetIdOS.isFailure) {
            val error = resultGetIdOS.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
            return@flow
        }
        val idOS = resultGetIdOS.getOrNull()!!
        val resultList = itemRepository.listByIdOS(
            token = token,
            idOS = idOS
        )
        if (resultList.isFailure) {
            val error = resultList.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
            return@flow
        }
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(2f, count, sizeAll),
                tableUpdate = TB_ITEM,
                levelUpdate = LevelUpdate.CLEAN
            )
        )
        val resultDeleteAll = itemRepository.deleteAll()
        if (resultDeleteAll.isFailure) {
            val error = resultDeleteAll.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
            return@flow
        }
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(3f, count, sizeAll),
                tableUpdate = TB_ITEM,
                levelUpdate = LevelUpdate.SAVE
            )
        )
        val entityList = resultList.getOrNull()!!
        val resultAddAll = itemRepository.addAll(entityList)
        if (resultAddAll.isFailure) {
            val error = resultAddAll.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
            return@flow
        }
    }

}