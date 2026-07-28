package com.antigravity.applocker.util

import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HashUtil @Inject constructor() {

    fun hashSHA256(input: String, salt: String = ""): String {
        val saltedInput = input + salt
        val bytes = saltedInput.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
