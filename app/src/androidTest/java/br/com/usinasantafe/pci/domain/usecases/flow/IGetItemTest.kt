package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.external.room.dao.stable.ComponentDao
import br.com.usinasantafe.pci.external.room.dao.stable.ItemDao
import br.com.usinasantafe.pci.external.room.dao.stable.ServiceDao
import br.com.usinasantafe.pci.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.ItemRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.ServiceRoomModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class IGetItemTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: IGetDescItem

    @Inject
    lateinit var itemDao: ItemDao

    @Inject
    lateinit var componentDao: ComponentDao

    @Inject
    lateinit var serviceDao: ServiceDao

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
                "IGetItem -> IItemRepository.getById"
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
                        idComponentItem = 1,
                        idServiceItem = 1
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
                "IGetItem -> IServiceRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_desc_if_component_is_0() =
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
                    )
                )
            )
            serviceDao.insertAll(
                listOf(
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "Service 1"
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "Service 1"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_table_component() =
        runTest {
            itemDao.insertAll(
                listOf(
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 1
                    )
                )
            )
            serviceDao.insertAll(
                listOf(
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "Service 1"
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
                "IGetItem -> IComponentRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_desc_if_process_execute_successfully() =
        runTest {
            itemDao.insertAll(
                listOf(
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 1
                    )
                )
            )
            serviceDao.insertAll(
                listOf(
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "Service 1"
                    )
                )
            )
            componentDao.insertAll(
                listOf(
                    ComponentRoomModel(
                        idComponent = 1,
                        codComponent = "01",
                        descComponent = "Component 1"
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "Service 1\n01 - Component 1"
            )
        }

}