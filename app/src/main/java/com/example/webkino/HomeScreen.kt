package com.example.webkino

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.webkino.ui.theme.bgGradient
import com.example.webkino.ui.theme.darkerGreyColor
import com.example.webkino.ui.theme.goldenColor
import com.example.webkino.ui.theme.lightGreyColor
import com.example.webkino.ui.theme.offWhiteColor


@Composable
fun HomeScreen(navController: NavHostController) {
    Box(modifier = Modifier
        .background(brush = bgGradient)
        .fillMaxSize())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.15f))

        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.logo),
            modifier = Modifier.width(250.dp)
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Button to switch to the MoviesScreen
        Button(
            onClick = {
                navController.navigate("moviesScreen")
            },
            modifier = Modifier
                .width(300.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = goldenColor),
        ) {
            // Clapperboard icon
            Icon(
                painter = painterResource(id = R.drawable.clapperboard),
                contentDescription = stringResource(R.string.icon),
                modifier = Modifier.size(20.dp),
                tint = darkerGreyColor
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                stringResource(R.string.movies),
                fontSize = 18.sp,
                color = darkerGreyColor
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Settings button
        Button(
            onClick = { navController.navigate("settingsScreen") },
            modifier = Modifier
                .width(300.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = offWhiteColor),
        ) {
            // Settings icon
            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = stringResource(R.string.icon),
                modifier = Modifier.size(20.dp),
                tint = darkerGreyColor
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                stringResource(R.string.settings),
                fontSize = 18.sp,
                color = darkerGreyColor
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Camera button
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, open the camera in video mode
                val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                context.startActivity(intent)
            } else {
                // Permission denied, show the toast
                showCameraPermissionDeniedToast(context)
            }
        }

        OutlinedButton(
            onClick = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission already granted, open the camera
                    val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                    context.startActivity(intent)
                } else {
                    // Request camera permission
                    launcher.launch(Manifest.permission.CAMERA)
                }
            },
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


        Spacer(modifier = Modifier.weight(1f))

        // Button to switch to information screen
        Row(modifier = Modifier.width(300.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { navController.navigate("informationScreen") },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.question_mark),
                    contentDescription = stringResource(R.string.icon),
                    tint = lightGreyColor
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.1f))
    }
}

// Outside of the composable
fun showCameraPermissionDeniedToast(context: Context) {
    Toast.makeText(context, context.getString(R.string.camera_permission_denied), Toast.LENGTH_SHORT).show()
}


@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
