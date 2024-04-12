package com.example.webkino

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import androidx.navigation.compose.rememberNavController
import com.example.webkino.ui.theme.bgGradient
import com.example.webkino.ui.theme.darkGreyColor
import com.example.webkino.ui.theme.darkerGreyColor
import com.example.webkino.ui.theme.goldenColor
import com.example.webkino.ui.theme.offWhiteColor

// Define your API key here
private const val API_KEY = "abf3a6ac8c9d2e33c91d318ea187ea19"

// Define the base URL of the API
private const val BASE_URL = "https://api.themoviedb.org/3/"

// Define a Retrofit interface for making API calls
interface MovieApiService {
    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MovieResponse>
}

// Define data classes to represent API response
data class MovieResponse(val results: List<Movie>)

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val poster_path: String?
)

@Composable
fun MoviesScreen(navController: NavHostController) {
    // Create a Retrofit instance
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Create an instance of the API service
    val service = retrofit.create(MovieApiService::class.java)

    // Define a state for holding the list of movies
    val moviesState = remember { mutableStateOf<List<Movie>>(emptyList()) }

    // Make an API call to fetch movies
    service.getMovies().enqueue(object : Callback<MovieResponse> {
        override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
            if (response.isSuccessful) {
                moviesState.value = response.body()?.results ?: emptyList()
            }
        }

        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            // Handle failure
        }
    })

    Box(modifier = Modifier.background(brush = bgGradient).fillMaxSize())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        // Button to switch to the MoviesScreen
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
fun MoviesScreenPreview() {
    // Create a mock container
    val navController = rememberNavController()
    MoviesScreen(navController = navController)
}