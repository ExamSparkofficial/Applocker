package com.antigravity.applocker.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.antigravity.applocker.data.repository.SharedFilesRepository
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.response.respondText
import io.ktor.server.response.respondBytes
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocalWebServerService : Service() {

    @Inject
    lateinit var sharedFilesRepository: SharedFilesRepository

    private var serverJob: Job? = null
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    
    private val server = embeddedServer(CIO, port = 8080) {
        routing {
            get("/") {
                val html = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>AppLocker File Sharing</title>
                        <meta name="viewport" content="width=device-width, initial-scale=1">
                        <style>
                            body { font-family: Arial, sans-serif; margin: 20px; background: #f0f2f5; }
                            h1 { color: #333; }
                            .file-list { list-style: none; padding: 0; }
                            .file-item { background: white; padding: 15px; margin-bottom: 10px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); display: flex; justify-content: space-between; align-items: center; }
                            .btn { background: #007bff; color: white; padding: 10px 15px; text-decoration: none; border-radius: 5px; }
                        </style>
                    </head>
                    <body>
                        <h1>AppLocker File Sharing</h1>
                        <p>Available files:</p>
                        <ul class="file-list" id="files"></ul>
                        
                        <script>
                            fetch('/files')
                                .then(res => res.json())
                                .then(data => {
                                    const list = document.getElementById('files');
                                    data.files.forEach((f, i) => {
                                        const li = document.createElement('li');
                                        li.className = 'file-item';
                                        li.innerHTML = `<span>File ` + (i + 1) + `</span> <a href="/download/` + i + `" class="btn">Download</a>`;
                                        list.appendChild(li);
                                    });
                                });
                        </script>
                    </body>
                    </html>
                """.trimIndent()
                call.respondText(html, ContentType.Text.Html)
            }
            get("/files") {
                val uris = sharedFilesRepository.sharedUris.value
                val count = uris.size
                
                // Simple JSON response (in a real app, use kotlinx.serialization)
                val filesJson = uris.mapIndexed { index, uri -> "{\"index\": $index}" }.joinToString(",")
                call.respondText("{\"count\": $count, \"files\": [$filesJson]}", ContentType.Application.Json)
            }
            get("/download/{index}") {
                val index = call.parameters["index"]?.toIntOrNull()
                val uris = sharedFilesRepository.sharedUris.value
                if (index != null && index >= 0 && index < uris.size) {
                    val uri = uris[index]
                    val contentResolver = applicationContext.contentResolver
                    val inputStream = contentResolver.openInputStream(uri)
                    if (inputStream != null) {
                        // Ktor uses OutgoingContent to stream files, but for simplicity we can use respondBytes if files are small,
                        // or better, respondOutputStream.
                        call.respondBytes(inputStream.readBytes())
                    } else {
                        call.respond(io.ktor.http.HttpStatusCode.NotFound, "")
                    }
                } else {
                    call.respond(io.ktor.http.HttpStatusCode.NotFound, "")
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
        
        serverJob = serviceScope.launch {
            server.start(wait = true)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        server.stop(1000, 2000)
        serverJob?.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "File Sharing Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("File Sharing is active")
        .setContentText("Local web server is running")
        .setSmallIcon(android.R.drawable.stat_sys_download_done)
        .setOngoing(true)
        .build()

    companion object {
        private const val CHANNEL_ID = "file_sharing_channel"
        private const val NOTIFICATION_ID = 2
    }
}
