package br.com.usinasantafe.pci.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pci.infra.models.room.stable.ServiceRoomModel
import br.com.usinasantafe.pci.utils.TB_SERVICE

@Dao
interface ServiceDao {

    @Insert
    fun insertAll(list: List<ServiceRoomModel>)

    @Query("DELETE FROM $TB_SERVICE")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_SERVICE")
    suspend fun all(): List<ServiceRoomModel>

    @Query("SELECT * FROM $TB_SERVICE WHERE idService IN (:ids)")
    suspend fun listByIds(ids: List<Int>): List<ServiceRoomModel>

    @Query("SELECT * FROM $TB_SERVICE WHERE idService = :id")
    suspend fun getById(id: Int): ServiceRoomModel

}