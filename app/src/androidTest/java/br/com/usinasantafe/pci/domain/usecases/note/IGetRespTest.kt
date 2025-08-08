package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.external.room.dao.stable.ComponentDao
import br.com.usinasantafe.pci.external.room.dao.stable.ItemDao
import br.com.usinasantafe.pci.external.room.dao.stable.ServiceDao
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.ItemRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.ServiceRoomModel
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.presenter.model.RespScreenModel
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
class IGetRespTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: IGetResp

    @Inject
    lateinit var itemDao: ItemDao

    @Inject
    lateinit var componentDao: ComponentDao

    @Inject
    lateinit var serviceDao: ServiceDao

    @Inject
    lateinit var respDao: RespDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_table_item() =
        runTest {
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetResp -> IItemRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_table_service() =
        runTest {
            itemDao.insertAll(
                listOf(
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 0,
                        idServiceItem = 1
                    ),
                    ItemRoomModel(
                        idItem = 2,
                        seqItem = 2,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 2
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetResp -> IServiceRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_table_resp_and_id_component_is_0() =
        runTest {
            itemDao.insertAll(
                listOf(
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 0,
                        idServiceItem = 1
                    ),
                    ItemRoomModel(
                        idItem = 2,
                        seqItem = 2,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 2
                    )
                )
            )
            serviceDao.insertAll(
                listOf(
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "Service 1"
                    ),
                    ServiceRoomModel(
                        idService = 2,
                        codService = 2,
                        descService = "Service 2"
                    ),
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetResp -> ICheckListRepository.getRespByIdItem"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_correct_if_process_execute_successfully_and_id_component_is_0() =
        runTest {
            itemDao.insertAll(
                listOf(
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 0,
                        idServiceItem = 1
                    ),
                    ItemRoomModel(
                        idItem = 2,
                        seqItem = 2,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 2
                    )
                )
            )
            serviceDao.insertAll(
                listOf(
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "Service 1"
                    ),
                    ServiceRoomModel(
                        idService = 2,
                        codService = 2,
                        descService = "Service 2"
                    ),
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 1,
                    idItem = 1,
                    option = OptionResp.ACCORDING,
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                RespScreenModel(
                    pos = 1,
                    desc = "Service 1",
                    option = OptionResp.ACCORDING,
                    obs = null
                )
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_table_component_and_id_component_is_not_0() =
        runTest {
            itemDao.insertAll(
                listOf(
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 0,
                        idServiceItem = 1
                    ),
                    ItemRoomModel(
                        idItem = 2,
                        seqItem = 2,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 2
                    )
                )
            )
            serviceDao.insertAll(
                listOf(
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "Service 1"
                    ),
                    ServiceRoomModel(
                        idService = 2,
                        codService = 2,
                        descService = "Service 2"
                    ),
                )
            )
            val result = usecase(2)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetResp -> IComponentRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_table_resp_and_id_component_is_not_0() =
        runTest {
            itemDao.insertAll(
                listOf(
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 0,
                        idServiceItem = 1
                    ),
                    ItemRoomModel(
                        idItem = 2,
                        seqItem = 2,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 2
                    )
                )
            )
            serviceDao.insertAll(
                listOf(
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "Service 1"
                    ),
                    ServiceRoomModel(
                        idService = 2,
                        codService = 2,
                        descService = "Service 2"
                    ),
                )
            )
            componentDao.insertAll(
                listOf(
                    ComponentRoomModel(
                        idComponent = 1,
                        codComponent = "01",
                        descComponent = "Component 1"
                    ),
                    ComponentRoomModel(
                        idComponent = 2,
                        codComponent = "02",
                        descComponent = "Component 2"
                    ),
                )
            )
            val result = usecase(2)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetResp -> ICheckListRepository.getRespByIdItem"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_correct_if_process_execute_successfully_and_id_component_is_not_0() =
        runTest {
            itemDao.insertAll(
                listOf(
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 0,
                        idServiceItem = 1
                    ),
                    ItemRoomModel(
                        idItem = 2,
                        seqItem = 2,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 2
                    )
                )
            )
            serviceDao.insertAll(
                listOf(
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "Service 1"
                    ),
                    ServiceRoomModel(
                        idService = 2,
                        codService = 2,
                        descService = "Service 2"
                    ),
                )
            )
            componentDao.insertAll(
                listOf(
                    ComponentRoomModel(
                        idComponent = 1,
                        codComponent = "01",
                        descComponent = "Component 1"
                    ),
                    ComponentRoomModel(
                        idComponent = 2,
                        codComponent = "02",
                        descComponent = "Component 2"
                    ),
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 1,
                    idItem = 1,
                    option = OptionResp.ACCORDING,
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 1,
                    idItem = 2,
                    option = OptionResp.NON_CONFORMING,
                    obs = "Obs Test"
                )
            )
            val result = usecase(2)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                RespScreenModel(
                    pos = 2,
                    desc = "Service 2\n01 - Component 1",
                    option = OptionResp.NON_CONFORMING,
                    obs = "Obs Test"
                )
            )
        }
}