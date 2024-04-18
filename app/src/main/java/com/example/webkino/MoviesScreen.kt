package com.example.webkino

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
import retrofit2.http.Query
import java.net.URL

// Define your API key here
private const val API_KEY = "abf3a6ac8c9d2e33c91d318ea187ea19"

// Define the base URL of the API
private const val BASE_URL = "https://api.themoviedb.org/3/"

// Define a Retrofit interface for making API calls
interface MovieApiService {
    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1, // Default page is 1
        @Query("include_adult") includeAdult: Boolean = adultContentAllowed,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "",
        @Query("with_genres") genres: String = ""
    ): Call<MovieResponse>
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(navController: NavHostController) {
    // Define a state for holding the list of movies
    val moviesState = remember { mutableStateOf<List<Movie>>(emptyList()) }
    val movieResponseState = remember { mutableStateOf<MovieResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    // Variable to hold the current page number
    var currentPage by remember { mutableIntStateOf(1) }

    // Main variable that holds data about genres
    var genres by remember { mutableStateOf(listOf(
        Genre(28, "Action"),
        Genre(12, "Adventure"),
        Genre(16, "Animation"),
        Genre(35, "Comedy"),
        Genre(80, "Crime"),
        Genre(99, "Documentary"),
        Genre(18, "Drama"),
        Genre(10751, "Family"),
        Genre(14, "Fantasy"),
        Genre(36, "History"),
        Genre(27, "Horror"),
        Genre(10402, "Music"),
        Genre(9648, "Mystery"),
        Genre(10749, "Romance"),
        Genre(878, "Science Fiction"),
        Genre(10770, "TV Movie"),
        Genre(53, "Thriller"),
        Genre(10752, "War"),
        Genre(37, "Western")
    ))}
    // Temporary variable to edit while in the genre selection dialog
    var temporaryGenres by remember { mutableStateOf(genres.map { it.copy() }.toList())}
    // Variable that controls visibility of genres selection dialog
    var showFiltersDialog by remember { mutableStateOf(false)}
    // Variable to pass to Retrofit instance
    var selectedGenres by remember { mutableStateOf(listOf<Int>()) }
    selectedGenres = genres.filter { it.isChecked }.map { it.id }



    // Variable that controls visibility of sorting selection dialog
    var showSortingDialog by remember { mutableStateOf(false)}
    // Main variable to hold data about sortingMethods
    var sortingMethods by remember { mutableStateOf(listOf(
        SortingMethod("popularity.desc", "Popularity (popular first)", true),
        SortingMethod("popularity.asc", "Popularity (unpopular first)", false),
        SortingMethod("primary_release_date.desc", "Release date (newest first)", false),
        SortingMethod("primary_release_date.asc", "Release date (oldest first)", false),
    ))}
    // Temporary variable to edit in the sorting method selection dialog
    var temporarySortingMethods by remember { mutableStateOf(sortingMethods.map { it.copy() }.toList())}
    // Variable to pass to Retrofit instance
    var selectedSortingMethod: String? = sortingMethods.find { it.isSelected }?.id
    println("Selected sorting method:$selectedSortingMethod")

    // Create a Retrofit instance
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Create an instance of the API service
    val service = retrofit.create(MovieApiService::class.java)

    // Function for fetching movie data and posters
    fun fetchMovies(page: Int = currentPage, genres: List<Int> = selectedGenres, sortBy: String? = selectedSortingMethod) {
        service.getMovies(page = page, genres = genres.joinToString("|"), sortBy = sortBy ?: "popularity.desc").enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    if (movieResponse != null) {
                        println("Sort by:$sortBy")
                        println("Base url:$BASE_URL")
                        println("")
                    }
                    val movies = movieResponse?.results ?: emptyList()
                    CoroutineScope(Dispatchers.IO).launch {
                        fetchPosterImagesForMovies(movies)
                        withContext(Dispatchers.Main) {
                            moviesState.value = movies
                            movieResponseState.value = movieResponse
                            isLoading = false
                        }
                    }
                }
            }

            private suspend fun fetchPosterImagesForMovies(movies: List<Movie>) {
                // Iterate through each movie and fetch its poster image
                movies.forEach { movie ->
                    if (movie.poster_path != null) {
                        try {
                            val inputStream = URL("https://image.tmdb.org/t/p/w500${movie.poster_path}").openStream()
                            val bitmap = BitmapFactory.decodeStream(inputStream)
                            val imageBitmap = bitmap.asImageBitmap()
                            movie.poster_image = imageBitmap
                        } catch (e: Exception) {
                            println(e)
                            isLoading = false
                        }
                    } else {
                        movie.poster_image = null
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Error handling
                isLoading = false
            }
        })
    }

    // Fetch movies for the initial page
    fetchMovies()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkerGreyColor,
                    titleContentColor = offWhiteColor
                ),
                title = {
                    Text(stringResource(R.string.movies), textAlign = TextAlign.Center,)
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
        // UI area
        if (isLoading) {
            // Show loading indicator if something is loading
            Box(modifier = Modifier
                .fillMaxSize()
                .background(brush = bgGradient), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = goldenColor)
            }
        } else {
            // Show UI if nothing is loading
            Box(modifier = Modifier
                .background(brush = bgGradient)
                .fillMaxSize()
                .padding(innerPadding)
            ) {
                // List of Movies and page switching
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp, 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Display the list of movies using LazyColumn
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    item() {
                        Spacer(modifier = Modifier.height(35.dp))
                    }
                        items(moviesState.value) { movie ->
                            MovieCard(movie = movie)
                        }
                        // Display page switching buttons in the bottom of LazyColumn
                        item() {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                TextButton(
                                    onClick = {
                                        if (currentPage > 1) {
                                            currentPage -= 1 // Decrement page number
                                            isLoading = true
                                            fetchMovies(currentPage) // Fetch movies for the previous page
                                        }
                                    },
                                    enabled = (currentPage > 1)
                                )
                                {
                                    Text(
                                        text = "◄  ${stringResource(id = R.string.previous)}",
                                        fontSize = 15.sp,
                                        color = if (currentPage > 1) goldenColor else darkerGreyColor
                                    )
                                }

                                Text(
                                    text = "${movieResponseState.value?.page} / ${movieResponseState.value?.total_pages}",
                                    fontSize = 15.sp,
                                    color = goldenColor,
                                    fontWeight = FontWeight.Bold
                                )

                                TextButton(
                                    onClick = {
                                        if (currentPage < (movieResponseState.value?.total_pages ?: 1)) {
                                            currentPage += 1 // Increment page number
                                            isLoading = true
                                            fetchMovies(currentPage) // Fetch movies for the next page
                                        }
                                    },
                                    enabled = ((currentPage < (movieResponseState.value?.total_pages
                                        ?: 1)))
                                ) {
                                    Text(
                                        text = "${stringResource(id = R.string.next)}  ►",
                                        fontSize = 15.sp,
                                        color = if (currentPage < (movieResponseState.value?.total_pages ?: 1)
                                        ) goldenColor else darkerGreyColor
                                    )
                                }
                            }
                        }
                    }
                }

                // Display filters and sorting chips
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        // Genre selection chip
                        Chip(
                            text = stringResource(R.string.filter),
                            onClick =
                            {
                                temporaryGenres = genres.map { it.copy() }
                                showFiltersDialog = true
                            },
                            image = painterResource(R.drawable.filter)
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                        // Sorting selection chip
                        Chip(
                            text = stringResource(id = R.string.sort_by),
                            onClick =
                            {
                                temporarySortingMethods = sortingMethods.map { it.copy() }
                                showSortingDialog = true
                            },
                            image = painterResource(R.drawable.sort)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                // Show sorting method selection dialog
                if (showSortingDialog) {
                    Dialog(onDismissRequest = {}) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                                .padding(16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(offWhiteColor)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Select sorting method",
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = darkerGreyColor
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(
                                    modifier = Modifier
                                        .width(250.dp)
                                        .height(2.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                Column(
                                    modifier = Modifier.fillMaxWidth().height(220.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    temporarySortingMethods.forEach { sortingMethod ->
                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                                .height(40.dp),
                                            shape = RoundedCornerShape(8.dp),
                                            onClick = {
                                                temporarySortingMethods.forEach {
                                                    it.isSelected = false
                                                }
                                                sortingMethod.isSelected = true
                                                showSortingDialog = false
                                                isLoading = true
                                                currentPage = 1
                                                sortingMethods = temporarySortingMethods.map { it.copy() }
                                                selectedSortingMethod = sortingMethods.find { it.isSelected }?.id
                                                fetchMovies(currentPage, selectedGenres, selectedSortingMethod)
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = lightGreyColor)
                                        ) {
                                            Text(
                                                text = sortingMethod.name,
                                                color = darkerGreyColor,
                                                fontSize = 15.sp,
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        }
                    }
                }

                // Display genre selection dialog
                if (showFiltersDialog) {
                    Dialog(onDismissRequest = {}) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)
                                .padding(16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(offWhiteColor)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    stringResource(R.string.select_genres),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = darkerGreyColor
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(
                                    modifier = Modifier
                                        .width(250.dp)
                                        .height(2.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                LazyColumn(modifier = Modifier
                                    .height(320.dp)
                                    .fillMaxWidth()) {
                                    items(temporaryGenres) { genre ->
                                        GenreItem(genre = genre)
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                Row(modifier = Modifier.width(250.dp), horizontalArrangement = Arrangement.Center) {
                                    OutlinedButton(
                                        onClick =
                                        {
                                            showFiltersDialog = false
                                            temporaryGenres = genres.map { it.copy() }
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.width(110.dp)
                                    ) {
                                        Text(stringResource(R.string.cancel), color = darkerGreyColor)
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Button(
                                        onClick =
                                        {
                                            showFiltersDialog = false
                                            isLoading = true
                                            currentPage = 1
                                            genres = temporaryGenres.map { it.copy() }
                                            selectedGenres = genres.filter { it.isChecked }.map { it.id }
                                            fetchMovies(currentPage, selectedGenres, selectedSortingMethod)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = goldenColor),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.width(110.dp)
                                    ) {
                                        Text(stringResource(R.string.apply), color = darkerGreyColor)
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}




@Preview
@Composable
fun MoviesScreenPreview() {
    // Create a mock container
    val navController = rememberNavController()
    MoviesScreen(navController = navController)
}