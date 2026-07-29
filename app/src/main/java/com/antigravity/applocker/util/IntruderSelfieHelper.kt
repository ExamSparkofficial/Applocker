package com.antigravity.applocker.util

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class IntruderSelfieHelper(private val context: Context, private val lifecycleOwner: LifecycleOwner) {

    private var imageCapture: ImageCapture? = null

    init {
        setupCamera()
    }

    private fun setupCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                imageCapture = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()
                
                val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, imageCapture)
            } catch (exc: Exception) {
                Log.e("IntruderSelfie", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    fun takePicture(packageName: String) {
        val capture = imageCapture ?: return

        val outputDir = File(context.filesDir, "intruders")
        if (!outputDir.exists()) outputDir.mkdirs()

        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(System.currentTimeMillis())
        val photoFile = File(outputDir, "intruder_${packageName}_${timestamp}.jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        capture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Log.d("IntruderSelfie", "Photo capture succeeded: ${output.savedUri}")
                    // Optionally, we could encrypt the file here using CryptoManager
                }

                override fun onError(exc: ImageCaptureException) {
                    Log.e("IntruderSelfie", "Photo capture failed: ${exc.message}", exc)
                }
            }
        )
    }
}
