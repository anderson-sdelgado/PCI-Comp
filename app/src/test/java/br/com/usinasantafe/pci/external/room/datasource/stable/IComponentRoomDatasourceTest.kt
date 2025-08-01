package br.com.usinasantafe.pci.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pci.external.room.DatabaseRoom
import br.com.usinasantafe.pci.external.room.dao.stable.ComponentDao
import br.com.usinasantafe.pci.infra.models.room.stable.ComponentRoomModel
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
class IComponentRoomDatasourceTest {

    private lateinit var componentDao: ComponentDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IComponentRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        componentDao = db.componentDao()
        datasource = IComponentRoomDatasource(componentDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = componentDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ComponentRoomModel(
                        idComponent = 1,
                        codComponent = "1",
                        descComponent = "TESTE",
                    ),
                    ComponentRoomModel(
                        idComponent = 1,
                        codComponent = "1",
                        descComponent = "TESTE",
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IComponentRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_component` (`idComponent`,`codComponent`,`descComponent`) VALUES (?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_component.idComponent] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = componentDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = componentDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ComponentRoomModel(
                        idComponent = 1,
                        codComponent = "1",
                        descComponent = "TESTE 1",
                    ),
                    ComponentRoomModel(
                        idComponent = 2,
                        codComponent = "2",
                        descComponent = "TESTE 2",
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
            val qtdAfter = componentDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = componentDao.all()
            val entity1 = list[0]
            assertEquals(
                entity1.idComponent,
                1
            )
            assertEquals(
                entity1.codComponent,
                1
            )
            assertEquals(
                entity1.descComponent,
                "TESTE 1"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idComponent,
                2
            )
            assertEquals(
                entity2.codComponent,
                2
            )
            assertEquals(
                entity2.descComponent,
                "TESTE 2"
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            componentDao.insertAll(
                listOf(
                    ComponentRoomModel(
                        idComponent = 1,
                        codComponent = "1",
                        descComponent = "TESTE 1",
                    )
                )
            )
            val qtdBefore = componentDao.all().size
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
            val qtdAfter = componentDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `listByIdList - Check execution correct`() =
        runTest {
            componentDao.insertAll(
                listOf(
                    ComponentRoomModel(
                        idComponent = 1,
                        codComponent = "1",
                        descComponent = "TESTE 1",
                    ),
                    ComponentRoomModel(
                        idComponent = 2,
                        codComponent = "2",
                        descComponent = "TESTE 2",
                    ),
                    ComponentRoomModel(
                        idComponent = 3,
                        codComponent = "3",
                        descComponent = "TESTE 3",
                    ),
                    ComponentRoomModel(
                        idComponent = 4,
                        codComponent = "4",
                        descComponent = "TESTE 4",
                    ),
                    ComponentRoomModel(
                        idComponent = 5,
                        codComponent = "5",
                        descComponent = "TESTE 5",
                    )
                )
            )
            val qtdBefore = componentDao.all().size
            assertEquals(
                qtdBefore,
                5
            )
            val result = datasource.listByIds(listOf(2, 3, 4))
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                3
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idComponent,
                2
            )
            assertEquals(
                entity1.codComponent,
                2
            )
            assertEquals(
                entity1.descComponent,
                "TESTE 2"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idComponent,
                3
            )
            assertEquals(
                entity2.codComponent,
                3
            )
            assertEquals(
                entity2.descComponent,
                "TESTE 3"
            )
            val entity3 = list[2]
            assertEquals(
                entity3.idComponent,
                4
            )
            assertEquals(
                entity3.codComponent,
                4
            )
            assertEquals(
                entity3.descComponent,
                "TESTE 4"
            )
        }


}