package br.com.usinasantafe.pci.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.utils.Status
import br.com.usinasantafe.pci.utils.StatusSend

@Dao
interface HeaderDao {

    @Insert
    suspend fun save(model: HeaderRoomModel): Long

    @Query("SELECT * FROM TB_HEADER")
    suspend fun all(): List<HeaderRoomModel>

    @Query("SELECT * FROM TB_HEADER WHERE statusSend = :statusSend")
    suspend fun listByStatusSend(statusSend: StatusSend): List<HeaderRoomModel>

    @Query("SELECT * FROM TB_HEADER WHERE status = :status")
    suspend fun getByStatus(status: Status): HeaderRoomModel

}