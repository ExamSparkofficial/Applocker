package com.antigravity.applocker.presentation.media

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaGalleryScreen(
    onNavigateBack: () -> Unit,
    onNavigateToViewer: (String) -> Unit,
    viewModel: MediaGalleryViewModel = hiltViewModel()
) {
    val mediaList by viewModel.mediaList.collectAsState(initial = emptyList())
    val context = LocalContext.current

    val deleteLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        // Handle result if needed (e.g., if user denied deletion, we might want to know)
    }

    LaunchedEffect(Unit) {
        viewModel.deleteRequestFlow.collect { intentSender ->
            val request = androidx.activity.result.IntentSenderRequest.Builder(intentSender).build()
            deleteLauncher.launch(request)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            viewModel.hideSelectedMedia(uris, context)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Encrypted Vault") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                launcher.launch(arrayOf("image/*", "video/*"))
            }) {
                Icon(Icons.Default.Add, contentDescription = "Hide Media")
            }
        }
    ) { padding ->
        if (mediaList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(
                    text = "Vault is empty.\nTap + to hide photos and videos.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {
                items(mediaList) { media ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .aspectRatio(1f)
                            .clickable { onNavigateToViewer(media.id) },
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            // Temporary placeholder until we implement decrypted thumbnail loading
                            Text(if (media.mimeType.startsWith("video")) "🎥" else "🖼️")
                        }
                    }
                }
            }
        }
    }
}
