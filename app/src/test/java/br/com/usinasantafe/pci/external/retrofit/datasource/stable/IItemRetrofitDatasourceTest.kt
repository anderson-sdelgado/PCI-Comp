package br.com.usinasantafe.pci.external.retrofit.datasource.stable

import br.com.usinasantafe.pci.di.provider.PersistenceModuleTest.provideRetrofitTest
import br.com.usinasantafe.pci.external.retrofit.api.stable.ItemApi
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ItemRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IItemRetrofitDatasourceTest {

    private val resultItemList = """
        [
            {"idItem":1,"seqItem":1,"idOSItem":1,"idPlantItem":1,"idComponentItem":1,"idServiceItem":1},
            {"idItem":2,"seqItem":2,"idOSItem":2,"idPlantItem":2,"idComponentItem":2,"idServiceItem":2}
        ]
    """.trimIndent()

    @Test
    fun `listByIdOS - Check return failure if token is invalid`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ItemApi::class.java)
            val datasource = IItemRetrofitDatasource(service)
            val result = datasource.listByIdOS(
                token = "TOKEN",
                idOS = 1
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IItemRetrofitDatasource.listByIdOS",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$",
                result.exceptionOrNull()!!.cause.toString()
            )
            server.shutdown()
        }

    @Test
    fun `listByIdOS - Check return failure if have Error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ItemApi::class.java)
            val datasource = IItemRetrofitDatasource(service)
            val result = datasource.listByIdOS(
                token = "TOKEN",
                idOS = 1
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemRetrofitDatasource.listByIdOS",
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
            server.shutdown()
        }


    @Test
    fun `listByIdOS - Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultItemList)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ItemApi::class.java)
            val datasource = IItemRetrofitDatasource(service)
            val result = datasource.listByIdOS(
                token = "TOKEN",
                idOS = 1
            )
            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        ItemRetrofitModel(
                            idItem = 1,
                            seqItem = 1,
                            idOSItem = 1,
                            idPlantItem = 1,
                            idComponentItem = 1,
                            idServiceItem = 1
                        ),
                        ItemRetrofitModel(
                            idItem = 2,
                            seqItem = 2,
                            idOSItem = 2,
                            idPlantItem = 2,
                            idComponentItem = 2,
                            idServiceItem = 2
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }

}