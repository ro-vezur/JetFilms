package com.example.jetfilms.View.Components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun OptimizedImage(
    url: String,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.05)
                    .build()
            }
            .crossfade(true)
            .build()
    }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .allowHardware(true)
            .build(),
        contentDescription = null,
        contentScale = contentScale,
        imageLoader = imageLoader,
        modifier = modifier
    )
}