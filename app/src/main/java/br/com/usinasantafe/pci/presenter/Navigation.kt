package br.com.usinasantafe.pci.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.pci.presenter.Screens.COLAB_HEADER_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.CONFIG_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.INITIAL_MENU_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.OS_HEADER_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.PASSWORD_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.SPLASH_SCREEN

object Screens {
    const val SPLASH_SCREEN = "splash"
    const val INITIAL_MENU_SCREEN = "initialMenuScreen"
    const val PASSWORD_SCREEN = "passwordScreen"
    const val CONFIG_SCREEN = "configScreen"
    const val COLAB_HEADER_SCREEN = "colabHeaderScreen"
    const val OS_HEADER_SCREEN = "osHeaderScreen"
}

object Args {

}

object Routes {
    const val SPLASH_ROUTE = SPLASH_SCREEN
    const val INITIAL_MENU_ROUTE = INITIAL_MENU_SCREEN
    const val PASSWORD_ROUTE = PASSWORD_SCREEN
    const val CONFIG_ROUTE = CONFIG_SCREEN
    const val COLAB_HEADER_ROUTE = COLAB_HEADER_SCREEN
    const val OS_HEADER_ROUTE = OS_HEADER_SCREEN
}

class NavigationActions(private val navController: NavHostController) {

    ///////////////////////// Splash //////////////////////////////////

    fun navigateToSplash() {
        navController.navigate(SPLASH_SCREEN)
    }

    ////////////////////////////////////////////////////////////////////

    ///////////////////////// Config //////////////////////////////////

    fun navigateToPassword() {
        navController.navigate(PASSWORD_SCREEN)
    }

    fun navigateToInitialMenu() {
        navController.navigate(INITIAL_MENU_SCREEN)
    }

    fun navigateToConfig() {
        navController.navigate(CONFIG_SCREEN)
    }

    ////////////////////////////////////////////////////////////////////

    ////////////////////////// Header //////////////////////////////////

    fun navigateToColabHeader() {
        navController.navigate(COLAB_HEADER_SCREEN)
    }

    fun navigateToOS() {
        navController.navigate(OS_HEADER_SCREEN)
    }

    ////////////////////////////////////////////////////////////////////

}
