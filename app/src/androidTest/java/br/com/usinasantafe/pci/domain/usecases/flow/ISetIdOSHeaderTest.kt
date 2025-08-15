package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.pci.external.sharedpreferences.datasource.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.sharedpreferences.HeaderSharedPreferencesModel
import br.com.usinasantafe.pci.utils.Status
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ISetIdOSHeaderTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ISetIdOSHeader

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderSharedPreferencesDatasource

    @Inject
    lateinit var headerDao: HeaderDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_header_shared_preferences() =
        runTest {
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdOSHeader -> ICheckListRepository.setIdOSHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_data_if_process_execute_successfully() =
        runTest {
            headerSharedPreferencesDatasource.save(
                HeaderSharedPreferencesModel(
                    idColab = 1,
                    idFactorySection = 1,
                    idOS = 1
                )
            )
            val result = usecase(2)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val list = headerDao.all()
            assertEquals(
                list.size,
                1
            )
            assertEquals(
                list[0].idColab,
                1
            )
            assertEquals(
                list[0].idFactorySection,
                1
            )
            assertEquals(
                list[0].idOS,
                2
            )
            assertEquals(
                list[0].status,
                Status.OPEN
            )
        }

}