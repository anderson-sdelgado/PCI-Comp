package br.com.usinasantafe.pci.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.usinasantafe.pci.presenter.Routes.COLAB_HEADER_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.CONFIG_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.INITIAL_MENU_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.OS_HEADER_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.PASSWORD_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.SPLASH_ROUTE
import br.com.usinasantafe.pci.presenter.view.configuration.config.ConfigScreen
import br.com.usinasantafe.pci.presenter.view.configuration.initial.InitialMenuScreen
import br.com.usinasantafe.pci.presenter.view.configuration.password.PasswordScreen
import br.com.usinasantafe.pci.presenter.view.header.colab.ColabHeaderScreen
import br.com.usinasantafe.pci.presenter.view.header.oslist.OSListHeaderScreen
import br.com.usinasantafe.pci.presenter.view.splash.SplashScreen


@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = SPLASH_ROUTE,
    navActions: NavigationActions = remember(navHostController) {
        NavigationActions(navHostController)
    }
) {

    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        ///////////////////////// Splash //////////////////////////////////

        composable(SPLASH_ROUTE) {
            SplashScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

        ///////////////////////// Config //////////////////////////////////

        composable(INITIAL_MENU_ROUTE) {
            InitialMenuScreen(
                onNavPassword = {
                    navActions.navigateToPassword()
                },
                onNavColab = {
                    navActions.navigateToColabHeader()
                }
            )
        }

        composable(PASSWORD_ROUTE) {
            PasswordScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                },
                onNavConfig = {
                    navActions.navigateToConfig()
                }
            )
        }

        composable(CONFIG_ROUTE)  {
            ConfigScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

        ////////////////////////// Header //////////////////////////////////

        composable(COLAB_HEADER_ROUTE) {
            ColabHeaderScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                },
                onNavOS = {
                    navActions.navigateToOS()
                }
            )
        }

        composable(OS_HEADER_ROUTE) {
            OSListHeaderScreen()
        }

        ////////////////////////////////////////////////////////////////////

    }
}