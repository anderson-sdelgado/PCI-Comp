package br.com.usinasantafe.pci.external.room.datasource.variable

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.pci.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.utils.Status
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

class IHeaderRoomDatasource @Inject constructor(
    private val headerDao: HeaderDao
): HeaderRoomDatasource {

    override suspend fun save(model: HeaderRoomModel): Result<Boolean> {
        try {
            headerDao.save(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getByStatus(status: Status): Result<HeaderRoomModel> {
        try {
            val model = headerDao.getByStatus(status)
            return Result.success(model)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}