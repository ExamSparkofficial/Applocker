package com.antigravity.applocker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.antigravity.applocker.presentation.dashboard.DashboardScreen
import com.antigravity.applocker.data.security.SecurityPreferences
import com.antigravity.applocker.util.HashUtil
import com.antigravity.applocker.presentation.settings.SetupPinScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    securityPreferences: SecurityPreferences = androidx.compose.ui.platform.LocalContext.current.let {
        dagger.hilt.android.EntryPointAccessors.fromApplication(it, com.antigravity.applocker.di.SecurityEntryPoint::class.java).securityPreferences()
    },
    hashUtil: HashUtil = androidx.compose.ui.platform.LocalContext.current.let {
        dagger.hilt.android.EntryPointAccessors.fromApplication(it, com.antigravity.applocker.di.SecurityEntryPoint::class.java).hashUtil()
    }
) {
    val startDestination = if (securityPreferences.getHashedPin().isNullOrEmpty()) Routes.SetupPin.route else Routes.Dashboard.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.SetupPin.route) {
            SetupPinScreen(
                onPinSet = {
                    navController.navigate(Routes.Dashboard.route) {
                        popUpTo(Routes.SetupPin.route) { inclusive = true }
                    }
                },
                securityPreferences = securityPreferences,
                hashUtil = hashUtil
            )
        }
        composable(Routes.Dashboard.route) {
            DashboardScreen(
                onNavigateToSettings = {
                    navController.navigate(Routes.Settings.route)
                }
            )
        }
        composable(Routes.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSetupPin = { navController.navigate(Routes.SetupPin.route) }
            )
        }
        composable(Routes.LockScreen.route) { backStackEntry ->
            val packageName = backStackEntry.arguments?.getString("packageName")
            // LockScreen(packageName)
        }
    }
}
