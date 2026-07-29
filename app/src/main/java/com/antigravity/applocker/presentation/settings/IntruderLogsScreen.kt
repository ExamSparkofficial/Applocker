package com.antigravity.applocker.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntruderLogsScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    var intruderPhotos by remember { mutableStateOf(emptyList<File>()) }

    LaunchedEffect(Unit) {
        val dir = File(context.filesDir, "intruders")
        if (dir.exists()) {
            intruderPhotos = dir.listFiles()?.filter { it.extension == "jpg" }?.sortedByDescending { it.lastModified() } ?: emptyList()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Intruder Logs", fontWeight = FontWeight.Bold) },
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
        if (intruderPhotos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No intruders caught yet!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(intruderPhotos) { photo ->
                    IntruderLogItem(photo = photo)
                }
            }
        }
    }
}

@Composable
fun IntruderLogItem(photo: File) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(photo),
                contentDescription = "Intruder Selfie",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            val parts = photo.nameWithoutExtension.split("_")
            val packageName = if (parts.size >= 2) parts[1] else "Unknown App"
            val timestampStr = if (parts.size >= 4) "${parts[2]}_${parts[3]}" else ""
            
            val formattedDate = try {
                val date = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).parse(timestampStr)
                SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault()).format(date ?: Date(photo.lastModified()))
            } catch (e: Exception) {
                SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault()).format(Date(photo.lastModified()))
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "App: $packageName",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
