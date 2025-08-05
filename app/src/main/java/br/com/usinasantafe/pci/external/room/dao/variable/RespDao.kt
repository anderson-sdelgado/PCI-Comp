package br.com.usinasantafe.pci.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel

@Dao
interface RespDao {

    @Insert
    suspend fun save(model: RespRoomModel): Long

}