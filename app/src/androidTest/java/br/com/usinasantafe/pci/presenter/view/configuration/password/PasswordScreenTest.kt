package br.com.usinasantafe.pci.presenter.view.configuration.password

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pci.HiltTestActivity
import br.com.usinasantafe.pci.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pci.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pci.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class PasswordScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Test
    fun check_open_screen() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_msg_if_password_invalid() =
        runTest {

            hiltRule.inject()

            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    password = "123456"
                )
            )

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("OK")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

        }

    private fun setContent() {
        composeTestRule.setContent {
            PasswordScreen (
                onNavInitialMenu = {},
                onNavConfig = {}
            )
        }
    }
}