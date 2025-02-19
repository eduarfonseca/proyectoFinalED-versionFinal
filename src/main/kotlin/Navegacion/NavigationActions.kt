package Navegacion

import ViewModels.AppViewModel
import androidx.navigation.NavHostController


class NavigationActions(
    private val navController: NavHostController,
    private val viewModel: AppViewModel
) {
    fun navigateTo(screen: Screen) {
        viewModel.navigateTo(screen)
        navController.navigate(screen.route) {
            navController.graph.startDestinationRoute?.let {
                popUpTo(it) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}