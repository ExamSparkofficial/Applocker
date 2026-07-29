package com.antigravity.applocker.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet {
        Spacer(Modifier.height(24.dp))
        Text(
            text = "SYGuard",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 28.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(24.dp))
        Divider()
        Spacer(Modifier.height(12.dp))

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Lock, contentDescription = null) },
            label = { Text("App Locker") },
            selected = currentRoute == Routes.Dashboard.route,
            onClick = {
                onNavigate(Routes.Dashboard.route)
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.VisibilityOff, contentDescription = null) },
            label = { Text("Hidden Apps") },
            selected = currentRoute == Routes.HiddenApps.route,
            onClick = {
                onNavigate(Routes.HiddenApps.route)
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.PhotoLibrary, contentDescription = null) },
            label = { Text("Encrypted Media") },
            selected = currentRoute == "media_gallery",
            onClick = {
                onNavigate("media_gallery")
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        Spacer(Modifier.weight(1f))
        
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Share, contentDescription = "File Sharing") },
            label = { Text("File Sharing") },
            selected = currentRoute == Routes.FileSharing.route,
            onClick = {
                onNavigate(Routes.FileSharing.route)
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = currentRoute == Routes.Settings.route,
            onClick = {
                onNavigate(Routes.Settings.route)
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        Spacer(Modifier.height(24.dp))
    }
}
