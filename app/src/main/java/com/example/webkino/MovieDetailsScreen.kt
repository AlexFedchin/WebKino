package com.example.webkino

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.webkino.ui.theme.bgGradient
import com.example.webkino.ui.theme.darkerGreyColor
import com.example.webkino.ui.theme.goldenColor
import com.example.webkino.ui.theme.lightGreyColor
import com.example.webkino.ui.theme.offWhiteColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.URL

interface MovieDetailsApiService {
    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): Call<MovieDetails>
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MovieDetailsScreen(navController: NavHostController, movieId: Int) {
    // Define a state for holding the movie details
    val movieDetailState = remember { mutableStateOf<MovieDetails?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Create a Retrofit instance
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Create an instance of the API service for movie details
    val service = retrofit.create(MovieDetailsApiService::class.java)

    // Function for fetching movie details
    fun fetchMovieDetail(movieId: Int) {
        service.getMovieDetail(movieId = movieId).enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                if (response.isSuccessful) {
                    val movieDetail = response.body()
                    if (movieDetail != null) {
                        // Fetch the poster image URL
                        val posterUrl = movieDetail.poster_path?.let { BASE_POSTER_URL + it }
                        // Fetch the poster image Bitmap asynchronously
                        if (posterUrl != null) {
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    val inputStream = URL(posterUrl).openStream()
                                    val bitmap = BitmapFactory.decodeStream(inputStream)
                                    val imageBitmap = bitmap.asImageBitmap()
                                    // Update the movieDetail with the poster image
                                    movieDetail.poster_image = imageBitmap
                                    // Update the state
                                    withContext(Dispatchers.Main) {
                                        movieDetailState.value = movieDetail
                                        isLoading = false
                                    }
                                } catch (e: Exception) {
                                    println(e)
                                    isLoading = false
                                }
                            }
                        } else {
                            // If poster_path is null, set poster_image to null
                            movieDetail.poster_image = null
                            movieDetailState.value = movieDetail
                            isLoading = false
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                // Error handling
                isLoading = false
            }
        })
    }

    // Fetch movie details for the given movie ID
    LaunchedEffect(movieId) {
        fetchMovieDetail(movieId)
    }

    val movieDetails = movieDetailState.value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkerGreyColor,
                    titleContentColor = offWhiteColor
                ),
                title = {
                    Text("Details", textAlign = TextAlign.Center)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("moviesScreen") }) {
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
        // Show loading indicator while fetching movie details
        if (isLoading) {
            // Show loading indicator if something is loading
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = bgGradient), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = goldenColor)
            }
        } else {
            if (movieDetails != null) {
                // Show UI if nothing is loading
                Box(
                    modifier = Modifier
                        .background(brush = bgGradient)
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Display poster and main ifo next to it
                        Row(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)) {
                            // Display the poster if loaded successfully and placeholder if not
                            if (movieDetails.poster_image == null) {
                                Image(
                                    painter = painterResource(id = R.drawable.poster_not_found),
                                    contentDescription = movieDetails.title,
                                    modifier = Modifier.height(200.dp))
                            } else {
                                Image(
                                    bitmap = movieDetails.poster_image!!,
                                    contentDescription = movieDetails.title,
                                    modifier = Modifier.height(200.dp))
                            }
                            Column(modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)) {
                                // Display movie title
                                Text(
                                    text = movieDetails.title,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = offWhiteColor,
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                // Display movie slogan
                                if (movieDetails.tagline?.isNotEmpty() == true) {
                                    Text(
                                        text = movieDetails.tagline,
                                        fontSize = 12.sp,
                                        fontStyle = FontStyle.Italic,
                                        color = lightGreyColor,
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Display movie's genres
                                FlowRow {
                                    for (genre in movieDetails.genres) {
                                        GenreChip(
                                            text = genre.name,
                                        )
                                        Spacer(modifier = Modifier
                                            .width(6.dp)
                                            .height(24.dp))
                                    }
                                }

                                HorizontalDivider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 8.dp, 0.dp, 10.dp),
                                    color = lightGreyColor,
                                    thickness = 1.dp
                                )

                                // Display release year
                                Row {
                                    Text(
                                        text = "${stringResource(R.string.year)}: ",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = offWhiteColor,
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = if (movieDetails.release_date.isNotEmpty()) movieDetails.release_date.substring(0, 4) else stringResource(R.string.unknown),
                                        fontSize = 14.sp,
                                        color = offWhiteColor,
                                    )

                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Display countries of production
                                Row {
                                    Text(
                                        text = "${stringResource(R.string.country)}: ",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = offWhiteColor,
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))

                                    Text(
                                        text = if (movieDetails.production_countries.isNotEmpty()) {
                                            movieDetails.production_countries.joinToString(separator = ", ") { it.name }
                                        } else {
                                            stringResource(R.string.unknown)
                                        },
                                        fontSize = 14.sp,
                                        color = offWhiteColor,
                                    )


                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row {
                                    Text(
                                        text = "${stringResource(R.string.status)}: ",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = offWhiteColor,
                                    )
                                    
                                    Spacer(modifier = Modifier.width(2.dp))

                                    Text(
                                        text = movieDetails.status.ifEmpty {
                                            stringResource(R.string.unknown)
                                        },
                                        fontSize = 14.sp,
                                        color = offWhiteColor,
                                    )

                                }
                            }
                        }

                        // Display movie's description
                        Text(
                            text = "${stringResource(R.string.description)}:",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = offWhiteColor,
                            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                        )
                        Text(
                            text = movieDetails.overview.ifEmpty {
                                stringResource(R.string.no_description)
                            },
                            fontSize = 16.sp,
                            color = offWhiteColor,
                            style = TextStyle(lineHeight = 24.sp),
                            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Display movie's rating
                        Text(
                            text = "${stringResource(R.string.rating)}:",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = offWhiteColor,
                            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                        )
                        
                        // Display movie's rating in stars
                        RatingStars(movieDetails.vote_average)
                    }
                }
            } else {
                // Show error message if movie with this id was not found
                Box(
                    modifier = Modifier
                        .background(brush = bgGradient)
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = stringResource(R.string.oops), color = offWhiteColor, fontWeight = FontWeight.Bold, fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = stringResource(R.string.movie_not_found), color = offWhiteColor, fontSize = 18.sp)
                            Text(text = stringResource(R.string.try_again), color = offWhiteColor, fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}