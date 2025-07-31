package br.com.usinasantafe.pci.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.usinasantafe.pci.presenter.Args.ID_PLANT_ARG
import br.com.usinasantafe.pci.presenter.Routes.COLAB_HEADER_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.CONFIG_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.INITIAL_MENU_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.OS_HEADER_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.PASSWORD_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.PLANT_LIST_NOTE_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.QUESTION_DESC_NOTE_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.QUESTION_LIST_NOTE_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.QUESTION_OBS_NOTE_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.QUESTION_RESP_NOTE_ROUTE
import br.com.usinasantafe.pci.presenter.Routes.SPLASH_ROUTE
import br.com.usinasantafe.pci.presenter.view.configuration.config.ConfigScreen
import br.com.usinasantafe.pci.presenter.view.configuration.initial.InitialMenuScreen
import br.com.usinasantafe.pci.presenter.view.configuration.password.PasswordScreen
import br.com.usinasantafe.pci.presenter.view.header.colab.ColabHeaderScreen
import br.com.usinasantafe.pci.presenter.view.header.oslist.OSListHeaderScreen
import br.com.usinasantafe.pci.presenter.view.note.plantlist.PlantListNoteScreen
import br.com.usinasantafe.pci.presenter.view.note.questionlist.QuestionListNoteScreen
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
                    navActions.navigateToOSHeader()
                }
            )
        }

        composable(OS_HEADER_ROUTE) {
            OSListHeaderScreen(
                onNavColab = {
                    navActions.navigateToColabHeader()
                },
                onNavPlantList = {
                    navActions.navigateToPlantListNote()
                },
            )
        }

        ////////////////////////////////////////////////////////////////////

        ////////////////////////// Note //////////////////////////////////

        composable(PLANT_LIST_NOTE_ROUTE) {
            PlantListNoteScreen(
                onNavOSList = {
                    navActions.navigateToOSHeader()
                },
                onNavQuestionList = {
                    navActions.navigateToQuestionListNote(
                        it
                    )
                }
            )
        }

        composable(
            QUESTION_LIST_NOTE_ROUTE,
            arguments = listOf(
                navArgument(ID_PLANT_ARG) {
                    type = NavType.IntType
                }
            )
        ) {
            QuestionListNoteScreen(
                onNavPlantList = {}
            )
        }

        composable(QUESTION_DESC_NOTE_ROUTE) {

        }

        composable(QUESTION_OBS_NOTE_ROUTE) {

        }

        composable(QUESTION_RESP_NOTE_ROUTE) {

        }


        ////////////////////////////////////////////////////////////////////

    }
}