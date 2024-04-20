package com.example.webkino

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.webkino.ui.theme.goldenColor
import com.example.webkino.ui.theme.lightGreyColor

@Composable
fun CameraButton() {
    val context = LocalContext.current

    // Camera button
    OutlinedButton(
        onClick = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // Open the camera in video mode
                val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                context.startActivity(intent)
            } else {
                // Request camera permission
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CAMERA), 0)
            }},
        modifier = Modifier.width(300.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, lightGreyColor),
    ) {
        // Camera icon
        Icon(
            painter = painterResource(id = R.drawable.camera),
            contentDescription = stringResource(R.string.icon),
            modifier = Modifier.size(20.dp),
            tint = goldenColor
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = stringResource(R.string.shoot_movie),
            fontSize = 18.sp,
            color = goldenColor,
            fontWeight = FontWeight.Light
        )
    }
}
