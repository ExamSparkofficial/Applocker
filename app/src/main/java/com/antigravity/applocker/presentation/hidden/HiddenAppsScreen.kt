package com.antigravity.applocker.presentation.hidden

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiddenAppsScreen(
    onNavigateBack: () -> Unit,
    viewModel: HiddenAppsViewModel = hiltViewModel()
) {
    val hiddenAppsState by viewModel.hiddenAppsState.collectAsState()
    val isDeviceOwner by viewModel.isDeviceOwner.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Secret Vault") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        var selectedTabIndex by remember { mutableStateOf(0) }
        val tabs = listOf("My Vault", "Manage")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            if (!isDeviceOwner) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(
                        text = "Device Owner Not Set! Apps will not actually hide until you run the ADB command from your PC.",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> {
                    // My Vault Tab
                    val hiddenApps = hiddenAppsState.filter { it.isHidden }
                    if (hiddenApps.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No hidden apps yet. Go to Manage to hide some.")
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(hiddenApps) { appItem ->
                                HiddenAppLaunchRow(
                                    appItem = appItem,
                                    onLaunch = { viewModel.launchHiddenApp(appItem.packageName) }
                                )
                            }
                        }
                    }
                }
                1 -> {
                    // Manage Tab
                    Text(
                        text = "Apps selected here will vanish from your phone's home screen.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(16.dp)
                    )
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(hiddenAppsState) { appItem ->
                            HiddenAppRow(
                                appItem = appItem,
                                onToggle = { isHidden ->
                                    viewModel.toggleAppHidden(appItem.packageName, isHidden)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HiddenAppLaunchRow(
    appItem: HiddenAppItem,
    onLaunch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLaunch() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            bitmap = appItem.icon.toBitmap().asImageBitmap(),
            contentDescription = appItem.appName,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = appItem.appName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun HiddenAppRow(
    appItem: HiddenAppItem,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            bitmap = appItem.icon.toBitmap().asImageBitmap(),
            contentDescription = appItem.appName,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = appItem.appName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = appItem.isHidden,
            onCheckedChange = { onToggle(it) }
        )
    }
}
