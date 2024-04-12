package com.example.webkino

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.webkino.ui.theme.bgGradient
import com.example.webkino.ui.theme.darkGreyColor
import com.example.webkino.ui.theme.darkerGreyColor
import com.example.webkino.ui.theme.goldenColor
import com.example.webkino.ui.theme.offWhiteColor

@Composable
fun SettingsScreen(navController: NavHostController) {
    Box(modifier = Modifier.background(brush = bgGradient).fillMaxSize())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        // Button to switch to go back to HomeScreen
        Button(
            onClick = {
                navController.navigate("homeScreen")
            },
            modifier = Modifier
                .padding(16.dp)
                .width(300.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = offWhiteColor),
        ) {
            Text(
                "Back",
                fontSize = 18.sp,
                color = darkGreyColor
            )
        }
    }
}

// This is a mock for preview as the screen composable has parameters
// and Preview design window cannot show components with parameters.
@Preview
@Composable
fun SettingsScreenPreview() {
    // Create a mock container
    val navController = rememberNavController()
    MoviesScreen(navController = navController)

}