package br.com.usinasantafe.pci.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pci.external.room.DatabaseRoom
import br.com.usinasantafe.pci.external.room.dao.stable.ItemDao
import br.com.usinasantafe.pci.infra.models.room.stable.ItemRoomModel
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
class IItemRoomDatasourceTest {

    private lateinit var itemDao: ItemDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IItemRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        itemDao = db.itemDao()
        datasource = IItemRoomDatasource(itemDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = itemDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 1
                    ),
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 1
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_item` (`idItem`,`seqItem`,`idOSItem`,`idPlantItem`,`idComponentItem`,`idServiceItem`) VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_item.idItem] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = itemDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = itemDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 1
                    ),
                    ItemRoomModel(
                        idItem = 2,
                        seqItem = 2,
                        idOSItem = 2,
                        idPlantItem = 2,
                        idComponentItem = 2,
                        idServiceItem = 2
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
            val qtdAfter = itemDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = itemDao.all()
            val entity1 = list[0]
            assertEquals(
                entity1.idItem,
                1
            )
            assertEquals(
                entity1.seqItem,
                1
            )
            assertEquals(
                entity1.idOSItem,
                1
            )
            assertEquals(
                entity1.idPlantItem,
                1
            )
            assertEquals(
                entity1.idComponentItem,
                1
            )
            assertEquals(
                entity1.idServiceItem,
                1
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idItem,
                2
            )
            assertEquals(
                entity2.seqItem,
                2
            )
            assertEquals(
                entity2.idOSItem,
                2
            )
            assertEquals(
                entity2.idPlantItem,
                2
            )
            assertEquals(
                entity2.idComponentItem,
                2
            )
            assertEquals(
                entity2.idServiceItem,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            itemDao.insertAll(
                listOf(
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 1
                    )
                )
            )
            val qtdBefore = itemDao.all().size
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
            val qtdAfter = itemDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `listAll - Check return empty list if table is empty`() =
        runTest {
            val result = datasource.listAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList<ItemRoomModel>()
            )
        }

    @Test
    fun `listAll - Check execution correct`() =
        runTest {
            itemDao.insertAll(
                listOf(
                    ItemRoomModel(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 1,
                        idServiceItem = 1
                    ),
                    ItemRoomModel(
                        idItem = 2,
                        seqItem = 2,
                        idOSItem = 2,
                        idPlantItem = 2,
                        idComponentItem = 2,
                        idServiceItem = 2
                    ),
                )
            )
            val result = datasource.listAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            assertEquals(
                list[0].idItem,
                1
            )
            assertEquals(
                list[0].seqItem,
                1
            )
            assertEquals(
                list[0].idOSItem,
                1
            )
            assertEquals(
                list[0].idPlantItem,
                1
            )
            assertEquals(
                list[0].idComponentItem,
                1
            )
            assertEquals(
                list[0].idServiceItem,
                1
            )
            assertEquals(
                list[1].idItem,
                2
            )
            assertEquals(
                list[1].seqItem,
                2
            )
            assertEquals(
                list[1].idOSItem,
                2
            )
            assertEquals(
                list[1].idPlantItem,
                2
            )
            assertEquals(
                list[1].idComponentItem,
                2
            )
            assertEquals(
                list[1].idServiceItem,
                2
            )
        }

}