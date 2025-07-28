package br.com.usinasantafe.pci.external.room.datasource.stable

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pci.external.room.DatabaseRoom
import br.com.usinasantafe.pci.external.room.dao.stable.PlantDao
import br.com.usinasantafe.pci.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.pci.infra.models.room.stable.PlantRoomModel
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
class IPlantRoomDatasourceTest {

    private lateinit var plantDao: PlantDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IPlantRoomDatasource

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        plantDao = db.plantDao()
        datasource = IPlantRoomDatasource(plantDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = plantDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    PlantRoomModel(
                        idPlant = 1,
                        codPlant = "01",
                        descPlant = "PLANT 01"
                    ),
                    PlantRoomModel(
                        idPlant = 1,
                        codPlant = "01",
                        descPlant = "PLANT 01"
                    )
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPlantRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_plant` (`idPlant`,`codPlant`,`descPlant`) VALUES (?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_plant.idPlant] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = plantDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = plantDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    PlantRoomModel(
                        idPlant = 1,
                        codPlant = "01",
                        descPlant = "PLANT 01"
                    ),
                    PlantRoomModel(
                        idPlant = 2,
                        codPlant = "02",
                        descPlant = "PLANT 02"
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
            val qtdAfter = plantDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = plantDao.all()
            assertEquals(
                list[0].idPlant,
                1
            )
            assertEquals(
                list[0].codPlant,
                "01"
            )
            assertEquals(
                list[0].descPlant,
                "PLANT 01"
            )
            assertEquals(
                list[1].idPlant,
                2
            )
            assertEquals(
                list[1].codPlant,
                "02"
            )
            assertEquals(
                list[1].descPlant,
                "PLANT 02"
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            plantDao.insertAll(
                listOf(
                    PlantRoomModel(
                        idPlant = 1,
                        codPlant = "01",
                        descPlant = "PLANT 01"
                    )
                )
            )
            val qtdBefore = plantDao.all().size
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
            val qtdAfter = plantDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }
}