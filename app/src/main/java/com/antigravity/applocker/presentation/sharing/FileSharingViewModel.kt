package com.antigravity.applocker.presentation.sharing

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antigravity.applocker.data.repository.SharedFilesRepository
import com.antigravity.applocker.util.DeviceStorageHelper
import com.antigravity.applocker.util.SharedAppInfo
import com.antigravity.applocker.util.SharedMediaInfo
import com.antigravity.applocker.util.WiFiDirectManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SharingScreenState {
    HOME,
    PICKER,
    PC_SHARE,
    SENDER_QR,
    RECEIVER_SCAN
}

data class FileSharingUiState(
    val screenState: SharingScreenState = SharingScreenState.HOME,
    val selectedUris: List<Uri> = emptyList(),
    val apps: List<SharedAppInfo> = emptyList(),
    val photos: List<SharedMediaInfo> = emptyList(),
    val isLoading: Boolean = false,
    val serverIp: String = "Unknown"
)

@HiltViewModel
class FileSharingViewModel @Inject constructor(
    private val deviceStorageHelper: DeviceStorageHelper,
    private val sharedFilesRepository: SharedFilesRepository,
    val wiFiDirectManager: WiFiDirectManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(FileSharingUiState())
    val uiState: StateFlow<FileSharingUiState> = _uiState.asStateFlow()

    init {
        loadData()
        _uiState.update { it.copy(serverIp = getLocalIpAddress()) }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val apps = deviceStorageHelper.getInstalledApps()
            val photos = deviceStorageHelper.getPhotos()
            _uiState.update { it.copy(apps = apps, photos = photos, isLoading = false) }
        }
    }

    fun setScreenState(state: SharingScreenState) {
        _uiState.update { it.copy(screenState = state) }
        if (state == SharingScreenState.HOME) {
            sharedFilesRepository.clearSharedUris()
            _uiState.update { it.copy(selectedUris = emptyList()) }
        }
    }

    fun toggleSelection(uri: Uri) {
        val current = _uiState.value.selectedUris.toMutableList()
        if (current.contains(uri)) {
            current.remove(uri)
        } else {
            current.add(uri)
        }
        _uiState.update { it.copy(selectedUris = current) }
        sharedFilesRepository.setSharedUris(current)
    }

    fun startPcShare() {
        setScreenState(SharingScreenState.PC_SHARE)
    }
    
    fun startSenderQR() {
        setScreenState(SharingScreenState.SENDER_QR)
        wiFiDirectManager.initChannel()
    }
    
    fun startReceiverScan() {
        setScreenState(SharingScreenState.RECEIVER_SCAN)
        wiFiDirectManager.initChannel()
    }

    private fun getLocalIpAddress(): String {
        try {
            val interfaces = java.net.NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val networkInterface = interfaces.nextElement()
                val addresses = networkInterface.inetAddresses
                while (addresses.hasMoreElements()) {
                    val address = addresses.nextElement()
                    if (!address.isLoopbackAddress && address is java.net.Inet4Address) {
                        return address.hostAddress ?: "Unknown"
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return "Unknown"
    }
}
