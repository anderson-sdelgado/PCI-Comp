package br.com.usinasantafe.pci.external.retrofit.datasource.variable

import br.com.usinasantafe.pci.di.provider.PersistenceModuleTest
import br.com.usinasantafe.pci.external.retrofit.api.variable.ConfigApi
import br.com.usinasantafe.pci.infra.models.retrofit.variable.ConfigRetrofitModelOutput
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test

class IConfigRetrofitDatasourceTest {

    @Test
    fun `recoverToken - Check return correct if function execute successfully`() =
        runTest {
            val retrofitModelOutput = ConfigRetrofitModelOutput(
                number = 16997417840,
                version = "1.00"
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""{"idBD":1}"""))
            val retrofit = PersistenceModuleTest.provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(ConfigApi::class.java)
            val dataSource = IConfigRetrofitDatasource(service)
            val result = dataSource.recoverToken(retrofitModelOutput)
            Assert.assertEquals(
                result.isSuccess,
                true
            )
            val retrofitModelInput = result.getOrNull()
            Assert.assertEquals(
                retrofitModelInput?.idBD,
                1
            )
            server.shutdown()
        }

    @Test
    fun `recoverToken - Check return failure if have error 404`() =
        runTest {
            val retrofitModelOutput = ConfigRetrofitModelOutput(
                number = 16997417840,
                version = "1.00"
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setResponseCode(404))
            val retrofit = PersistenceModuleTest.provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(ConfigApi::class.java)
            val dataSource = IConfigRetrofitDatasource(service)
            val result = dataSource.recoverToken(retrofitModelOutput)
            Assert.assertEquals(
                result.isFailure,
                true
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRetrofitDatasource.recoverToken"
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
            server.shutdown()
        }

    @Test
    fun `recoverToken - Check return failure if have failure Connection`() =
        runTest {
            val retrofitModelOutput = ConfigRetrofitModelOutput(
                number = 16997417840,
                version = "1.00"
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("Failure Connection BD"))
            val retrofit = PersistenceModuleTest.provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(ConfigApi::class.java)
            val dataSource = IConfigRetrofitDatasource(service)
            val result = dataSource.recoverToken(retrofitModelOutput)
            Assert.assertEquals(
                result.isFailure,
                true
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRetrofitDatasource.recoverToken"
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$"
            )
            server.shutdown()
        }

    @Test
    fun `recoverToken - Check return failure if sent data incorrect`() =
        runTest {
            val retrofitModelOutput = ConfigRetrofitModelOutput(
                number = 16997417840,
                version = "1.00"
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""{"idBD":1a}"""))
            val retrofit = PersistenceModuleTest.provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(ConfigApi::class.java)
            val dataSource = IConfigRetrofitDatasource(service)
            val result = dataSource.recoverToken(retrofitModelOutput)
            Assert.assertEquals(
                result.isFailure,
                true
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRetrofitDatasource.recoverToken"
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 9 path \$.idBD"
            )
            server.shutdown()
        }

}