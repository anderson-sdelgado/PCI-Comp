package br.com.usinasantafe.pci.external.retrofit.datasource.stable

import br.com.usinasantafe.pci.di.provider.PersistenceModuleTest.provideRetrofitTest
import br.com.usinasantafe.pci.external.retrofit.api.stable.PlantApi
import br.com.usinasantafe.pci.infra.models.retrofit.stable.PlantRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IPlantRetrofitDatasourceTest {

    private val resultPlantList = """
        [
            {"idPlant":1,"codPlant":"01","descPlant":"PLANTA 01"},
            {"idPlant":2,"codPlant":"02","descPlant":"PLANTA 02"}
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
            val service = retrofit.create(PlantApi::class.java)
            val datasource = IPlantRetrofitDatasource(service)
            val result = datasource.listByIdFactorySection(
                token = "TOKEN",
                idFactorySection = 1
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IPlantRetrofitDatasource.listByIdFactorySection",
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
            val service = retrofit.create(PlantApi::class.java)
            val datasource = IPlantRetrofitDatasource(service)
            val result = datasource.listByIdFactorySection(
                token = "TOKEN",
                idFactorySection = 1
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IPlantRetrofitDatasource.listByIdFactorySection",
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
                MockResponse().setBody(resultPlantList)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(PlantApi::class.java)
            val datasource = IPlantRetrofitDatasource(service)
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
                        PlantRetrofitModel(
                            idPlant = 1,
                            codPlant = "01",
                            descPlant = "PLANTA 01",
                            idFactorySectionPlant = 1
                        ),
                        PlantRetrofitModel(
                            idPlant = 2,
                            codPlant = "02",
                            descPlant = "PLANTA 02",
                            idFactorySectionPlant = 1
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }

}