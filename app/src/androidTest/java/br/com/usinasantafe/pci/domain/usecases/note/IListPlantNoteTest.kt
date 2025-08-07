package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.external.room.dao.stable.ItemDao
import br.com.usinasantafe.pci.external.room.dao.stable.PlantDao
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.models.room.stable.ItemRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.PlantRoomModel
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.presenter.model.PlantScreenModel
import br.com.usinasantafe.pci.utils.OptionResp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class IListPlantNoteTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: IListPlantNote

    @Inject
    lateinit var itemDao: ItemDao

    @Inject
    lateinit var plantDao: PlantDao

    @Inject
    lateinit var respDao: RespDao

    private val itemRoomModelList = listOf(
        ItemRoomModel(
            idItem = 1,
            seqItem = 1,
            idOSItem = 1,
            idPlantItem = 1,
            idComponentItem = 1,
            idServiceItem = 1
        ),
        ItemRoomModel(
            idItem = 2,
            seqItem = 2,
            idOSItem = 1,
            idPlantItem = 1,
            idComponentItem = 1,
            idServiceItem = 1
        ),
        ItemRoomModel(
            idItem = 3,
            seqItem = 3,
            idOSItem = 1,
            idPlantItem = 2,
            idComponentItem = 2,
            idServiceItem = 2
        ),
        ItemRoomModel(
            idItem = 4,
            seqItem = 4,
            idOSItem = 1,
            idPlantItem = 3,
            idComponentItem = 3,
            idServiceItem = 3
        ),
        ItemRoomModel(
            idItem = 5,
            seqItem = 5,
            idOSItem = 1,
            idPlantItem = 3,
            idComponentItem = 3,
            idServiceItem = 3
        ),
    )

    private val plantRoomModelList = listOf(
        PlantRoomModel(
            idPlant = 1,
            codPlant = "01",
            descPlant = "Plant 1",
            idFactorySectionPlant = 1
        ),
        PlantRoomModel(
            idPlant = 2,
            codPlant = "02",
            descPlant = "Plant 2",
            idFactorySectionPlant = 1
        ),
        PlantRoomModel(
            idPlant = 3,
            codPlant = "03",
            descPlant = "Plant 3",
            idFactorySectionPlant = 1
        ),
        PlantRoomModel(
            idPlant = 4,
            codPlant = "04",
            descPlant = "Plant 4",
            idFactorySectionPlant = 1
        )
    )

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_empty_list_if_not_have_data_in_table_item_room() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList<PlantScreenModel>()
            )
        }

    @Test
    fun check_return_empty_list_if_not_have_data_in_table_plant_room() =
        runTest {
            itemDao.insertAll(itemRoomModelList)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList<PlantScreenModel>()
            )
        }

    @Test
    fun check_return_success_if_process_execute_and_resp_is_empty() =
        runTest {
            itemDao.insertAll(itemRoomModelList)
            plantDao.insertAll(plantRoomModelList)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    PlantScreenModel(
                        id = 1,
                        cod = "01",
                        desc = "Plant 1",
                        status = false
                    ),
                    PlantScreenModel(
                        id = 2,
                        cod = "02",
                        desc = "Plant 2",
                        status = false
                    ),
                    PlantScreenModel(
                        id = 3,
                        cod = "03",
                        desc = "Plant 3",
                        status = false
                    )
                )
            )
        }

    @Test
    fun check_return_success_if_process_execute() =
        runTest {
            itemDao.insertAll(itemRoomModelList)
            plantDao.insertAll(plantRoomModelList)
            respDao.save(
                RespRoomModel(
                    idHeader = 1,
                    idItem = 2,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs"
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    PlantScreenModel(
                        id = 2,
                        cod = "02",
                        desc = "Plant 2",
                        status = false
                    ),
                    PlantScreenModel(
                        id = 3,
                        cod = "03",
                        desc = "Plant 3",
                        status = false
                    ),
                    PlantScreenModel(
                        id = 1,
                        cod = "01",
                        desc = "Plant 1",
                        status = true
                    ),
                )
            )
        }

}