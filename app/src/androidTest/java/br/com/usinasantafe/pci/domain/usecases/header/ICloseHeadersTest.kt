package br.com.usinasantafe.pci.domain.usecases.header

import br.com.usinasantafe.pci.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
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
class ICloseHeadersTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ICloseHeaders

    @Inject
    lateinit var headerDao: HeaderDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_true_if_header_is_empty() =
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
    fun check_close_all_header_open_if_process_execute_successfully() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    id = 1,
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.OPEN
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    id = 2,
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 2,
                    status = Status.OPEN
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    id = 3,
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 3,
                    status = Status.FINISH
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    id = 4,
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 4,
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
                true
            )
            val list = headerDao.all()
            assertEquals(
                list.count(),
                4
            )
            val model1 = list[0]
            assertEquals(
                model1.id,
                1
            )
            assertEquals(
                model1.idColab,
                1
            )
            assertEquals(
                model1.idFactorySection,
                1
            )
            assertEquals(
                model1.idOS,
                1
            )
            assertEquals(
                model1.status,
                Status.CLOSE
            )
            val model2 = list[1]
            assertEquals(
                model2.id,
                2
            )
            assertEquals(
                model2.idColab,
                1
            )
            assertEquals(
                model2.idFactorySection,
                1
            )
            assertEquals(
                model2.idOS,
                2
            )
            assertEquals(
                model2.status,
                Status.CLOSE
            )
            val model3 = list[2]
            assertEquals(
                model3.id,
                3
            )
            assertEquals(
                model3.idColab,
                1
            )
            assertEquals(
                model3.idFactorySection,
                1
            )
            assertEquals(
                model3.idOS,
                3
            )
            assertEquals(
                model3.status,
                Status.FINISH
            )
            val model4 = list[3]
            assertEquals(
                model4.id,
                4
            )
            assertEquals(
                model4.idColab,
                1
            )
            assertEquals(
                model4.idFactorySection,
                1
            )
            assertEquals(
                model4.idOS,
                4
            )
            assertEquals(
                model4.status,
                Status.CLOSE
            )
        }
}