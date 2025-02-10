package com.example.jetfilms.Hilt

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import coil.util.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.1)
                    .build()
            }
            .diskCachePolicy(CachePolicy.DISABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.03)
                    .directory(cacheDir)
                    .build()
            }
            .crossfade(true)
            .allowHardware(true)
            .allowRgb565(true)
            .logger(DebugLogger())
            .build()
    }

}