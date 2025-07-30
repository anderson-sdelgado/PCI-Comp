package br.com.usinasantafe.pci.external.retrofit.datasource.stable

import br.com.usinasantafe.pci.di.provider.PersistenceModuleTest.provideRetrofitTest
import br.com.usinasantafe.pci.external.retrofit.api.stable.ComponentApi
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ComponentRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IComponentRetrofitDatasourceTest {

    private val resultComponentList = """
        [
          {"idComponent":1,"codComponent":1,"descComponent":"Teste 1"},
          {"idComponent":2,"codComponent":2,"descComponent":"Teste 2"}
        ]
    """.trimIndent()

    @Test
    fun `listAll - Check return failure if token is invalid`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ComponentApi::class.java)
            val datasource = IComponentRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IComponentRetrofitDatasource.listAll",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$",
                result.exceptionOrNull()!!.cause.toString()
            )
            server.shutdown()
        }

    @Test
    fun `listAll - Check return failure if have Error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ComponentApi::class.java)
            val datasource = IComponentRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IComponentRetrofitDatasource.listAll",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                NullPointerException().toString(),
                result.exceptionOrNull()!!.cause.toString()
            )
            server.shutdown()
        }


    @Test
    fun `listAll - Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultComponentList)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ComponentApi::class.java)
            val datasource = IComponentRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        ComponentRetrofitModel(
                            idComponent = 1,
                            codComponent = 1,
                            descComponent = "Teste 1"
                        ),
                        ComponentRetrofitModel(
                            idComponent = 2,
                            codComponent = 2,
                            descComponent = "Teste 2"
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }

}