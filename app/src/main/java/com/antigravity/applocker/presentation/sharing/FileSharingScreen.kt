package com.antigravity.applocker.presentation.sharing

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.antigravity.applocker.services.LocalWebServerService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileSharingScreen(
    onNavigateBack: () -> Unit,
    viewModel: FileSharingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Apps", "Photos")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Share Files") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            if (uiState.selectedUris.isNotEmpty() && !uiState.isSharing) {
                BottomAppBar {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${uiState.selectedUris.size} selected")
                        Button(onClick = {
                            viewModel.startSharing()
                            // Start foreground service
                            val intent = Intent(context, LocalWebServerService::class.java)
                            context.startService(intent)
                        }) {
                            Text("Share via Wi-Fi / PC")
                        }
                    }
                }
            } else if (uiState.isSharing) {
                BottomAppBar {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Sharing active", color = MaterialTheme.colorScheme.primary)
                        Button(onClick = {
                            viewModel.stopSharing()
                            context.stopService(Intent(context, LocalWebServerService::class.java))
                        }) {
                            Text("Stop")
                        }
                    }
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (uiState.isSharing) {
                SharingActiveView(uiState)
            } else {
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }
                
                if (uiState.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    when (selectedTabIndex) {
                        0 -> AppSelectionList(uiState, viewModel)
                        1 -> PhotoSelectionGrid(uiState, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun AppSelectionList(uiState: FileSharingUiState, viewModel: FileSharingViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(uiState.apps) { app ->
            val isSelected = uiState.selectedUris.contains(app.apkUri)
            ListItem(
                headlineContent = { Text(app.name) },
                supportingContent = { Text(app.packageName) },
                trailingContent = {
                    if (isSelected) {
                        Icon(Icons.Default.CheckCircle, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                modifier = Modifier.clickable { viewModel.toggleSelection(app.apkUri) }
            )
        }
    }
}

@Composable
fun PhotoSelectionGrid(uiState: FileSharingUiState, viewModel: FileSharingViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(uiState.photos) { photo ->
            val isSelected = uiState.selectedUris.contains(photo.uri)
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .aspectRatio(1f)
                    .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.LightGray)
                    .clickable { viewModel.toggleSelection(photo.uri) },
                contentAlignment = Alignment.Center
            ) {
                Text(photo.name, fontSize = 10.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(4.dp))
                if (isSelected) {
                    Icon(
                        Icons.Default.CheckCircle, 
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SharingActiveView(uiState: FileSharingUiState) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Share to PC", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Open your web browser on PC and enter:", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                "http://${uiState.serverIp}:8080",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text("Share to Phone", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Make sure the other phone is on the same Wi-Fi or connected via Wi-Fi Direct.")
        CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
    }
}
