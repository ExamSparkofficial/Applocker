package com.antigravity.applocker.presentation.sharing

import android.content.Intent
import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.antigravity.applocker.services.LocalWebServerService
import com.antigravity.applocker.util.QRCodeAnalyzer
import com.antigravity.applocker.util.QRCodeGenerator
import com.antigravity.applocker.util.SharedAppInfo
import com.antigravity.applocker.util.SharedMediaInfo
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileSharingScreen(
    onNavigateBack: () -> Unit,
    viewModel: FileSharingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    BackHandler {
        if (uiState.screenState != SharingScreenState.HOME) {
            viewModel.setScreenState(SharingScreenState.HOME)
        } else {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Share Files") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (uiState.screenState != SharingScreenState.HOME) {
                            viewModel.setScreenState(SharingScreenState.HOME)
                        } else {
                            onNavigateBack()
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (uiState.screenState) {
                SharingScreenState.HOME -> HomeScreen(viewModel)
                SharingScreenState.PICKER -> PickerScreen(uiState, viewModel)
                SharingScreenState.PC_SHARE -> PcShareScreen(uiState, viewModel)
                SharingScreenState.SENDER_QR -> SenderQrScreen(uiState, viewModel)
                SharingScreenState.RECEIVER_SCAN -> ReceiverScanScreen(viewModel)
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: FileSharingViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Share",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(48.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.setScreenState(SharingScreenState.PICKER) },
                modifier = Modifier.size(120.dp).aspectRatio(1f),
                shape = CircleShape
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Upload, contentDescription = "Send", modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Send", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
            Button(
                onClick = { viewModel.startReceiverScan() },
                modifier = Modifier.size(120.dp).aspectRatio(1f),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Download, contentDescription = "Receive", modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Receive", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        OutlinedButton(
            onClick = { viewModel.startPcShare() },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Icon(Icons.Default.Computer, contentDescription = "PC")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Share to PC", fontSize = 18.sp)
        }
    }
}

@Composable
fun PickerScreen(uiState: FileSharingUiState, viewModel: FileSharingViewModel) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Apps", "Photos")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        
        Box(modifier = Modifier.weight(1f)) {
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
        
        if (uiState.selectedUris.isNotEmpty()) {
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${uiState.selectedUris.size} selected", fontWeight = FontWeight.Bold)
                    Button(onClick = { viewModel.startSenderQR() }) {
                        Text("Send via QR")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.QrCode, contentDescription = "QR Code")
                    }
                }
            }
        }
    }
}

@Composable
fun AppSelectionList(uiState: FileSharingUiState, viewModel: FileSharingViewModel) {
    val pm = LocalContext.current.packageManager
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(uiState.apps) { app ->
            val isSelected = uiState.selectedUris.contains(app.apkUri)
            
            val iconBitmap = remember(app.packageName) {
                try {
                    pm.getApplicationIcon(app.packageName).toBitmap().asImageBitmap()
                } catch (e: Exception) {
                    null
                }
            }

            ListItem(
                leadingContent = {
                    if (iconBitmap != null) {
                        Image(bitmap = iconBitmap, contentDescription = app.name, modifier = Modifier.size(48.dp))
                    } else {
                        Box(modifier = Modifier.size(48.dp).background(Color.LightGray))
                    }
                },
                headlineContent = { Text(app.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                supportingContent = { Text(app.packageName, maxLines = 1, overflow = TextOverflow.Ellipsis) },
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
        contentPadding = PaddingValues(2.dp)
    ) {
        items(uiState.photos) { photo ->
            val isSelected = uiState.selectedUris.contains(photo.uri)
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .aspectRatio(1f)
                    .clickable { viewModel.toggleSelection(photo.uri) }
            ) {
                AsyncImage(
                    model = photo.uri,
                    contentDescription = photo.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                if (isSelected) {
                    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)))
                    Icon(
                        Icons.Default.CheckCircle, 
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.TopEnd).padding(4.dp).size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PcShareScreen(uiState: FileSharingUiState, viewModel: FileSharingViewModel) {
    val context = LocalContext.current
    var isServerRunning by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val intent = Intent(context, LocalWebServerService::class.java)
        context.startService(intent)
        isServerRunning = true
        onDispose {
            context.stopService(intent)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Computer, contentDescription = "PC", modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Share to PC", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Open your web browser on PC and enter:", textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "http://${uiState.serverIp}:8080",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text("Waiting for connection...", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun SenderQrScreen(uiState: FileSharingUiState, viewModel: FileSharingViewModel) {
    val context = LocalContext.current
    val qrUrl = "http://${uiState.serverIp}:8080"
    
    val qrBitmap = remember(qrUrl) {
        QRCodeGenerator.generateQRCode(qrUrl)?.asImageBitmap()
    }

    DisposableEffect(Unit) {
        val intent = Intent(context, LocalWebServerService::class.java)
        context.startService(intent)
        onDispose {
            context.stopService(intent)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Scan to Connect", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Ask the receiver to scan this QR code", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(32.dp))
        
        Surface(
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            if (qrBitmap != null) {
                Image(
                    bitmap = qrBitmap,
                    contentDescription = "QR Code",
                    modifier = Modifier.size(250.dp).padding(16.dp)
                )
            } else {
                Box(modifier = Modifier.size(250.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        Text("Or open in browser:", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(qrUrl, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun ReceiverScanScreen(viewModel: FileSharingViewModel) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var scannedUrl by remember { mutableStateOf<String?>(null) }
    val executor = remember { Executors.newSingleThreadExecutor() }
    
    DisposableEffect(Unit) {
        onDispose {
            executor.shutdown()
        }
    }

    if (scannedUrl != null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = "Success", tint = Color.Green, modifier = Modifier.size(100.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Text("Connected!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Downloading from:", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(scannedUrl!!, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { 
                val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(scannedUrl!!))
                context.startActivity(intent)
            }, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                Text("Open in Browser to Download", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(onClick = { scannedUrl = null }) {
                Text("Scan Again")
            }
        }
    } else {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    val imageAnalyzer = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(executor, QRCodeAnalyzer { url ->
                                scannedUrl = url
                            })
                        }
                        
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            imageAnalyzer
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, ContextCompat.getMainExecutor(ctx))
                
                previewView
            },
            modifier = Modifier.fillMaxSize()
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            Surface(
                color = Color.Black.copy(alpha = 0.6f),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ) {
                Text(
                    "Scan Sender's QR Code",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }
        }
    }
}
