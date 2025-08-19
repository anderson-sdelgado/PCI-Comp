package br.com.usinasantafe.pci.external.room.datasource.variable

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pci.external.room.DatabaseRoom
import br.com.usinasantafe.pci.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.pci.utils.Status
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date
import kotlin.intArrayOf

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IHeaderRoomDatasourceTest {

    private lateinit var headerDao: HeaderDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IHeaderRoomDatasource

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        headerDao = db.headerDao()
        datasource = IHeaderRoomDatasource(headerDao)
    }

    @Test
    fun `save - Check data save is correct`() =
        runTest {
            val qtdBefore = headerDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.save(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    dateHour = Date()
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
            val qtdAfter = headerDao.all().size
            assertEquals(
                qtdAfter,
                1
            )
            val list = headerDao.all()
            assertEquals(
                list[0].idColab,
                1
            )
            assertEquals(
                list[0].idFactorySection,
                1
            )
            assertEquals(
                list[0].idOS,
                1
            )
            assertEquals(
                list[0].id,
                1
            )
            assertEquals(
                list[0].status,
                Status.OPEN
            )
        }

    @Test
    fun `save - Check add`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 1,
                    idOS = 100,
                    status = Status.CLOSE,
                    dateHour = Date()
                )
            )
            val qtdBefore = headerDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.save(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    dateHour = Date()
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

            val list = headerDao.all()
            assertEquals(
                list.size,
                2
            )
            val model1 = list[0]
            assertEquals(
                model1.id,
                1
            )
            assertEquals(
                model1.idColab,
                2
            )
            assertEquals(
                model1.idFactorySection,
                1
            )
            assertEquals(
                model1.idOS,
                100
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
                1
            )
            assertEquals(
                model2.status,
                Status.OPEN
            )
        }

    @Test
    fun `save - Check update`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 1,
                    idOS = 100,
                    status = Status.CLOSE,
                    dateHour = Date()
                )
            )
            val qtdBefore = headerDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.save(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 1,
                    idOS = 100,
                    dateHour = Date()
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

            val list = headerDao.all()
            assertEquals(
                list.size,
                1
            )
            val model1 = list[0]
            assertEquals(
                model1.id,
                1
            )
            assertEquals(
                model1.idColab,
                2
            )
            assertEquals(
                model1.idFactorySection,
                1
            )
            assertEquals(
                model1.idOS,
                100
            )
            assertEquals(
                model1.status,
                Status.OPEN
            )
        }

    @Test
    fun `getIdByStatus - Check return data`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE,
                    dateHour = Date()
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 2,
                    idOS = 2,
                    status = Status.OPEN,
                    dateHour = Date()
                )
            )
            val result = datasource.getIdByStatusOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            val id = result.getOrNull()!!
            assertEquals(
                id,
                2
            )
        }

    @Test
    fun `finish - Check return failure if not have header open`() =
        runTest {
            val result = datasource.finish()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderRoomDatasource.finish"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel.setStatus(br.com.usinasantafe.pci.utils.Status)\" because \"model\" is null"
            )
        }

    @Test
    fun `finish - Check data if finish is correct`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE,
                    dateHour = Date()
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 2,
                    idOS = 2,
                    status = Status.OPEN,
                    dateHour = Date()
                )
            )
            val result = datasource.finish()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val list = headerDao.all()
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
                2
            )
            assertEquals(
                model2.idFactorySection,
                2
            )
            assertEquals(
                model2.idOS,
                2
            )
            assertEquals(
                model2.status,
                Status.FINISH
            )
        }

    @Test
    fun `listByIdOSList - Check return data correct`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE,
                    dateHour = Date()
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 2,
                    idOS = 2,
                    status = Status.OPEN,
                    dateHour = Date()
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 3,
                    idFactorySection = 3,
                    idOS = 3,
                    status = Status.OPEN,
                    dateHour = Date()
                )
            )
            val ids = listOf(1, 3)
            val result = datasource.listByIdOSList(ids)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    HeaderRoomModel(
                        idColab = 1,
                        idFactorySection = 1,
                        idOS = 1,
                        status = Status.CLOSE,
                        dateHour = Date()
                    ),
                    HeaderRoomModel(
                        idColab = 3,
                        idFactorySection = 3,
                        idOS = 3,
                        status = Status.OPEN,
                        dateHour = Date()
                    )
                )
            )
        }

    @Test
    fun `close - Check data if finish is correct`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE,
                    dateHour = Date()
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 2,
                    idOS = 2,
                    status = Status.OPEN,
                    dateHour = Date()
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 3,
                    idFactorySection = 1,
                    idOS = 3,
                    status = Status.FINISH,
                    dateHour = Date()
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 4,
                    idFactorySection = 1,
                    idOS = 10,
                    status = Status.OPEN,
                    dateHour = Date()
                )
            )
            val result = datasource.close()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val list = headerDao.all()
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
                2
            )
            assertEquals(
                model2.idFactorySection,
                2
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
                3
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
                4
            )
            assertEquals(
                model4.idFactorySection,
                1
            )
            assertEquals(
                model4.idOS,
                10
            )
            assertEquals(
                model4.status,
                Status.CLOSE
            )
        }

    @Test
    fun `listByIds - Check return data correct`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE,
                    dateHour = Date()
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 2,
                    idOS = 2,
                    status = Status.OPEN,
                    dateHour = Date()
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 3,
                    idFactorySection = 3,
                    idOS = 3,
                    status = Status.OPEN,
                    dateHour = Date()
                )
            )
            val ids = listOf(1, 3)
            val result = datasource.listByIds(ids)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
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
                3
            )
            assertEquals(
                model2.idColab,
                3
            )
            assertEquals(
                model2.idFactorySection,
                3
            )
            assertEquals(
                model2.idOS,
                3
            )
            assertEquals(
                model2.status,
                Status.OPEN
            )
        }

    @Test
    fun `setIdServById - Check return failure if not have data`() =
        runTest {
            val result = datasource.setIdServById(
                1,
                1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderRoomDatasource.setIdServById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.message,
                "Cannot invoke \"br.com.usinasantafe.pci.infra.models.room.variable.HeaderRoomModel.setIdServ(java.lang.Integer)\" because \"model\" is null"
            )
        }

    @Test
    fun `setIdServById - Check alter data`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE,
                    dateHour = Date()
                )
            )
            val result = datasource.setIdServById(
                1,
                1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val list = headerDao.all()
            val model = list[0]
            assertEquals(
                model.id,
                1
            )
            assertEquals(
                model.idColab,
                1
            )
            assertEquals(
                model.idFactorySection,
                1
            )
            assertEquals(
                model.idOS,
                1
            )
            assertEquals(
                model.idServ,
                1
            )
            assertEquals(
                model.status,
                Status.CLOSE
            )
        }

    @Test
    fun `all - Check list empty`() =
        runTest {
            val result = datasource.all()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf<HeaderRoomModel>()
            )
        }

    @Test
    fun `all - Check list correct`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE,
                    dateHour = Date()
                )
            )
            val result = datasource.all()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.id,
                1
            )
            assertEquals(
                model.idColab,
                1
            )
            assertEquals(
                model.idFactorySection,
                1
            )
            assertEquals(
                model.idOS,
                1
            )
            assertEquals(
                model.status,
                Status.CLOSE
            )
        }

    @Test
    fun `delete - Check not delete if idHeader is different`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE,
                    dateHour = Date()
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 2,
                    idOS = 2,
                    status = Status.OPEN,
                    dateHour = Date()
                )
            )
            val result = datasource.delete(
                3
            )
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
                list.size,
                2
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
                2
            )
            assertEquals(
                model2.idFactorySection,
                2
            )
            assertEquals(
                model2.idOS,
                2
            )
            assertEquals(
                model2.status,
                Status.OPEN
            )

        }

    @Test
    fun `delete - Check delete if idHeader is equal`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE,
                    dateHour = Date()
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 2,
                    idOS = 2,
                    status = Status.OPEN,
                    dateHour = Date()
                )
            )
            val result = datasource.delete(
                2
            )
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
                list.size,
                1
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
        }

    @Test
    fun `checkOpen - Check not have header open`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1,
                    status = Status.CLOSE,
                    dateHour = Date()
                )
            )
            val result = datasource.checkOpen()
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
    fun `checkOpen - Check have header open`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 2,
                    idOS = 2,
                    status = Status.OPEN,
                    dateHour = Date()
                )
            )
            val result = datasource.checkOpen()
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