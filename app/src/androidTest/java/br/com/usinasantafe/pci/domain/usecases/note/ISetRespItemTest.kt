package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.utils.OptionResp
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
class ISetRespItemTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ISetRespItem

    @Inject
    lateinit var headerDao: HeaderDao

    @Inject
    lateinit var respDao: RespDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_header_table() =
        runTest {
            val result = usecase(
                id = 1,
                idPlant = 1,
                option = OptionResp.NON_CONFORMING,
                obs = "obs"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItem -> ICheckListRepository.saveResp"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_data_insert_if_process_execute_successfully() =
        runTest {
            headerDao.insert(
                model = HeaderRoomModel(
                    id = 1,
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.OPEN,
                )
            )
            val qtdBefore = respDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = usecase(
                id = 1,
                idPlant = 1,
                option = OptionResp.NON_CONFORMING,
                obs = "obs"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val qtdAfter = respDao.all().size
            assertEquals(
                qtdAfter,
                1
            )
            val list = respDao.all()
            val model = list[0]
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.idItem,
                1
            )
            assertEquals(
                model.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                model.obs,
                "obs"
            )
        }

}