package br.com.usinasantafe.pci.domain.usecases.update

import br.com.usinasantafe.pci.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.pci.external.room.dao.stable.ItemDao
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.presenter.model.ResultUpdateModel
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import br.com.usinasantafe.pci.utils.updatePercentage
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class IUpdateTableItemByIdOSTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: UpdateTableItemByIdOS

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerSharedPreferencesDatasource: HeaderSharedPreferencesDatasource

    @Inject
    lateinit var itemDao: ItemDao

    private val resultItemListIncorrect = """
        [
            {"idItem":1,"seqItem":1,"idOSItem":1,"idPlantItem":1,"idComponentItem":1,"idServiceItem":1},
            {"idItem":1,"seqItem":1,"idOSItem":1,"idPlantItem":1,"idComponentItem":1,"idServiceItem":1}
        ]
    """.trimIndent()

    private val resultItemList = """
        [
            {"idItem":1,"seqItem":1,"idOSItem":1,"idPlantItem":1,"idComponentItem":1,"idServiceItem":1},
            {"idItem":2,"seqItem":2,"idOSItem":2,"idPlantItem":2,"idComponentItem":2,"idServiceItem":2}
        ]
    """.trimIndent()

    @Test
    fun check_return_failure_if_not_have_data_in_config_internal() =
        runTest {

            hiltRule.inject()

            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                list.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemByIdOS -> IGetToken -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_shared_internal() =
        runTest {

            hiltRule.inject()

            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idBD = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                )
            )

            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                list.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemByIdOS -> ICheckListRepository.getIdOSHeaderOpen -> IHeaderSharedPreferencesDatasource.getIdOS -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
        }

    @Test
    fun check_return_failure_if_web_service_return_error() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idBD = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                )
            )

            headerSharedPreferencesDatasource.setIdOS(
                idOS = 1
            )

            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                list.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemByIdOS -> IItemRepository.listByIdOS -> IItemRetrofitDatasource.listByIdOS -> java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
            server.shutdown()
        }

    @Test
    fun check_return_failure_if_web_service_return_value_repeated() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultItemListIncorrect)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idBD = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                )
            )

            headerSharedPreferencesDatasource.setIdOS(
                idOS = 1
            )

            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                list.count(),
                4
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemByIdOS -> IItemRepository.addAll -> IItemRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_item.idItem (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
            server.shutdown()
        }

    @Test
    fun check_return_correct_if_web_service_return_data_correct() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultItemList)
            )
            BaseUrlModuleTest.url = server.url("/").toString()
            hiltRule.inject()

            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idBD = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                )
            )

            headerSharedPreferencesDatasource.setIdOS(
                idOS = 1
            )

            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                list.count(),
                3
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            val modelList = itemDao.all()
            assertEquals(
                modelList.count(),
                2
            )
            assertEquals(
                modelList[0].idItem,
                1
            )
            assertEquals(
                modelList[0].seqItem,
                1
            )
            assertEquals(
                modelList[0].idOSItem,
                1
            )
            assertEquals(
                modelList[0].idPlantItem,
                1
            )
            assertEquals(
                modelList[0].idComponentItem,
                1
            )
            assertEquals(
                modelList[0].idServiceItem,
                1
            )
            assertEquals(
                modelList[1].idItem,
                2
            )
            assertEquals(
                modelList[1].seqItem,
                2
            )
            assertEquals(
                modelList[1].idOSItem,
                2
            )
            assertEquals(
                modelList[1].idPlantItem,
                2
            )
            assertEquals(
                modelList[1].idComponentItem,
                2
            )
            assertEquals(
                modelList[1].idServiceItem,
                2
            )
            server.shutdown()
        }
}