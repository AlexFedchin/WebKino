package com.example.webkino

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
            // Cinema icon
            Image(
                painter = painterResource(id = R.drawable.clapperboard),
                contentDescription = stringResource(R.string.movies),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                stringResource(R.string.movies),
                fontSize = 18.sp,
                color = darkGreyColor
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Settings button
        Button(
            onClick = { navController.navigate("settingsScreen") },
            modifier = Modifier
                .width(300.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = offWhiteColor),
        ) {
            // Settings icon
            Image(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = stringResource(R.string.settings),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                stringResource(R.string.settings),
                fontSize = 18.sp,
                color = darkGreyColor
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.width(300.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            // Information icon
            Image(
                painter = painterResource(id = R.drawable.information),
                contentDescription = stringResource(R.string.information),
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navController.navigate("informationScreen") },
            )
        }

        Spacer(modifier = Modifier.weight(0.1f))
    }
}

// This is a mock for preview as the screen composable has parameters
// and Preview design window cannot show components with parameters.
@Preview
@Composable
fun HomeScreenPreview() {
    // Create a mock container
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
