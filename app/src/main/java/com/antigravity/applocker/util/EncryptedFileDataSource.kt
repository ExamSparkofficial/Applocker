package com.antigravity.applocker.util

import android.net.Uri
import androidx.media3.common.C
import androidx.media3.datasource.BaseDataSource
import androidx.media3.datasource.DataSpec
import java.io.InputStream
import java.io.EOFException

class EncryptedFileDataSource(
    private val inputStreamProvider: () -> InputStream
) : BaseDataSource(true) {

    private var inputStream: InputStream? = null
    private var opened = false
    private var bytesRemaining: Long = 0

    override fun open(dataSpec: DataSpec): Long {
        try {
            transferInitializing(dataSpec)
            
            val stream = inputStreamProvider()
            inputStream = stream
            
            val skipped = stream.skip(dataSpec.position)
            if (skipped < dataSpec.position) {
                throw EOFException()
            }
            
            bytesRemaining = if (dataSpec.length != C.LENGTH_UNSET.toLong()) {
                dataSpec.length
            } else {
                val available = stream.available().toLong()
                if (available == Integer.MAX_VALUE.toLong()) {
                    C.LENGTH_UNSET.toLong()
                } else {
                    available
                }
            }
            
            opened = true
            transferStarted(dataSpec)
            
            return bytesRemaining
        } catch (e: Exception) {
            throw e
        }
    }

    override fun read(buffer: ByteArray, offset: Int, length: Int): Int {
        if (length == 0) {
            return 0
        } else if (bytesRemaining == 0L) {
            return C.RESULT_END_OF_INPUT
        }

        val bytesToRead = if (bytesRemaining == C.LENGTH_UNSET.toLong()) {
            length
        } else {
            minOf(bytesRemaining, length.toLong()).toInt()
        }

        val bytesRead = try {
            inputStream?.read(buffer, offset, bytesToRead) ?: -1
        } catch (e: Exception) {
            throw e
        }

        if (bytesRead == -1) {
            if (bytesRemaining != C.LENGTH_UNSET.toLong()) {
                throw EOFException()
            }
            return C.RESULT_END_OF_INPUT
        }

        if (bytesRemaining != C.LENGTH_UNSET.toLong()) {
            bytesRemaining -= bytesRead
        }
        bytesTransferred(bytesRead)
        return bytesRead
    }

    override fun getUri(): Uri? = null

    override fun close() {
        inputStream?.let {
            try {
                it.close()
            } catch (e: Exception) {
                // Ignore
            }
            inputStream = null
        }
        if (opened) {
            opened = false
            transferEnded()
        }
    }
}
