package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.external.room.dao.stable.ComponentDao
import br.com.usinasantafe.pci.external.room.dao.stable.ItemDao
import br.com.usinasantafe.pci.external.room.dao.stable.ServiceDao
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.external.sharedpreferences.datasource.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.ItemRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.ServiceRoomModel
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.infra.models.sharedpreferences.HeaderSharedPreferencesModel
import br.com.usinasantafe.pci.presenter.model.ItemScreenModel
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
class IListItemNoteTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: IListItemNote

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderSharedPreferencesDatasource

    @Inject
    lateinit var itemDao: ItemDao

    @Inject
    lateinit var componentDao: ComponentDao

    @Inject
    lateinit var serviceDao: ServiceDao

    @Inject
    lateinit var respDao: RespDao

    private val itemRoomList = listOf(
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
            idPlantItem = 1,
            idComponentItem = 2,
            idServiceItem = 2
        ),
        ItemRoomModel(
            idItem = 4,
            seqItem = 4,
            idOSItem = 1,
            idPlantItem = 1,
            idComponentItem = 3,
            idServiceItem = 3
        ),
        ItemRoomModel(
            idItem = 5,
            seqItem = 5,
            idOSItem = 1,
            idPlantItem = 1,
            idComponentItem = 3,
            idServiceItem = 4
        ),
        ItemRoomModel(
            idItem = 6,
            seqItem = 6,
            idOSItem = 2,
            idPlantItem = 1,
            idComponentItem = 3,
            idServiceItem = 4
        ),
        ItemRoomModel(
            idItem = 7,
            seqItem = 7,
            idOSItem = 1,
            idPlantItem = 2,
            idComponentItem = 3,
            idServiceItem = 4
        ),
    )

    val componentRoomModelList = listOf(
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
        ComponentRoomModel(
            idComponent = 3,
            codComponent = "03",
            descComponent = "Component 3"
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
    fun check_return_failure_if_not_have_data_header_in_shared_preferences() =
        runTest {
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemNote -> ICheckListRepository.getIdOSHeaderOpen -> IHeaderSharedPreferencesDatasource.getIdOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_empty_list_if_not_have_data_in_table_item_room() =
        runTest {
            headerSharedPreferencesDatasource.save(
                HeaderSharedPreferencesModel(
                    idOS = 1
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList<ItemScreenModel>()
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_table_component_and_service_room() =
        runTest {
            headerSharedPreferencesDatasource.save(
                HeaderSharedPreferencesModel(
                    idOS = 1
                )
            )
            itemDao.insertAll(itemRoomList)
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.util.NoSuchElementException: Collection contains no element matching the predicate."
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_table_service_room() =
        runTest {
            headerSharedPreferencesDatasource.save(
                HeaderSharedPreferencesModel(
                    idOS = 1
                )
            )
            itemDao.insertAll(itemRoomList)
            componentDao.insertAll(componentRoomModelList)
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.util.NoSuchElementException: Collection contains no element matching the predicate."
            )
        }

    @Test
    fun check_return_list_if_process_execute_successfully() =
        runTest {
            headerSharedPreferencesDatasource.save(
                HeaderSharedPreferencesModel(
                    idOS = 1
                )
            )
            itemDao.insertAll(itemRoomList)
            componentDao.insertAll(componentRoomModelList)
            serviceDao.insertAll(serviceRoomModelList)
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                5
            )
            val item1 = list[0]
            assertEquals(
                item1,
                ItemScreenModel(
                    id = 1,
                    pos = 1,
                    descService = "Service 1",
                    descComponent = "",
                    option = null
                )
            )
            val item2 = list[1]
            assertEquals(
                item2,
                ItemScreenModel(
                    id = 2,
                    pos = 2,
                    descService = "Service 1",
                    descComponent = "01 - Component 1",
                    option = null
                )
            )
            val item3 = list[2]
            assertEquals(
                item3,
                ItemScreenModel(
                    id = 3,
                    pos = 3,
                    descService = "Service 2",
                    descComponent = "02 - Component 2",
                    option = null
                )
            )
            val item4 = list[3]
            assertEquals(
                item4,
                ItemScreenModel(
                    id = 4,
                    pos = 4,
                    descService = "Service 3",
                    descComponent = "03 - Component 3",
                    option = null
                )
            )
            val item5 = list[4]
            assertEquals(
                item5,
                ItemScreenModel(
                    id = 5,
                    pos = 5,
                    descService = "Service 4",
                    descComponent = "03 - Component 3",
                    option = null
                )
            )
        }

    @Test
    fun check_return_list_if_process_execute_successfully_with_resp() =
        runTest {
            headerSharedPreferencesDatasource.save(
                HeaderSharedPreferencesModel(
                    idOS = 1
                )
            )
            itemDao.insertAll(itemRoomList)
            componentDao.insertAll(componentRoomModelList)
            serviceDao.insertAll(serviceRoomModelList)
            respDao.insert(
                RespRoomModel(
                    idHeader = 1,
                    idItem = 1,
                    option = OptionResp.ACCORDING
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 2,
                    idItem = 3,
                    option = OptionResp.NON_CONFORMING,
                    obs = "OBS"
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                5
            )
            val item2 = list[0]
            assertEquals(
                item2,
                ItemScreenModel(
                    id = 2,
                    pos = 2,
                    descService = "Service 1",
                    descComponent = "01 - Component 1",
                    option = null
                )
            )
            val item4 = list[1]
            assertEquals(
                item4,
                ItemScreenModel(
                    id = 4,
                    pos = 4,
                    descService = "Service 3",
                    descComponent = "03 - Component 3",
                    option = null
                )
            )
            val item5 = list[2]
            assertEquals(
                item5,
                ItemScreenModel(
                    id = 5,
                    pos = 5,
                    descService = "Service 4",
                    descComponent = "03 - Component 3",
                    option = null
                )
            )
            val item1 = list[3]
            assertEquals(
                item1,
                ItemScreenModel(
                    id = 1,
                    pos = 1,
                    descService = "Service 1",
                    descComponent = "",
                    option = OptionResp.ACCORDING
                )
            )
            val item3 = list[4]
            assertEquals(
                item3,
                ItemScreenModel(
                    id = 3,
                    pos = 3,
                    descService = "Service 2",
                    descComponent = "02 - Component 2",
                    option = OptionResp.NON_CONFORMING,
                )
            )
        }



}