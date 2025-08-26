package br.com.usinasantafe.pci.external.retrofit.datasource.variable

import br.com.usinasantafe.pci.di.provider.PersistenceModuleTest.provideRetrofitTest
import br.com.usinasantafe.pci.external.retrofit.api.variable.CheckListApi
import br.com.usinasantafe.pci.infra.models.retrofit.variable.HeaderRetrofitModelOutput
import br.com.usinasantafe.pci.infra.models.retrofit.variable.RespRetrofitModelOutput
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckListRetrofitDatasourceTest {

    val retrofitModelOutputList = listOf(
        HeaderRetrofitModelOutput(
            id = 1,
            idColab = 1,
            idFactorySection = 1,
            idOS = 1,
            dateHour = "10/10/2023 10:10",
            respList = listOf(
                RespRetrofitModelOutput(
                    id = 1,
                    idHeader = 1,
                    idItem = 1,
                    idPlant = 1,
                    option = 1,
                    obs = "OK",
                    idServ = null,
                    dateHour = "10/10/2023 10:10",
                    status = 1
                )
            ),
            number = 1,
            idServ = null,
            status = 1
        )
    )

    @Test
    fun `send - Check return failure if have error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setResponseCode(404))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(CheckListApi::class.java)
            val dataSource = ICheckListRetrofitDatasource(service)
            val result = dataSource.send(
                "token",
                retrofitModelOutputList
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
            server.shutdown()
        }

    @Test
    fun `send - Check return failure if have failure Connection`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("Failure Connection BD"))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(CheckListApi::class.java)
            val dataSource = ICheckListRetrofitDatasource(service)
            val result = dataSource.send(
                "token",
                retrofitModelOutputList
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$"
            )
            server.shutdown()
        }

    @Test
    fun `send - Check return failure if sent data incorrect`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""{"id":1a}"""))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(CheckListApi::class.java)
            val dataSource = ICheckListRetrofitDatasource(service)
            val result = dataSource.send(
                "token",
                retrofitModelOutputList
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
            server.shutdown()
        }

    @Test
    fun `send - Check return correct if function execute successfully`() =
        runTest {
            val response = """
                [
                    {
                        "id": 1,
                        "idServ": 1,
                        "respList": [
                            {
                                "id": 1,
                                "idServ": 1
                            }
                        ]
                    }
                ]
            """.trimIndent()
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody(response))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(CheckListApi::class.java)
            val dataSource = ICheckListRetrofitDatasource(service)
            val result = dataSource.send(
                "token",
                retrofitModelOutputList
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val headerRetrofitModelInputList = result.getOrNull()!!
            assertEquals(
                headerRetrofitModelInputList.size,
                1
            )
            val headerRetrofitModelInput = headerRetrofitModelInputList[0]
            assertEquals(
                headerRetrofitModelInput.id,
                1
            )
            assertEquals(
                headerRetrofitModelInput.idServ,
                1
            )
            val respRetrofitModelInputList = headerRetrofitModelInput.respList
            assertEquals(
                respRetrofitModelInputList.size,
                1
            )
            val respRetrofitModelInput = respRetrofitModelInputList[0]
            assertEquals(
                respRetrofitModelInput.id,
                1
            )
            assertEquals(
                respRetrofitModelInput.idServ,
                1
            )
            server.shutdown()
        }
}