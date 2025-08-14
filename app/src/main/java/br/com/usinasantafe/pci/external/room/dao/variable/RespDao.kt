package br.com.usinasantafe.pci.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.utils.Status
import br.com.usinasantafe.pci.utils.StatusSend

@Dao
interface RespDao {

    @Insert
    suspend fun insert(model: RespRoomModel): Long

    @Update
    suspend fun update(model: RespRoomModel): Int

    @Query("SELECT * FROM TB_RESP")
    suspend fun all(): List<RespRoomModel>

    @Query("SELECT * FROM TB_RESP WHERE idItem IN (:idItems)")
    suspend fun listByIdItems(idItems: List<Int>): List<RespRoomModel>

    @Query("SELECT * FROM TB_RESP WHERE idItem = :idItem")
    suspend fun getByIdItem(idItem: Int): RespRoomModel

    @Query("SELECT count(*) FROM TB_RESP WHERE idItem = :idItem")
    suspend fun countByIdItem(idItem: Int): Int

    @Query("UPDATE TB_RESP SET status = :status WHERE idHeader = :idHeader AND idPlant = :idPlant")
    suspend fun finishItems(
        idHeader: Int,
        idPlant: Int,
        status: Status = Status.FINISH
    ): Int

    @Query("SELECT * FROM TB_RESP WHERE idHeader = :idHeader AND idPlant = :idPlant")
    suspend fun listByIdHeaderAndIdPlant(
        idHeader: Int,
        idPlant: Int
    ): List<RespRoomModel>

    @Query("SELECT * FROM TB_RESP WHERE idHeader = :idHeader")
    suspend fun listByIdHeader(
        idHeader: Int
    ): List<RespRoomModel>

    @Query("SELECT count(*) FROM TB_RESP WHERE statusSend = :statusSend")
    suspend fun countRespSend(
        statusSend: StatusSend = StatusSend.SEND
    ): Int

}