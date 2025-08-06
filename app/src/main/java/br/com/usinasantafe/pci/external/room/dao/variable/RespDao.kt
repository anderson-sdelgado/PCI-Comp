package br.com.usinasantafe.pci.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel

@Dao
interface RespDao {

    @Insert
    suspend fun save(model: RespRoomModel): Long

    @Query("SELECT * FROM TB_RESP")
    suspend fun all(): List<RespRoomModel>

    @Query("SELECT * FROM TB_RESP WHERE idItem IN (:idItems)")
    suspend fun listByIdItems(idItems: List<Int>): List<RespRoomModel>

}