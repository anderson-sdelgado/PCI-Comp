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
                    idOS = 1
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
    fun `getByStatus - Check data save is correct`() =
        runTest {
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
                    idColab = 2,
                    idFactorySection = 2,
                    idOS = 2,
                    status = Status.OPEN
                )
            )
            val result = datasource.getByStatusOpenDefault(Status.OPEN)
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model.id,
                2
            )
            assertEquals(
                model.idColab,
                2
            )
            assertEquals(
                model.idFactorySection,
                2
            )
            assertEquals(
                model.idOS,
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
                    status = Status.CLOSE
                )
            )
            headerDao.insert(
                HeaderRoomModel(
                    idColab = 2,
                    idFactorySection = 2,
                    idOS = 2,
                    status = Status.OPEN
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

}