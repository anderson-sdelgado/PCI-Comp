package br.com.usinasantafe.pci.infra.repositories.variable

import br.com.usinasantafe.pci.domain.entities.variable.Config
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.pci.infra.models.retrofit.variable.ConfigRetrofitModelOutput
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.utils.FlagUpdate
import br.com.usinasantafe.pci.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.text.get

class IConfigRepositoryTest {

    private val configSharedPreferencesDatasource = mock<ConfigSharedPreferencesDatasource>()
    private val configRetrofitDatasource = mock<ConfigRetrofitDatasource>()
    private val repository = IConfigRepository(
        configSharedPreferencesDatasource = configSharedPreferencesDatasource,
        configRetrofitDatasource = configRetrofitDatasource
    )

    @Test
    fun `hasConfig - Check return failure if have error in ConfigSharedPreferencesDatasource has`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.has()
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.has",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasConfig()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.hasConfig -> IConfigSharedPreferencesDatasource.has"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasConfig - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.has()
            ).thenReturn(
                Result.success(
                    false
                )
            )
            val result = repository.hasConfig()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `getPassword - Check return failure if have error in ConfigSharedPreferencesDatasource getPassword`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getPassword()
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.getPassword",
                    "-",
                    Exception()
                )
            )
            val result = repository.getPassword()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.getPassword -> IConfigSharedPreferencesDatasource.getPassword"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getPassword - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getPassword()
            ).thenReturn(
                Result.success("12345")
            )
            val result = repository.getPassword()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "12345"
            )
        }

    @Test
    fun `get - Check return failure if have error in ConfigSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.get",
                    "-",
                    Exception()
                )
            )
            val result = repository.get()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.get -> IConfigSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `get - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    ConfigSharedPreferencesModel(
                        statusSend = StatusSend.SENT
                    )
                )
            )
            val result = repository.get()
            assertEquals(
                result.isSuccess,
                true
            )
            val sharedPreferencesModel = result.getOrNull()!!
            assertEquals(
                sharedPreferencesModel.statusSend,
                StatusSend.SENT
            )
        }


    @Test
    fun `save - Check return failure if have error in ConfigSharedPreferencesDatasource save`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.save(
                    ConfigSharedPreferencesModel(
                        number = 16997417840,
                        password = "12345",
                        version = "1.00",
                        idBD = 1
                    )
                )
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.save",
                    "-",
                    Exception()
                )
            )
            val result = repository.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "1.00",
                    idBD = 1
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.save -> IConfigSharedPreferencesDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `save - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.save(
                    ConfigSharedPreferencesModel(
                        number = 16997417840,
                        password = "12345",
                        version = "1.00",
                        idBD = 1
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "1.00",
                    idBD = 1
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
        }

    @Test
    fun `send - Check return failure if have error in ConfigRetrofitDatasource recoverToken`() =
        runTest {
            val retrofitModelOutput = ConfigRetrofitModelOutput(
                number = 16997417840,
                version = "1.00"
            )
            val entity = Config(
                number = 16997417840,
                version = "1.00"
            )
            whenever(
                configRetrofitDatasource.recoverToken(
                    retrofitModelOutput = retrofitModelOutput
                )
            ).thenReturn(
                resultFailure(
                    "IConfigRetrofitDatasource.recoverToken",
                    "-",
                    Exception()
                )
            )
            val result = repository.send(entity)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.send -> IConfigRetrofitDatasource.recoverToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return correct if function execute successfully`() =
        runTest {
            val retrofitModelOutput = ConfigRetrofitModelOutput(
                number = 16997417840,
                version = "1.00"
            )
            val retrofitModelInput = ConfigRetrofitModelInput(
                idBD = 1
            )
            val entity = Config(
                number = 16997417840,
                version = "1.00"
            )
            whenever(
                configRetrofitDatasource.recoverToken(
                    retrofitModelOutput = retrofitModelOutput
                )
            ).thenReturn(
                Result.success(retrofitModelInput)
            )
            val result = repository.send(entity)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Config(
                    idBD = 1
                )
            )
        }

    @Test
    fun `setFlagUpdate - Check return failure if have failure in execution ConfigSharedPreferencesDatasource setFlagUpdate`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.setFlagUpdate(FlagUpdate.UPDATED)
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.setFlagUpdate",
                    "-",
                    Exception()
                )
            )
            val result = repository.setFlagUpdate(FlagUpdate.UPDATED)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.setFlagUpdate -> IConfigSharedPreferencesDatasource.setFlagUpdate"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setFlagUpdate - Check return true is execution successfully`() = runTest {
        whenever(
            configSharedPreferencesDatasource.setFlagUpdate(FlagUpdate.UPDATED)
        ).thenReturn(
            Result.success(true)
        )
        val result = repository.setFlagUpdate(FlagUpdate.UPDATED)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

}