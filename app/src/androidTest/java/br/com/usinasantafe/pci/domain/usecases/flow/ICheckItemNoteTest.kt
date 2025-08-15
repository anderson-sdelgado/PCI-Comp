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
class ICheckItemNoteTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ICheckItemNote

    @Inject
    lateinit var itemDao: ItemDao

    @Inject
    lateinit var componentDao: ComponentDao

    @Inject
    lateinit var serviceDao: ServiceDao

    private val listItem = listOf(
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
            idServiceItem = 4
        ),
    )

    val componentRoomModelListMinor = listOf(
        ComponentRoomModel(
            idComponent = 1,
            codComponent = "1",
            descComponent = "Component 1"
        ),
        ComponentRoomModel(
            idComponent = 2,
            codComponent = "2",
            descComponent = "Component 2"
        )
    )

    val componentRoomModelList = listOf(
        ComponentRoomModel(
            idComponent = 1,
            codComponent = "1",
            descComponent = "Component 1"
        ),
        ComponentRoomModel(
            idComponent = 2,
            codComponent = "2",
            descComponent = "Component 2"
        ),
        ComponentRoomModel(
            idComponent = 3,
            codComponent = "3",
            descComponent = "Component 3"
        )
    )

    val serviceRoomModelListMinor = listOf(
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
        ServiceRoomModel(
            idService = 3,
            codService = 3,
            descService = "Service 3"
        )
    )

    val serviceRoomModelList = listOf(
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
        ServiceRoomModel(
            idService = 3,
            codService = 3,
            descService = "Service 3"
        ),
        ServiceRoomModel(
            idService = 4,
            codService = 4,
            descService = "Service 4"
        )
    )

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_true_if_not_have_data() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun check_return_false_if_not_have_data_in_table_component() =
        runTest {
            itemDao.insertAll(listItem)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun check_return_false_if_have_data_in_table_component_smaller_qtd() =
        runTest {
            itemDao.insertAll(listItem)
            componentDao.insertAll(componentRoomModelListMinor)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun check_return_false_if_not_have_data_in_table_service() =
        runTest {
            itemDao.insertAll(listItem)
            componentDao.insertAll(componentRoomModelList)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun check_return_false_if_have_data_in_table_service_smaller_qtd() =
        runTest {
            itemDao.insertAll(listItem)
            componentDao.insertAll(componentRoomModelList)
            serviceDao.insertAll(serviceRoomModelListMinor)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun check_return_true_if_process_execute_successfully() =
        runTest {
            itemDao.insertAll(listItem)
            componentDao.insertAll(componentRoomModelList)
            serviceDao.insertAll(serviceRoomModelList)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }
}