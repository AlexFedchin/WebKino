package com.example.webkino

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.webkino.ui.theme.bgGradient
import com.example.webkino.ui.theme.darkerGreyColor
import com.example.webkino.ui.theme.goldenColor
import com.example.webkino.ui.theme.offWhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkerGreyColor,
                    titleContentColor = offWhiteColor
                ),
                title = {
                    Text(stringResource(R.string.information), textAlign = TextAlign.Center)
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
        {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)) {
                item {
                    Text(
                        text = stringResource(R.string.overview),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = goldenColor,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.info_1),
                        fontSize = 16.sp,
                        color = offWhiteColor,
                        style = TextStyle(lineHeight = 20.sp),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.how_to_use),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = goldenColor,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                    Text(
                        text = stringResource(R.string.info_2),
                        fontSize = 16.sp,
                        color = offWhiteColor,
                        style = TextStyle(lineHeight = 20.sp),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                item {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(id = R.drawable.screenshot_1),
                            contentDescription = "App screenshot",
                            modifier = Modifier
                                .height(400.dp)
                                .padding(vertical = 8.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                item {
                    Text(
                        text = stringResource(R.string.info_3),
                        fontSize = 16.sp,
                        color = offWhiteColor,
                        style = TextStyle(lineHeight = 20.sp),
                    )

                    Text(
                        text = stringResource(R.string.info_4),
                        fontSize = 16.sp,
                        color = offWhiteColor,
                        style = TextStyle(lineHeight = 20.sp),
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Text(
                        text = stringResource(R.string.info_5),
                        fontSize = 16.sp,
                        color = offWhiteColor,
                        style = TextStyle(lineHeight = 20.sp),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.credits),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = goldenColor,
                        modifier = Modifier.padding(top = 24.dp)
                    )

                    Text(
                        text = stringResource(R.string.info_6),
                        fontSize = 16.sp,
                        color = offWhiteColor,
                        style = TextStyle(lineHeight = 20.sp),
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = stringResource(R.string.info_7),
                        fontSize = 16.sp,
                        color = offWhiteColor,
                        style = TextStyle(lineHeight = 20.sp),
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Text(
                        text = stringResource(R.string.info_8),
                        fontSize = 16.sp,
                        color = offWhiteColor,
                        style = TextStyle(lineHeight = 20.sp),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                // Links
                item {
                    Text(
                        text = stringResource(R.string.links),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = goldenColor,
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                    )

                    val urlTMDB = "https://www.themoviedb.org/"
                    val urlFlatIcon = "https://www.flaticon.com/"
                    val context = LocalContext.current

                    TextButton(onClick = { val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(urlTMDB)
                        context.startActivity( intent )
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.tmdb),
                            contentDescription = stringResource(id = R.string.icon),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "TheMovieDataBase.org",
                            fontSize = 16.sp,
                            color = offWhiteColor,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                        )
                    }

                    TextButton(
                        onClick =
                        {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(urlFlatIcon)
                            context.startActivity( intent )
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.flat_icon),
                            contentDescription = stringResource(id = R.string.icon),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "FlatIcon.com",
                            fontSize = 16.sp,
                            color = offWhiteColor,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                        )
                    }
                }
            }
        }
    }
}
