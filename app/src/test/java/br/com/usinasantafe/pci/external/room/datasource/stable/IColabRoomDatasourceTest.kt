package br.com.usinasantafe.pci.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pci.external.room.DatabaseRoom
import br.com.usinasantafe.pci.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pci.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.collections.addAll
import kotlin.collections.get
import kotlin.intArrayOf

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IColabRoomDatasourceTest {

    private lateinit var colabDao: ColabDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IColabRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        colabDao = db.colabDao()
        datasource = IColabRoomDatasource(colabDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = colabDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ColabRoomModel(
                        idColab = 1,
                        regColab = 1,
                        nameColab = "TESTE",
                        idFactorySectionColab = 1
                    ),
                    ColabRoomModel(
                        idColab = 1,
                        regColab = 1,
                        nameColab = "TESTE",
                        idFactorySectionColab = 1
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IColabRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_colab` (`idColab`,`regColab`,`nameColab`,`idFactorySectionColab`) VALUES (?,?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_colab.idColab] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = colabDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = colabDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ColabRoomModel(
                        idColab = 1,
                        regColab = 1,
                        nameColab = "TESTE 1",
                        idFactorySectionColab = 1
                    ),
                    ColabRoomModel(
                        idColab = 2,
                        regColab = 2,
                        nameColab = "TESTE 2",
                        idFactorySectionColab = 2
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
            val qtdAfter = colabDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = colabDao.all()
            val entity1 = list[0]
            assertEquals(
                entity1.idColab,
                1
            )
            assertEquals(
                entity1.regColab,
                1
            )
            assertEquals(
                entity1.nameColab,
                "TESTE 1"
            )
            assertEquals(
                entity1.idFactorySectionColab,
                1
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idColab,
                2
            )
            assertEquals(
                entity2.regColab,
                2
            )
            assertEquals(
                entity2.nameColab,
                "TESTE 2"
            )
            assertEquals(
                entity2.idFactorySectionColab,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            colabDao.insertAll(
                listOf(
                    ColabRoomModel(
                        idColab = 1,
                        regColab = 1,
                        nameColab = "TESTE 1",
                        idFactorySectionColab = 1
                    )
                )
            )
            val qtdBefore = colabDao.all().size
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
            val qtdAfter = colabDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

}