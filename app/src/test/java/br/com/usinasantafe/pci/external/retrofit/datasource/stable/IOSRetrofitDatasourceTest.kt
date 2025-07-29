package br.com.usinasantafe.pci.external.retrofit.datasource.stable

import br.com.usinasantafe.pci.di.provider.PersistenceModuleTest.provideRetrofitTest
import br.com.usinasantafe.pci.external.retrofit.api.stable.OSApi
import br.com.usinasantafe.pci.infra.models.retrofit.stable.OSRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IOSRetrofitDatasourceTest {

    private val resultOSList = """
        [
            {"idOS":1,"nroOS":1,"idPlantOS":1,"qtdDayOS":1,"descPeriodOS":"DIARIO","idFactorySectionOS":1},
            {"idOS":2,"nroOS":2,"idPlantOS":2,"qtdDayOS":2,"descPeriodOS":"SEMANAL","idFactorySectionOS":1}
        ]
    """.trimIndent()

    @Test
    fun `listByIdFactorySection - Check return failure if token is invalid`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service)
            val result = datasource.listByIdFactorySection(
                token = "TOKEN",
                idFactorySection = 1
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IOSRetrofitDatasource.listByIdFactorySection",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$",
                result.exceptionOrNull()!!.cause.toString()
            )
            server.shutdown()
        }

    @Test
    fun `listByIdFactorySection - Check return failure if have Error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service)
            val result = datasource.listByIdFactorySection(
                token = "TOKEN",
                idFactorySection = 1
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IOSRetrofitDatasource.listByIdFactorySection",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                NullPointerException().toString(),
                result.exceptionOrNull()!!.cause.toString()
            )
            server.shutdown()
        }


    @Test
    fun `listByIdFactorySection - Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultOSList)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service)
            val result = datasource.listByIdFactorySection(
                token = "TOKEN",
                idFactorySection = 1
            )
            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        OSRetrofitModel(
                            idOS = 1,
                            nroOS = 1,
                            idPlantOS = 1,
                            qtdDayOS = 1,
                            descPeriodOS = "DIARIO",
                            idFactorySectionOS = 1
                        ),
                        OSRetrofitModel(
                            idOS = 2,
                            nroOS = 2,
                            idPlantOS = 2,
                            qtdDayOS = 2,
                            descPeriodOS = "SEMANAL",
                            idFactorySectionOS = 1
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }

}