package com.antigravity.applocker.presentation.media

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.net.Uri

import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.media3.datasource.DataSource
import com.antigravity.applocker.util.EncryptedFileDataSource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaViewerScreen(
    mediaId: String,
    onNavigateBack: () -> Unit,
    viewModel: MediaViewerViewModel = hiltViewModel()
) {
    val media by viewModel.media.collectAsState()
    val decryptedBitmap by viewModel.decryptedBitmap.collectAsState()
    val context = LocalContext.current
    
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }
    
    LaunchedEffect(mediaId) {
        viewModel.loadMedia(mediaId)
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        viewModel.unhideMedia()
                        onNavigateBack()
                    }) {
                        Icon(Icons.Default.Restore, contentDescription = "Unhide", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.5f)
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            if (media?.mimeType?.startsWith("image") == true) {
                decryptedBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Encrypted Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                } ?: CircularProgressIndicator()
            } else if (media?.mimeType?.startsWith("video") == true) {
                // Initialize ExoPlayer with custom EncryptedDataSource
                LaunchedEffect(media) {
                    media?.let { entity ->
                        val factory = DataSource.Factory {
                            EncryptedFileDataSource {
                                viewModel.getDecryptedInputStream(entity)
                            }
                        }
                        val mediaItem = MediaItem.fromUri(Uri.parse("encrypted://video"))
                        val source = androidx.media3.exoplayer.source.ProgressiveMediaSource.Factory(factory)
                            .createMediaSource(mediaItem)
                        exoPlayer.setMediaSource(source)
                        exoPlayer.prepare()
                        exoPlayer.playWhenReady = true
                    }
                }
                
                AndroidView(
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            player = exoPlayer
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
