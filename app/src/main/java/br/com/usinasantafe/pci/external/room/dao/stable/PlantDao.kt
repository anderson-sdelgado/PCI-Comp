package br.com.usinasantafe.pci.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pci.infra.models.room.stable.PlantRoomModel
import br.com.usinasantafe.pci.utils.TB_PLANT

@Dao
interface PlantDao {

    @Insert
    fun insertAll(list: List<PlantRoomModel>)

    @Query("DELETE FROM $TB_PLANT")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_PLANT")
    suspend fun all(): List<PlantRoomModel>
}