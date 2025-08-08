package br.com.usinasantafe.pci.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel

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

}