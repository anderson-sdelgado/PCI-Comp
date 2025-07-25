package br.com.usinasantafe.pci.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pci.infra.models.sharedpreferences.HeaderSharedPreferencesModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IHeaderSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: IHeaderSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = IHeaderSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `setIdColabAndIdFactorySection - Check return data correct the Config SharedPreferences internal`() =
        runTest {
            val model = HeaderSharedPreferencesModel(
                idColab = 1,
                idFactorySection = 1
            )
            datasource.save(model)
            val resultBefore = datasource.get()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            val resultGetBefore = resultBefore.getOrNull()!!
            assertEquals(
                resultGetBefore.idColab,
                1
            )
            assertEquals(
                resultGetBefore.idFactorySection,
                1
            )
            val result = datasource.setIdColabAndIdFactorySection(
                idColab = 2,
                idFactorySection = 2
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultAfter = datasource.get()
            assertEquals(
                resultAfter.isSuccess,
                true
            )
            val resultGetAfter = resultAfter.getOrNull()!!
            assertEquals(
                resultGetAfter.idColab,
                2
            )
            assertEquals(
                resultGetAfter.idFactorySection,
                2
            )
        }

    @Test
    fun `getIdFactorySection - Check return failure if not have data in Config SharedPreferences internal`() =
        runTest {
            val result = datasource.getIdFactorySection()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderSharedPreferencesDatasource.getIdFactorySection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getIdFactorySection - Check return correct if function execute successfully`() =
        runTest {
            val data = HeaderSharedPreferencesModel(
                idColab = 1,
                idFactorySection = 2
            )
            datasource.save(data)
            val result = datasource.getIdFactorySection()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                2
            )
        }
}