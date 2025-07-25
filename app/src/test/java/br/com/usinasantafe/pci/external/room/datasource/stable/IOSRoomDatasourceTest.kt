package br.com.usinasantafe.pci.external.room.datasource.stable

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pci.external.room.DatabaseRoom
import br.com.usinasantafe.pci.external.room.dao.stable.OSDao
import br.com.usinasantafe.pci.infra.models.room.stable.OSRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IOSRoomDatasourceTest {

    private lateinit var osDao: OSDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IOSRoomDatasource

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        osDao = db.osDao()
        datasource = IOSRoomDatasource(osDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = osDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 1,
                        idPlantOS = 1,
                        qtdDayOS = 1,
                        descPeriodOS = "DIARIO"
                    ),
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 1,
                        idPlantOS = 1,
                        qtdDayOS = 1,
                        descPeriodOS = "DIARIO"
                    )
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_os` (`idOS`,`nroOS`,`idPlantOS`,`qtdDayOS`,`descPeriodOS`) VALUES (?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_os.idOS] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = osDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = osDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 1,
                        idPlantOS = 1,
                        qtdDayOS = 1,
                        descPeriodOS = "DIARIO"
                    ),
                    OSRoomModel(
                        idOS = 2,
                        nroOS = 2,
                        idPlantOS = 1,
                        qtdDayOS = 1,
                        descPeriodOS = "DIARIO"
                    )
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
            val qtdAfter = osDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = osDao.all()
            assertEquals(
                list[0].idOS,
                1
            )
            assertEquals(
                list[0].nroOS,
                1
            )
            assertEquals(
                list[0].idPlantOS,
                1
            )
            assertEquals(
                list[0].qtdDayOS,
                1
            )
            assertEquals(
                list[0].descPeriodOS,
                "DIARIO"
            )
            assertEquals(
                list[1].idOS,
                2
            )
            assertEquals(
                list[1].nroOS,
                2
            )
            assertEquals(
                list[1].idPlantOS,
                1
            )
            assertEquals(
                list[1].qtdDayOS,
                1
            )
            assertEquals(
                list[1].descPeriodOS,
                "DIARIO"
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 1,
                        idPlantOS = 1,
                        qtdDayOS = 1,
                        descPeriodOS = "DIARIO"
                    )
                )
            )
            val qtdBefore = osDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val qtdAfter = osDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }
}