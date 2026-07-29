package com.antigravity.applocker.presentation.navigation

sealed class Routes(val route: String) {
    object Dashboard : Routes("dashboard")
    object Settings : Routes("settings")
    object SetupPin : Routes("setup_pin")
    object HiddenApps : Routes("hidden_apps")
    object FileSharing : Routes("file_sharing")
    object LockScreen : Routes("lock_screen/{packageName}") {
        fun createRoute(packageName: String) = "lock_screen/$packageName"
    }
}
