package com.antigravity.applocker.util

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pInfo
import android.net.wifi.p2p.WifiP2pManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

import dagger.hilt.android.qualifiers.ApplicationContext

@Singleton
class WiFiDirectManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val manager: WifiP2pManager? by lazy(LazyThreadSafetyMode.NONE) {
        context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager?
    }

    private var channel: WifiP2pManager.Channel? = null
    
    private val _peers = MutableStateFlow<List<WifiP2pDevice>>(emptyList())
    val peers: StateFlow<List<WifiP2pDevice>> = _peers
    
    private val _connectionInfo = MutableStateFlow<WifiP2pInfo?>(null)
    val connectionInfo: StateFlow<WifiP2pInfo?> = _connectionInfo

    val intentFilter = IntentFilter().apply {
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }
    
    fun initChannel() {
        if (channel == null) {
            channel = manager?.initialize(context, context.mainLooper, null)
        }
    }

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                    manager?.requestPeers(channel) { peerList ->
                        _peers.value = peerList.deviceList.toList()
                    }
                }
                WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                    val networkInfo = intent.getParcelableExtra<NetworkInfo>(WifiP2pManager.EXTRA_NETWORK_INFO)
                    if (networkInfo?.isConnected == true) {
                        manager?.requestConnectionInfo(channel) { info ->
                            _connectionInfo.value = info
                        }
                    } else {
                        _connectionInfo.value = null
                    }
                }
            }
        }
    }
    
    fun registerReceiver() {
        context.registerReceiver(receiver, intentFilter)
    }
    
    fun unregisterReceiver() {
        try {
            context.unregisterReceiver(receiver)
        } catch (e: Exception) {
            // Ignore
        }
    }

    @SuppressLint("MissingPermission")
    fun discoverPeers(onSuccess: () -> Unit, onFailure: (Int) -> Unit) {
        manager?.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() = onSuccess()
            override fun onFailure(reasonCode: Int) = onFailure(reasonCode)
        })
    }
    
    @SuppressLint("MissingPermission")
    fun connect(deviceAddress: String, onSuccess: () -> Unit, onFailure: (Int) -> Unit) {
        val config = WifiP2pConfig().apply {
            this.deviceAddress = deviceAddress
        }
        manager?.connect(channel, config, object : WifiP2pManager.ActionListener {
            override fun onSuccess() = onSuccess()
            override fun onFailure(reason: Int) = onFailure(reason)
        })
    }
    
    fun createGroup(onSuccess: () -> Unit, onFailure: (Int) -> Unit) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            manager?.createGroup(channel, object : WifiP2pManager.ActionListener {
                override fun onSuccess() = onSuccess()
                override fun onFailure(reason: Int) = onFailure(reason)
            })
        }
    }
}
