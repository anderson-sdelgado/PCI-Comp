package br.com.usinasantafe.pci.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pci.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pci.utils.TB_COLAB

@Dao
interface ColabDao {

    @Insert
    fun insert(model: ColabRoomModel)

    @Insert
    fun insertAll(list: List<ColabRoomModel>)

    @Query("DELETE FROM $TB_COLAB")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_COLAB")
    suspend fun all(): List<ColabRoomModel>

}