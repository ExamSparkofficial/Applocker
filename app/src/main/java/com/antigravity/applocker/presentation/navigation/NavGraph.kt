package com.antigravity.applocker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.antigravity.applocker.presentation.dashboard.DashboardScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.Dashboard.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.Dashboard.route) {
            DashboardScreen(
                onNavigateToSettings = {
                    navController.navigate(Routes.Settings.route)
                }
            )
        }
        composable(Routes.Settings.route) {
            com.antigravity.applocker.presentation.settings.SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.LockScreen.route) { backStackEntry ->
            val packageName = backStackEntry.arguments?.getString("packageName")
            // LockScreen(packageName)
        }
    }
}
