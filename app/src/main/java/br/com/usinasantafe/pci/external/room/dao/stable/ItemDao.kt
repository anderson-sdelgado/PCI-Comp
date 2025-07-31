package br.com.usinasantafe.pci.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pci.infra.models.room.stable.ItemRoomModel
import br.com.usinasantafe.pci.utils.TB_ITEM

@Dao
interface ItemDao {

    @Insert
    fun insertAll(list: List<ItemRoomModel>)

    @Query("DELETE FROM $TB_ITEM")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_ITEM")
    suspend fun all(): List<ItemRoomModel>

}