package com.antigravity.applocker.triggers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.antigravity.applocker.presentation.lock.LockActivity

class SecretCodeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SECRET_CODE") {
            val uri = intent.data
            if (uri != null && uri.scheme == "android_secret_code") {
                val host = uri.host
                if (host == "0987") {
                    val launchIntent = Intent(context, LockActivity::class.java).apply {
                        putExtra(LockActivity.EXTRA_PACKAGE_NAME, "HIDDEN_VAULT")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    context.startActivity(launchIntent)
                }
            }
        }
    }
}
