package br.com.usinasantafe.pci.external.room.datasource.variable

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pci.external.room.DatabaseRoom
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IRespRoomDatasourceTest {

    private lateinit var respDao: RespDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IRespRoomDatasource

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        respDao = db.respDao()
        datasource = IRespRoomDatasource(respDao)
    }

    @Test
    fun `save - Check insert data is correct if not have data`() =
        runTest {
            val qtdBefore = respDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.save(
                RespRoomModel(
                    idHeader = 1,
                    idItem = 1,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs"
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val list = respDao.all()
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.idHeader,
                1
            )
            assertEquals(
                entity.idItem,
                1
            )
            assertEquals(
                entity.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                entity.obs,
                "obs"
            )
        }

    @Test
    fun `save - Check update data is correct if have data`() =
        runTest {
            respDao.insert(
                RespRoomModel(
                    idHeader = 1,
                    idItem = 1,
                    option = OptionResp.ACCORDING,
                    statusSend = StatusSend.SENT
                )
            )
            val listBefore = respDao.all()
            assertEquals(
                listBefore.size,
                1
            )
            val entityBefore = listBefore[0]
            assertEquals(
                entityBefore.id,
                1
            )
            assertEquals(
                entityBefore.idHeader,
                1
            )
            assertEquals(
                entityBefore.idItem,
                1
            )
            assertEquals(
                entityBefore.option,
                OptionResp.ACCORDING
            )
            assertEquals(
                entityBefore.obs,
                null
            )
            assertEquals(
                entityBefore.statusSend,
                StatusSend.SENT
            )
            val result = datasource.save(
                RespRoomModel(
                    idHeader = 1,
                    idItem = 1,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs"
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val list = respDao.all()
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entityBefore.id,
                1
            )
            assertEquals(
                entity.idHeader,
                1
            )
            assertEquals(
                entity.idItem,
                1
            )
            assertEquals(
                entity.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                entity.obs,
                "obs"
            )
            assertEquals(
                entityBefore.statusSend,
                StatusSend.SEND
            )
        }

    @Test
    fun `listByIdItems - Check list empty if not have data`() =
        runTest {
            val result = datasource.listByIdItems(listOf(1, 2))
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf<RespRoomModel>()
            )
            assertEquals(
                result.getOrNull()!!.size,
                0
            )
        }

    @Test
    fun `listByIdItems - Check return list correct`() =
        runTest {
            respDao.insert(
                RespRoomModel(
                    idHeader = 5,
                    idItem = 1,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs"
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 4,
                    idItem = 3,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs"
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 3,
                    idItem = 2,
                    option = OptionResp.ACCORDING
                )
            )
            val result = datasource.listByIdItems(listOf(1, 2))
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!.size,
                2
            )
            val list = result.getOrNull()!!
            assertEquals(
                list[0].idHeader,
                5
            )
            assertEquals(
                list[0].idItem,
                1
            )
            assertEquals(
                list[0].option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                list[0].obs,
                "obs"
            )
            assertEquals(
                list[1].idHeader,
                3
            )
            assertEquals(
                list[1].idItem,
                2
            )
            assertEquals(
                list[1].option,
                OptionResp.ACCORDING
            )
            assertEquals(
                list[1].obs,
                null
            )
        }

    @Test
    fun `getByIdItem - Check return list correct`() =
        runTest {
            respDao.insert(
                RespRoomModel(
                    idHeader = 5,
                    idItem = 1,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs"
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 4,
                    idItem = 3,
                    option = OptionResp.NON_CONFORMING,
                    obs = "obs"
                )
            )
            respDao.insert(
                RespRoomModel(
                    idHeader = 3,
                    idItem = 2,
                    option = OptionResp.ACCORDING
                )
            )
            val result = datasource.getByIdItem(2)
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model.idHeader,
                3
            )
            assertEquals(
                model.idItem,
                2
            )
            assertEquals(
                model.option,
                OptionResp.ACCORDING
            )
            assertEquals(
                model.obs,
                null
            )
        }
}