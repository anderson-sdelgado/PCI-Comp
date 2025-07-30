package br.com.usinasantafe.pci.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pci.external.room.DatabaseRoom
import br.com.usinasantafe.pci.external.room.dao.stable.ServiceDao
import br.com.usinasantafe.pci.infra.models.room.stable.ServiceRoomModel
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
class IServiceRoomDatasourceTest {

    private lateinit var serviceDao: ServiceDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IServiceRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        serviceDao = db.serviceDao()
        datasource = IServiceRoomDatasource(serviceDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = serviceDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "TESTE",
                    ),
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "TESTE",
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IServiceRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_service` (`idService`,`codService`,`descService`) VALUES (?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_service.idService] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = serviceDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = serviceDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "TESTE 1",
                    ),
                    ServiceRoomModel(
                        idService = 2,
                        codService = 2,
                        descService = "TESTE 2",
                    ),
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
            val qtdAfter = serviceDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = serviceDao.all()
            val entity1 = list[0]
            assertEquals(
                entity1.idService,
                1
            )
            assertEquals(
                entity1.codService,
                1
            )
            assertEquals(
                entity1.descService,
                "TESTE 1"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idService,
                2
            )
            assertEquals(
                entity2.codService,
                2
            )
            assertEquals(
                entity2.descService,
                "TESTE 2"
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            serviceDao.insertAll(
                listOf(
                    ServiceRoomModel(
                        idService = 1,
                        codService = 1,
                        descService = "TESTE 1",
                    )
                )
            )
            val qtdBefore = serviceDao.all().size
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
            val qtdAfter = serviceDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

}