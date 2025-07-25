package br.com.usinasantafe.pci.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pci.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.pci.utils.TB_OS

@Dao
interface OSDao {

    @Insert
    fun insertAll(list: List<OSRoomModel>)

    @Query("DELETE FROM $TB_OS")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_OS")
    suspend fun all(): List<OSRoomModel>
}