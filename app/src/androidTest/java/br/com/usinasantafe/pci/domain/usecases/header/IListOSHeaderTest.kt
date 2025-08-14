package br.com.usinasantafe.pci.domain.usecases.header

import br.com.usinasantafe.pci.external.room.dao.stable.OSDao
import br.com.usinasantafe.pci.external.room.dao.stable.PlantDao
import br.com.usinasantafe.pci.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.PlantRoomModel
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.presenter.model.OSScreenModel
import br.com.usinasantafe.pci.utils.Status
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

    @Inject
    lateinit var headerDao: HeaderDao

    val plantList = listOf(
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
            idFactorySectionPlant = 1
        ),
        PlantRoomModel(
            idPlant = 4,
            codPlant = "004",
            descPlant = "PLANT 004",
            idFactorySectionPlant = 1
        ),
        PlantRoomModel(
            idPlant = 5,
            codPlant = "005",
            descPlant = "PLANT 005",
            idFactorySectionPlant = 2
        )
    )

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
    fun check_return_list_correct_if_process_execute_successfully_without_header() =
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
                        idPlantOS = 5,
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
                    ),
                    OSRoomModel(
                        idOS = 5,
                        nroOS = 5,
                        idPlantOS = 3,
                        qtdDayOS = 2,
                        descPeriodOS = "DIARIO",
                        idFactorySectionOS = 1
                    )
                )
            )
            plantDao.insertAll(plantList)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    OSScreenModel(
                        idOS = 1,
                        nroOS = 1,
                        period = "DIARIO",
                        codPlant = "001",
                        descPlant = "PLANT 001",
                        status = false
                    ),
                    OSScreenModel(
                        idOS = 2,
                        nroOS = 2,
                        period = "SEMANAL",
                        codPlant = "001",
                        descPlant = "PLANT 001",
                        status = false
                    ),
                    OSScreenModel(
                        idOS = 4,
                        nroOS = 4,
                        period = "DIARIO",
                        codPlant = "002",
                        descPlant = "PLANT 002",
                        status = false
                    ),
                    OSScreenModel(
                        idOS = 5,
                        nroOS = 5,
                        period = "DIARIO",
                        codPlant = "003",
                        descPlant = "PLANT 003",
                        status = false
                    )
                )
            )
        }

    @Test
    fun check_return_list_correct_if_process_execute_successfully_with_header_close_same_colab() =
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
                        idPlantOS = 5,
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
                    ),
                    OSRoomModel(
                        idOS = 5,
                        nroOS = 5,
                        idPlantOS = 3,
                        qtdDayOS = 2,
                        descPeriodOS = "DIARIO",
                        idFactorySectionOS = 1
                    )
                )
            )
            plantDao.insertAll(plantList)
            headerDao.insert(
                HeaderRoomModel(
                    id = 1,
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE
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
                    OSScreenModel(
                        idOS = 2,
                        nroOS = 2,
                        period = "SEMANAL",
                        codPlant = "001",
                        descPlant = "PLANT 001",
                        status = false
                    ),
                    OSScreenModel(
                        idOS = 4,
                        nroOS = 4,
                        period = "DIARIO",
                        codPlant = "002",
                        descPlant = "PLANT 002",
                        status = false
                    ),
                    OSScreenModel(
                        idOS = 5,
                        nroOS = 5,
                        period = "DIARIO",
                        codPlant = "003",
                        descPlant = "PLANT 003",
                        status = false
                    ),
                    OSScreenModel(
                        idOS = 1,
                        nroOS = 1,
                        period = "DIARIO",
                        codPlant = "001",
                        descPlant = "PLANT 001",
                        status = true
                    )
                )
            )
        }

    @Test
    fun check_return_list_correct_if_process_execute_successfully_with_header_finish_same_colab() =
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
                        idPlantOS = 5,
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
                    ),
                    OSRoomModel(
                        idOS = 5,
                        nroOS = 5,
                        idPlantOS = 3,
                        qtdDayOS = 2,
                        descPeriodOS = "DIARIO",
                        idFactorySectionOS = 1
                    )
                )
            )
            plantDao.insertAll(plantList)
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 4,
                    status = Status.FINISH
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
                    OSScreenModel(
                        idOS = 2,
                        nroOS = 2,
                        period = "SEMANAL",
                        codPlant = "001",
                        descPlant = "PLANT 001",
                        status = false
                    ),
                    OSScreenModel(
                        idOS = 5,
                        nroOS = 5,
                        period = "DIARIO",
                        codPlant = "003",
                        descPlant = "PLANT 003",
                        status = false
                    ),
                    OSScreenModel(
                        idOS = 1,
                        nroOS = 1,
                        period = "DIARIO",
                        codPlant = "001",
                        descPlant = "PLANT 001",
                        status = true
                    )
                )
            )
        }

    @Test
    fun check_return_list_correct_if_process_execute_successfully_with_header_other_colab() =
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
                        idPlantOS = 5,
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
                    ),
                    OSRoomModel(
                        idOS = 5,
                        nroOS = 5,
                        idPlantOS = 3,
                        qtdDayOS = 2,
                        descPeriodOS = "DIARIO",
                        idFactorySectionOS = 1
                    )
                )
            )
            plantDao.insertAll(plantList)
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 4,
                    status = Status.FINISH
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 1,
                    idOS = 5,
                    status = Status.CLOSE
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
                    OSScreenModel(
                        idOS = 2,
                        nroOS = 2,
                        period = "SEMANAL",
                        codPlant = "001",
                        descPlant = "PLANT 001",
                        status = false
                    ),
                    OSScreenModel(
                        idOS = 1,
                        nroOS = 1,
                        period = "DIARIO",
                        codPlant = "001",
                        descPlant = "PLANT 001",
                        status = true
                    )
                )
            )
        }
}