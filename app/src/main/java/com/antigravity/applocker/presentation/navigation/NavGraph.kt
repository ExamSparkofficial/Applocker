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
import com.antigravity.applocker.presentation.settings.SettingsScreen

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    securityPreferences: SecurityPreferences = androidx.compose.ui.platform.LocalContext.current.let {
        dagger.hilt.android.EntryPointAccessors.fromApplication(it, com.antigravity.applocker.di.SecurityEntryPoint::class.java).securityPreferences()
    },
    hashUtil: HashUtil = androidx.compose.ui.platform.LocalContext.current.let {
        dagger.hilt.android.EntryPointAccessors.fromApplication(it, com.antigravity.applocker.di.SecurityEntryPoint::class.java).hashUtil()
    },
    initialRoute: String? = null
) {
    val startDestination = initialRoute ?: if (securityPreferences.getHashedPin().isNullOrEmpty()) Routes.SetupPin.route else Routes.Dashboard.route
    
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Don't allow drawer on SetupPin or LockScreen
    val gesturesEnabled = currentRoute != Routes.SetupPin.route && currentRoute != Routes.LockScreen.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        drawerContent = {
            DrawerContent(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.Dashboard.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
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
                    },
                    onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    }
                )
            }
            composable(Routes.Settings.route) {
                SettingsScreen(
                    onNavigateBack = { navController.navigateUp() },
                    onNavigateToSetupPin = {
                        navController.navigate(Routes.SetupPin.route)
                    },
                    onNavigateToHiddenVault = {
                        navController.navigate(Routes.HiddenApps.route)
                    }
                )
            }
            composable(Routes.HiddenApps.route) {
                com.antigravity.applocker.presentation.hidden.HiddenAppsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("media_gallery") {
                com.antigravity.applocker.presentation.media.MediaGalleryScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToViewer = { mediaId ->
                        navController.navigate("media_viewer/$mediaId")
                    }
                )
            }
            composable("media_viewer/{mediaId}") { backStackEntry ->
                val mediaId = backStackEntry.arguments?.getString("mediaId") ?: ""
                com.antigravity.applocker.presentation.media.MediaViewerScreen(
                    mediaId = mediaId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(Routes.LockScreen.route) { backStackEntry ->
                val packageName = backStackEntry.arguments?.getString("packageName")
                // LockScreen(packageName)
            }
        }
    }
}
