package br.com.usinasantafe.pci.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.utils.Status

@Dao
interface HeaderDao {

    @Insert
    suspend fun insert(model: HeaderRoomModel): Long

    @Update
    suspend fun update(model: HeaderRoomModel): Int

    @Query("SELECT * FROM TB_HEADER")
    suspend fun all(): List<HeaderRoomModel>

    @Query("SELECT * FROM TB_HEADER WHERE status = :status")
    suspend fun getByStatus(status: Status): HeaderRoomModel

    @Query("SELECT * FROM TB_HEADER WHERE idOS IN (:ids)")
    suspend fun listByIdOSList(ids: List<Int>): List<HeaderRoomModel>

    @Query("SELECT * FROM TB_HEADER WHERE status = :status")
    suspend fun listByStatus(status: Status): List<HeaderRoomModel>

    @Query("SELECT count(*) FROM TB_HEADER WHERE status = :status")
    suspend fun checkByStatus(status: Status): Int

    @Query("SELECT * FROM TB_HEADER WHERE idColab = :idColab AND idOS = :idOS")
    suspend fun listByIdColabAndIdOS(idColab: Int, idOS: Int): List<HeaderRoomModel>

    @Query("SELECT * FROM TB_HEADER WHERE id IN (:ids)")
    suspend fun listByIds(ids: List<Int>): List<HeaderRoomModel>

    @Query("SELECT * FROM TB_HEADER WHERE id = :id")
    suspend fun getById(id: Int): HeaderRoomModel

    @Query("DELETE FROM TB_HEADER WHERE id = :id")
    suspend fun delete(id: Int): Int

}