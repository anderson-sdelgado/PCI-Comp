package br.com.usinasantafe.pci.domain.usecases.header

import br.com.usinasantafe.pci.external.room.dao.stable.OSDao
import br.com.usinasantafe.pci.external.room.dao.stable.PlantDao
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.PlantRoomModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class IListOSHeaderTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: IListOSHeader

    @Inject
    lateinit var headerSharedPreferencesDatasource: HeaderSharedPreferencesDatasource

    @Inject
    lateinit var osDao: OSDao

    @Inject
    lateinit var plantDao: PlantDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_header_internal() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListOSHeader -> ICheckListRepository.getIdFactorySectionHeaderOpen -> IHeaderSharedPreferencesDatasource.getIdFactorySection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_list_empty_if_not_have_data() =
        runTest {
            headerSharedPreferencesDatasource.setIdColabAndIdFactorySection(
                idColab = 1,
                idFactorySection = 1
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.count(),
                0
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_plant_to_os() =
        runTest {
            headerSharedPreferencesDatasource.setIdColabAndIdFactorySection(
                idColab = 1,
                idFactorySection = 1
            )
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 1,
                        idPlantOS = 1,
                        qtdDayOS = 1,
                        descPeriodOS = "DIARIO",
                        idFactorySectionOS = 1
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListOSHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.util.NoSuchElementException: Collection contains no element matching the predicate."
            )
        }

    @Test
    fun check_return_list_correct_if_process_execute_successfully() =
        runTest {
            headerSharedPreferencesDatasource.setIdColabAndIdFactorySection(
                idColab = 1,
                idFactorySection = 1
            )
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 1,
                        idPlantOS = 1,
                        qtdDayOS = 1,
                        descPeriodOS = "DIARIO",
                        idFactorySectionOS = 1
                    ),
                    OSRoomModel(
                        idOS = 2,
                        nroOS = 2,
                        idPlantOS = 1,
                        qtdDayOS = 2,
                        descPeriodOS = "SEMANAL",
                        idFactorySectionOS = 1
                    ),
                    OSRoomModel(
                        idOS = 3,
                        nroOS = 3,
                        idPlantOS = 2,
                        qtdDayOS = 2,
                        descPeriodOS = "DIARIO",
                        idFactorySectionOS = 2
                    ),
                    OSRoomModel(
                        idOS = 4,
                        nroOS = 4,
                        idPlantOS = 2,
                        qtdDayOS = 2,
                        descPeriodOS = "DIARIO",
                        idFactorySectionOS = 1
                    )
                )
            )
            plantDao.insertAll(
                listOf(
                    PlantRoomModel(
                        idPlant = 1,
                        codPlant = "001",
                        descPlant = "PLANT 001",
                        idFactorySectionPlant = 1
                    ),
                    PlantRoomModel(
                        idPlant = 2,
                        codPlant = "002",
                        descPlant = "PLANT 002",
                        idFactorySectionPlant = 1
                        ),
                    PlantRoomModel(
                        idPlant = 3,
                        codPlant = "003",
                        descPlant = "PLANT 003",
                        idFactorySectionPlant = 2
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.count(),
                3
            )
            assertEquals(
                list[0].id,
                1
            )
            assertEquals(
                list[0].os,
                "OS 1"
            )
            assertEquals(
                list[0].period,
                "DIARIO"
            )
            assertEquals(
                list[0].codPlant,
                "001"
            )
            assertEquals(
                list[0].descPlant,
                "PLANT 001"
            )
            assertEquals(
                list[1].id,
                2
            )
            assertEquals(
                list[1].os,
                "OS 2"
            )
            assertEquals(
                list[1].period,
                "SEMANAL"
            )
            assertEquals(
                list[1].codPlant,
                "001"
            )
            assertEquals(
                list[1].descPlant,
                "PLANT 001"
            )
            assertEquals(
                list[2].id,
                4
            )
            assertEquals(
                list[2].os,
                "OS 4"
            )
            assertEquals(
                list[2].period,
                "DIARIO"
            )
            assertEquals(
                list[2].codPlant,
                "002"
            )
            assertEquals(
                list[2].descPlant,
                "PLANT 002"
            )
        }
}