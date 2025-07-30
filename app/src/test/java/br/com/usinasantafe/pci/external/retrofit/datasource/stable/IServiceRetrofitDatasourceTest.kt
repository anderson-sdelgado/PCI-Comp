package br.com.usinasantafe.pci.external.retrofit.datasource.stable

import br.com.usinasantafe.pci.di.provider.PersistenceModuleTest.provideRetrofitTest
import br.com.usinasantafe.pci.external.retrofit.api.stable.ServiceApi
import br.com.usinasantafe.pci.infra.models.retrofit.stable.ServiceRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IServiceRetrofitDatasourceTest {

    private val resultServiceList = """
        [
          {"idService":1,"codService":1,"descService":"Teste 1"},
          {"idService":2,"codService":2,"descService":"Teste 2"}
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
            val service = retrofit.create(ServiceApi::class.java)
            val datasource = IServiceRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IServiceRetrofitDatasource.listAll",
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
            val service = retrofit.create(ServiceApi::class.java)
            val datasource = IServiceRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IServiceRetrofitDatasource.listAll",
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
                MockResponse().setBody(resultServiceList)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ServiceApi::class.java)
            val datasource = IServiceRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        ServiceRetrofitModel(
                            idService = 1,
                            codService = 1,
                            descService = "Teste 1"
                        ),
                        ServiceRetrofitModel(
                            idService = 2,
                            codService = 2,
                            descService = "Teste 2"
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }

}