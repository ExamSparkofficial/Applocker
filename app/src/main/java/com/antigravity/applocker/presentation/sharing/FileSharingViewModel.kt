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

data class FileSharingUiState(
    val selectedUris: List<Uri> = emptyList(),
    val apps: List<SharedAppInfo> = emptyList(),
    val photos: List<SharedMediaInfo> = emptyList(),
    val isLoading: Boolean = false,
    val isSharing: Boolean = false,
    val serverIp: String = "192.168.1.X" // Placeholder, in real app resolve actual IP
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
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val apps = deviceStorageHelper.getInstalledApps()
            val photos = deviceStorageHelper.getPhotos()
            _uiState.update { it.copy(apps = apps, photos = photos, isLoading = false) }
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

    fun startSharing() {
        _uiState.update { it.copy(isSharing = true) }
        wiFiDirectManager.initChannel()
        wiFiDirectManager.discoverPeers({}, {})
    }
    
    fun stopSharing() {
        _uiState.update { it.copy(isSharing = false) }
        sharedFilesRepository.clearSharedUris()
    }
}
