package br.com.usinasantafe.pci.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pci.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.pci.utils.TB_COMPONENT

@Dao
interface ComponentDao {

    @Insert
    fun insertAll(list: List<ComponentRoomModel>)

    @Query("DELETE FROM $TB_COMPONENT")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_COMPONENT")
    suspend fun all(): List<ComponentRoomModel>

    @Query("SELECT * FROM $TB_COMPONENT WHERE idComponent IN (:ids)")
    suspend fun listByIds(ids: List<Int>): List<ComponentRoomModel>

}