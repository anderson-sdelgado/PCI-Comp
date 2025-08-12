package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.Status
import br.com.usinasantafe.pci.utils.StatusSend
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ICloseItemsNoteTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ICloseItemsNote

    @Inject
    lateinit var headerDao: HeaderDao

    @Inject
    lateinit var respDao: RespDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_header_table() =
        runTest {
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICloseItemsNote -> ICheckListRepository.closeItems"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_alter_data_if_process_execute_successfully() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 2,
                    status = Status.CLOSE
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 1,
                    idItem = 1,
                    idPlant = 1,
                    option = OptionResp.ACCORDING,
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 1,
                    idItem = 3,
                    idPlant = 1,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs"
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 2,
                    idItem = 2,
                    idPlant = 1,
                    option = OptionResp.ACCORDING
                )
            )
            val listBefore = respDao.all()
            assertEquals(
                listBefore.size,
                3
            )
            val model1Before = listBefore[0]
            assertEquals(
                model1Before,
                RespRoomModel(
                    id = 1,
                    idHeader = 1,
                    idItem = 1,
                    idPlant = 1,
                    option = OptionResp.ACCORDING,
                    obs = null,
                    statusSend = StatusSend.SEND,
                    status = Status.OPEN
                )
            )
            val model2Before = listBefore[1]
            assertEquals(
                model2Before,
                RespRoomModel(
                    id = 2,
                    idHeader = 1,
                    idItem = 3,
                    idPlant = 1,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs",
                    statusSend = StatusSend.SEND,
                    status = Status.OPEN
                )
            )
            val model3Before = listBefore[2]
            assertEquals(
                model3Before,
                RespRoomModel(
                    id = 3,
                    idHeader = 2,
                    idItem = 2,
                    idPlant = 1,
                    option = OptionResp.ACCORDING,
                    obs = null,
                    statusSend = StatusSend.SEND,
                    status = Status.OPEN
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val listAfter = respDao.all()
            assertEquals(
                listAfter.size,
                3
            )
            val model1After = listAfter[0]
            assertEquals(
                model1After,
                RespRoomModel(
                    id = 1,
                    idHeader = 1,
                    idItem = 1,
                    idPlant = 1,
                    option = OptionResp.ACCORDING,
                    obs = null,
                    statusSend = StatusSend.SEND,
                    status = Status.CLOSE
                )
            )
            val model2After = listAfter[1]
            assertEquals(
                model2After,
                RespRoomModel(
                    id = 2,
                    idHeader = 1,
                    idItem = 3,
                    idPlant = 1,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs",
                    statusSend = StatusSend.SEND,
                    status = Status.CLOSE
                )
            )
            val model3After = listAfter[2]
            assertEquals(
                model3After,
                RespRoomModel(
                    id = 3,
                    idHeader = 2,
                    idItem = 2,
                    idPlant = 1,
                    option = OptionResp.ACCORDING,
                    obs = null,
                    statusSend = StatusSend.SEND,
                    status = Status.OPEN
                )
            )
        }
}