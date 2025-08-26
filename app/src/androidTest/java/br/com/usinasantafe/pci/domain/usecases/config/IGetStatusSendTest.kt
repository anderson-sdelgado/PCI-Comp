package br.com.usinasantafe.pci.domain.usecases.config

import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.room.variable.RespRoomModel
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.utils.OptionResp
import br.com.usinasantafe.pci.utils.StatusSend
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class IGetStatusSendTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: GetStatusSend

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var respDao: RespDao

    @Before
    fun init() {
        hiltRule.inject()
    }

//    @Test
//    fun check_return_status_started_if_not_have_data_in_config_internal() =
//        runTest {
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                StatusSend.STARTED
//            )
//        }
//
//    @Test
//    fun check_return_status_sent_if_not_have_data_to_send() =
//        runTest {
//            initialRegister(1)
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                StatusSend.SENT
//            )
//        }
//
//    @Test
//    fun check_return_status_send_if_have_data_to_send() =
//        runTest {
//            initialRegister(2)
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                StatusSend.SEND
//            )
//        }

    private suspend fun initialRegister(level: Int) {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                idBD = 1,
                number = 16997417840,
                version = "1.0",
                password = "12345",
            )
        )

        if (level == 1) return

        respDao.insert(
            RespRoomModel(
                id = 1,
                idHeader = 1,
                idItem = 1,
                idPlant = 1,
                option = OptionResp.NON_CONFORMING,
                obs = "OK",
                idServ = null,
                statusSend = StatusSend.SEND
            )
        )

        if (level == 2) return

    }

    }