package com.antigravity.applocker.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import java.io.File

data class SharedAppInfo(
    val name: String,
    val packageName: String,
    val apkUri: Uri,
    val size: Long
)

data class SharedMediaInfo(
    val id: Long,
    val name: String,
    val uri: Uri,
    val size: Long,
    val mimeType: String
)

@Singleton
class DeviceStorageHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun getInstalledApps(): List<SharedAppInfo> = withContext(Dispatchers.IO) {
        val pm = context.packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        
        packages.filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }
            .map { appInfo ->
                val apkFile = File(appInfo.publicSourceDir)
                SharedAppInfo(
                    name = pm.getApplicationLabel(appInfo).toString(),
                    packageName = appInfo.packageName,
                    apkUri = Uri.fromFile(apkFile),
                    size = apkFile.length()
                )
            }.sortedBy { it.name }
    }

    suspend fun getPhotos(): List<SharedMediaInfo> = withContext(Dispatchers.IO) {
        val mediaList = mutableListOf<SharedMediaInfo>()
        val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.MIME_TYPE
        )
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val mimeTypeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getLong(sizeColumn)
                val mimeType = cursor.getString(mimeTypeColumn)
                val contentUri = Uri.withAppendedPath(collection, id.toString())

                mediaList.add(SharedMediaInfo(id, name ?: "Unknown", contentUri, size, mimeType))
            }
        }
        mediaList
    }
}
