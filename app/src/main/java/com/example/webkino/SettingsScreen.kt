package com.example.webkino

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import java.util.Locale

var adultContentAllowed : Boolean = false

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    // Define variable to hold adult content selection
    val switchState = remember { mutableStateOf(adultContentAllowed)}
//    // Define app's language options
//    val languageOptions = listOf("English", "Russian")
//    // Define a variable to hold the currently selected language
//    var languageSelected by remember { mutableStateOf("English") }
//    // Define a variable to hold the current Locale
//    val currentLocale = remember { mutableStateOf(Locale.getDefault()) }

//    // Function to set the locale based on the selected language
//    fun setLocale(language: String) {
//        currentLocale.value = when (language) {
//            "English" -> Locale("en")
//            "Russian" -> Locale("ru")
//            else -> Locale.getDefault()
//        }
//    }

//    // Call the setLocale function when the language changes
//    LaunchedEffect(languageSelected) {
//        setLocale(languageSelected)
//    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkerGreyColor,
                    titleContentColor = offWhiteColor
                ),
                title = {
                    Text(stringResource(R.string.settings), textAlign = TextAlign.Center)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("homeScreen") }) {
                        Icon(
                            tint = offWhiteColor,
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier
            .background(brush = bgGradient)
            .fillMaxSize()
            .padding(innerPadding))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            // Adult content setting
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.adultContent), fontSize = 18.sp, color = offWhiteColor)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = switchState.value,
                    onCheckedChange =
                    {
                        switchState.value = !switchState.value
                        adultContentAllowed = !adultContentAllowed
                    },
                    colors = SwitchDefaults.colors(checkedTrackColor = goldenColor)
                )
            }

//            // Dropdown menu for selecting language
//            var expanded by remember { mutableStateOf(false) }

//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(text = "Language", fontSize = 18.sp, color = offWhiteColor)
//                Spacer(modifier = Modifier.weight(1f))
//                Box(
//                    modifier = Modifier.clickable { expanded = true },
//
//                ) {
//                    DropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = { expanded = false },
//                        modifier = Modifier.background(darkerGreyColor)
//                    ) {
//                        languageOptions.forEach { language ->
//
//                            DropdownMenuItem(
//                                onClick = {
//                                    languageSelected = language
//                                    expanded = false },
//                                text = { Text(text = language, fontSize = 18.sp) },
//                                colors = MenuDefaults.itemColors(goldenColor),
//                                modifier = Modifier.background(darkerGreyColor)
//                            )
//
//                        }
//                    }
//                    Text(
//                        text = languageSelected,
//                        color = goldenColor,
//                        fontSize = 18.sp
//                    )
//                }
//            }
            Spacer(modifier = Modifier.weight(1f))
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
    SettingsScreen(navController = navController)

}