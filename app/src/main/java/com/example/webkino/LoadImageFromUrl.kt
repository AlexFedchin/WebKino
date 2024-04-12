package com.example.webkino

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import java.net.URL

@Composable
fun LoadImageFromUrl(url: String, contentDescription: String?, imageHeight: Int) {
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Load image from URL using coroutine
    LaunchedEffect(url) {
        try {
            val inputStream = URL(url).openStream()
            val bmp = BitmapFactory.decodeStream(inputStream)
            imageBitmap = bmp
        } catch (e: Exception) {
            // Handle error
        }
    }

    // Display the image if loaded successfully
    imageBitmap?.let { loadedBitmap ->
        Image(
            bitmap = loadedBitmap.asImageBitmap(),
            contentDescription = contentDescription,
            modifier = Modifier.height(imageHeight.dp)
        )
    }
}
