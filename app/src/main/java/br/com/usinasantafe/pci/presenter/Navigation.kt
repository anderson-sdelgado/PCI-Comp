package br.com.usinasantafe.pci.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.pci.presenter.Args.ID_ITEM_ARG
import br.com.usinasantafe.pci.presenter.Args.ID_PLANT_ARG
import br.com.usinasantafe.pci.presenter.Screens.COLAB_HEADER_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.CONFIG_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.INITIAL_MENU_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.OS_HEADER_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.PASSWORD_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.PLANT_LIST_NOTE_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.QUESTION_DESC_NOTE_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.QUESTION_LIST_NOTE_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.QUESTION_OBS_NOTE_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.QUESTION_RESP_NOTE_SCREEN
import br.com.usinasantafe.pci.presenter.Screens.SPLASH_SCREEN

object Screens {
    const val SPLASH_SCREEN = "splash"
    const val INITIAL_MENU_SCREEN = "initialMenuScreen"
    const val PASSWORD_SCREEN = "passwordScreen"
    const val CONFIG_SCREEN = "configScreen"
    const val COLAB_HEADER_SCREEN = "colabHeaderScreen"
    const val OS_HEADER_SCREEN = "osHeaderScreen"
    const val PLANT_LIST_NOTE_SCREEN = "plantListNoteScreen"
    const val QUESTION_LIST_NOTE_SCREEN = "questionListNoteScreen"
    const val QUESTION_DESC_NOTE_SCREEN = "questionDescNoteScreen"
    const val QUESTION_OBS_NOTE_SCREEN = "questionObsNoteScreen"
    const val QUESTION_RESP_NOTE_SCREEN = "questionRespNoteScreen"
}

object Args {
    const val ID_PLANT_ARG = "idPlant"
    const val ID_ITEM_ARG = "idItem"
}

object Routes {
    const val SPLASH_ROUTE = SPLASH_SCREEN
    const val INITIAL_MENU_ROUTE = INITIAL_MENU_SCREEN
    const val PASSWORD_ROUTE = PASSWORD_SCREEN
    const val CONFIG_ROUTE = CONFIG_SCREEN
    const val COLAB_HEADER_ROUTE = COLAB_HEADER_SCREEN
    const val OS_HEADER_ROUTE = OS_HEADER_SCREEN
    const val PLANT_LIST_NOTE_ROUTE = "$PLANT_LIST_NOTE_SCREEN/{$ID_PLANT_ARG}"
    const val QUESTION_LIST_NOTE_ROUTE = QUESTION_LIST_NOTE_SCREEN
    const val QUESTION_DESC_NOTE_ROUTE = QUESTION_DESC_NOTE_SCREEN
    const val QUESTION_OBS_NOTE_ROUTE = QUESTION_OBS_NOTE_SCREEN
    const val QUESTION_RESP_NOTE_ROUTE = "$QUESTION_RESP_NOTE_SCREEN/{$ID_PLANT_ARG}/{$ID_ITEM_ARG}"
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

    fun navigateToOSHeader() {
        navController.navigate(OS_HEADER_SCREEN)
    }

    ////////////////////////////////////////////////////////////////////

    ////////////////////////// note //////////////////////////////////

    fun navigateToPlantListNote() {
        navController.navigate(PLANT_LIST_NOTE_SCREEN)
    }

    fun navigateToQuestionListNote(
        idPlant: Int
    ) {
        navController.navigate("${QUESTION_LIST_NOTE_SCREEN}/${idPlant}")
    }

    fun navigateToQuestionDescNote(
        idPlant: Int,
        idItem: Int
    ) {
        navController.navigate("${QUESTION_DESC_NOTE_SCREEN}/${idPlant}/${idItem}")
    }

    fun navigateToQuestionObsNote(
        idPlant: Int,
        idItem: Int
    ) {
        navController.navigate("${QUESTION_OBS_NOTE_SCREEN}/${idPlant}/${idItem}")
    }

    fun navigateToQuestionRespNote(
        idPlant: Int,
        idItem: Int
    ) {
        navController.navigate("${QUESTION_RESP_NOTE_SCREEN}/${idPlant}/${idItem}")
    }

    ////////////////////////////////////////////////////////////////////

}
