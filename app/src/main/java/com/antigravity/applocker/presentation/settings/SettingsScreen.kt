package com.antigravity.applocker.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.antigravity.applocker.lockengine.AppLockerDeviceAdminReceiver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSetupPin: () -> Unit,
    onNavigateToHiddenVault: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    val componentName = ComponentName(context, AppLockerDeviceAdminReceiver::class.java)
    
    var isAdminActive by androidx.compose.runtime.remember { mutableStateOf(devicePolicyManager.isAdminActive(componentName)) }
    
    val lifecycleOwner = LocalLifecycleOwner.current
    androidx.compose.runtime.DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isAdminActive = devicePolicyManager.isAdminActive(componentName)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Display",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            
            item {
                SettingsSwitch(
                    title = "Dark Mode",
                    subtitle = "Use dark theme across the app",
                    checked = uiState.isDarkMode,
                    onCheckedChange = viewModel::toggleDarkMode
                )
            }

            item {
                SettingsSwitch(
                    title = "AMOLED Pitch Black",
                    subtitle = "Use true black for OLED screens (Requires Dark Mode)",
                    checked = uiState.isAmoledMode,
                    onCheckedChange = viewModel::toggleAmoledMode,
                    enabled = uiState.isDarkMode
                )
            }
            
            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                Text(
                    text = "Security",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            
            item {
                SettingsItem(
                    title = "Change PIN / Pattern",
                    subtitle = "Update your primary lock method",
                    onClick = onNavigateToSetupPin
                )
            }
            
            item {
                SettingsItem(
                    title = "Manage Hidden Vault",
                    subtitle = "Hide apps from your home screen (Requires Device Owner)",
                    onClick = onNavigateToHiddenVault
                )
            }
            
            item {
                SettingsSwitch(
                    title = "Uninstall Protection",
                    subtitle = "Prevent the app from being uninstalled by intruders",
                    checked = isAdminActive,
                    onCheckedChange = { active -> 
                        if (active && !isAdminActive) {
                            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                                putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
                                putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Activate to prevent uninstalling AppLocker")
                            }
                            context.startActivity(intent)
                        } else if (!active && isAdminActive) {
                            devicePolicyManager.removeActiveAdmin(componentName)
                            isAdminActive = false
                        }
                    }
                )
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                Text(
                    text = "Customization",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                val securityPreferences = androidx.compose.runtime.remember { 
                    dagger.hilt.android.EntryPointAccessors.fromApplication(context.applicationContext, com.antigravity.applocker.di.SecurityEntryPoint::class.java).securityPreferences() 
                }
                var wallpaperUri by androidx.compose.runtime.remember { mutableStateOf(securityPreferences.getWallpaperUri()) }
                
                val launcher = androidx.activity.compose.rememberLauncherForActivityResult(
                    androidx.activity.result.contract.ActivityResultContracts.GetContent()
                ) { uri ->
                    if (uri != null) {
                        context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        securityPreferences.saveWallpaperUri(uri.toString())
                        wallpaperUri = uri.toString()
                    }
                }
                
                SettingsItem(
                    title = "Set Lock Screen Wallpaper",
                    subtitle = if (wallpaperUri != null) "Custom wallpaper is set" else "Choose an image from gallery",
                    onClick = { launcher.launch("image/*") }
                )
            }
            
            item {
                SettingsSwitch(
                    title = "Intruder Selfie",
                    subtitle = "Take a photo after 3 failed attempts",
                    checked = uiState.intruderSelfieEnabled,
                    onCheckedChange = { /* viewModel.toggleIntruderSelfie */ },
                    enabled = false // Needs Camera permission flow
                )
            }
        }
    }
}

@Composable
fun SettingsSwitch(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
