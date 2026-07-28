# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Room Database
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class * { *; }

# Hilt & Dagger
-keep class dagger.hilt.** { *; }
-keep @dagger.hilt.android.AndroidEntryPoint class * { *; }
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }

# Security Crypto
-keep class androidx.security.crypto.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# General Android Optimization
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose

# Suppress missing class warnings for annotations that are not strictly required at runtime
-dontwarn com.google.errorprone.annotations.**
-dontwarn javax.annotation.**
