package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.Status
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject

@HiltAndroidTest
class IDeleteNoteTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: IDeleteNote

    @Inject
    lateinit var headerDao: HeaderDao

    @Inject
    lateinit var respDao: RespDao

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
    fun check_data_correct_if_noted_current_open() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    dateHour = Date(),
                    status = Status.OPEN
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
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val headerModelList = headerDao.all()
            assertEquals(
                headerModelList.size,
                1
            )
            val headerModel = headerModelList[0]
            assertEquals(
                headerModel.id,
                1
            )
            assertEquals(
                headerModel.idColab,
                1
            )
            assertEquals(
                headerModel.idFactorySection,
                1
            )
            assertEquals(
                headerModel.idOS,
                1
            )
            assertEquals(
                headerModel.status,
                Status.OPEN
            )
            val respModelList = respDao.all()
            assertEquals(
                respModelList.size,
                1
            )
            val respModel = respModelList[0]
            assertEquals(
                respModel.id,
                1
            )
            assertEquals(
                respModel.idHeader,
                1
            )
            assertEquals(
                respModel.idItem,
                1
            )
            assertEquals(
                respModel.idPlant,
                1
            )
            assertEquals(
                respModel.option,
                OptionResp.ACCORDING
            )
            assertEquals(
                respModel.obs,
                null
            )
        }

    @Test
    fun check_data_correct_if_noted_current_finish() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    dateHour = Date(),
                    status = Status.OPEN
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 1,
                    idOS = 2,
                    dateHour = Date(),
                    status = Status.FINISH
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
                    idHeader = 2,
                    idItem = 3,
                    idPlant = 1,
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
                true
            )
            val headerModelList = headerDao.all()
            assertEquals(
                headerModelList.size,
                2
            )
            val headerModel = headerModelList[0]
            assertEquals(
                headerModel.id,
                1
            )
            assertEquals(
                headerModel.idColab,
                1
            )
            assertEquals(
                headerModel.idFactorySection,
                1
            )
            assertEquals(
                headerModel.idOS,
                1
            )
            assertEquals(
                headerModel.status,
                Status.OPEN
            )
            val headerModel2 = headerModelList[1]
            assertEquals(
                headerModel2.id,
                2
            )
            assertEquals(
                headerModel2.idColab,
                2
            )
            assertEquals(
                headerModel2.idFactorySection,
                1
            )
            assertEquals(
                headerModel2.idOS,
                2
            )
            assertEquals(
                headerModel2.status,
                Status.FINISH
            )
            val respModelList = respDao.all()
            assertEquals(
                respModelList.size,
                2
            )
            val respModel = respModelList[0]
            assertEquals(
                respModel.id,
                1
            )
            assertEquals(
                respModel.idHeader,
                1
            )
            assertEquals(
                respModel.idItem,
                1
            )
            assertEquals(
                respModel.idPlant,
                1
            )
            assertEquals(
                respModel.option,
                OptionResp.ACCORDING
            )
            assertEquals(
                respModel.obs,
                null
            )
            val respModel2 = respModelList[1]
            assertEquals(
                respModel2.id,
                2
            )
            assertEquals(
                respModel2.idHeader,
                2
            )
            assertEquals(
                respModel2.idItem,
                3
            )
            assertEquals(
                respModel2.idPlant,
                1
            )
            assertEquals(
                respModel2.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                respModel2.obs,
                "obs"
            )
        }

    @Test
    fun check_data_correct_if_noted_minor_year() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    dateHour = Date(),
                    status = Status.OPEN
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 1,
                    idOS = 2,
                    dateHour = Date(),
                    status = Status.FINISH
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 3,
                    idFactorySection = 1,
                    idOS = 3,
                    dateHour = Date(1723292662000),
                    status = Status.OPEN,
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
                    idHeader = 2,
                    idItem = 3,
                    idPlant = 1,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs"
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 3,
                    idItem = 4,
                    idPlant = 2,
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
                true
            )
            val headerModelList = headerDao.all()
            assertEquals(
                headerModelList.size,
                2
            )
            val headerModel = headerModelList[0]
            assertEquals(
                headerModel.id,
                1
            )
            assertEquals(
                headerModel.idColab,
                1
            )
            assertEquals(
                headerModel.idFactorySection,
                1
            )
            assertEquals(
                headerModel.idOS,
                1
            )
            assertEquals(
                headerModel.status,
                Status.OPEN
            )
            val headerModel2 = headerModelList[1]
            assertEquals(
                headerModel2.id,
                2
            )
            assertEquals(
                headerModel2.idColab,
                2
            )
            assertEquals(
                headerModel2.idFactorySection,
                1
            )
            assertEquals(
                headerModel2.idOS,
                2
            )
            assertEquals(
                headerModel2.status,
                Status.FINISH
            )
            val respModelList = respDao.all()
            assertEquals(
                respModelList.size,
                2
            )
            val respModel = respModelList[0]
            assertEquals(
                respModel.id,
                1
            )
            assertEquals(
                respModel.idHeader,
                1
            )
            assertEquals(
                respModel.idItem,
                1
            )
            assertEquals(
                respModel.idPlant,
                1
            )
            assertEquals(
                respModel.option,
                OptionResp.ACCORDING
            )
            assertEquals(
                respModel.obs,
                null
            )
            val respModel2 = respModelList[1]
            assertEquals(
                respModel2.id,
                2
            )
            assertEquals(
                respModel2.idHeader,
                2
            )
            assertEquals(
                respModel2.idItem,
                3
            )
            assertEquals(
                respModel2.idPlant,
                1
            )
            assertEquals(
                respModel2.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                respModel2.obs,
                "obs"
            )
        }

    @Test
    fun check_data_correct_if_noted_minor_month_and_finish() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    dateHour = Date(),
                    status = Status.OPEN
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 1,
                    idOS = 2,
                    dateHour = Date(),
                    status = Status.FINISH
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 3,
                    idFactorySection = 1,
                    idOS = 3,
                    dateHour = Date(1723292662000),
                    status = Status.OPEN,
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 4,
                    idFactorySection = 1,
                    idOS = 4,
                    status = Status.FINISH,
                    dateHour = Date(1752150262000)
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
                    idHeader = 2,
                    idItem = 3,
                    idPlant = 1,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs"
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 3,
                    idItem = 4,
                    idPlant = 2,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs"
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 4,
                    idItem = 5,
                    idPlant = 2,
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
                true
            )
            val headerModelList = headerDao.all()
            assertEquals(
                headerModelList.size,
                2
            )
            val headerModel = headerModelList[0]
            assertEquals(
                headerModel.id,
                1
            )
            assertEquals(
                headerModel.idColab,
                1
            )
            assertEquals(
                headerModel.idFactorySection,
                1
            )
            assertEquals(
                headerModel.idOS,
                1
            )
            assertEquals(
                headerModel.status,
                Status.OPEN
            )
            val headerModel2 = headerModelList[1]
            assertEquals(
                headerModel2.id,
                2
            )
            assertEquals(
                headerModel2.idColab,
                2
            )
            assertEquals(
                headerModel2.idFactorySection,
                1
            )
            assertEquals(
                headerModel2.idOS,
                2
            )
            assertEquals(
                headerModel2.status,
                Status.FINISH
            )
            val respModelList = respDao.all()
            assertEquals(
                respModelList.size,
                2
            )
            val respModel = respModelList[0]
            assertEquals(
                respModel.id,
                1
            )
            assertEquals(
                respModel.idHeader,
                1
            )
            assertEquals(
                respModel.idItem,
                1
            )
            assertEquals(
                respModel.idPlant,
                1
            )
            assertEquals(
                respModel.option,
                OptionResp.ACCORDING
            )
            assertEquals(
                respModel.obs,
                null
            )
            val respModel2 = respModelList[1]
            assertEquals(
                respModel2.id,
                2
            )
            assertEquals(
                respModel2.idHeader,
                2
            )
            assertEquals(
                respModel2.idItem,
                3
            )
            assertEquals(
                respModel2.idPlant,
                1
            )
            assertEquals(
                respModel2.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                respModel2.obs,
                "obs"
            )
        }
}