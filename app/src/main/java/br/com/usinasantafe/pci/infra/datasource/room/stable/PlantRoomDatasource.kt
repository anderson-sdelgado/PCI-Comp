package br.com.usinasantafe.pci.infra.datasource.room.stable

import br.com.usinasantafe.pci.infra.models.room.stable.PlantRoomModel

interface PlantRoomDatasource {
    suspend fun addAll(list: List<PlantRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByIdFactorySection(idFactorySection: Int): Result<List<PlantRoomModel>>
    suspend fun listByIds(ids: List<Int>): Result<List<PlantRoomModel>>

}