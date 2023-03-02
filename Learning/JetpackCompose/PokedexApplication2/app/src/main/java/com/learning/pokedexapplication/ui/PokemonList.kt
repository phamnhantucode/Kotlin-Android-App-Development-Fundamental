package com.learning.pokedexapplication.ui.theme

import android.widget.Space
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.learning.pokedexapplication.R
import com.learning.pokedexapplication.data.model.PokedexListEntry
import com.learning.pokedexapplication.ui.viewmodel.PokemonListViewModel

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    Surface(  // a root of a screen
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.pokemon_logo),
                contentDescription = stringResource(
                    id = R.string.app_name
                ),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(modifier = Modifier.fillMaxWidth(), hint = "Search pokemon ...") {
                viewModel.searchPokemonList(it)
            }

            Spacer(modifier = Modifier.height(20.dp))
            PokedexListEntry(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    hint: String = stringResource(id = R.string.emptyString),
    viewModel: PokemonListViewModel = hiltViewModel(),
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    Box(
        modifier = modifier
    ) {
        val localFocusManager =
            LocalFocusManager.current
        TextField(
            value = text, onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontSize = 17.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .shadow(5.dp)
                .clip(MaterialTheme.shapes.medium),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            ),
            placeholder = {
                Text(text = hint, color = Color.LightGray)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "Search",
                    tint = Color.DarkGray
                )
            },
            keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }) //close keyboard when click Ok
        )
    }
}

@Composable
fun PokedexEntry(
    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        modifier = modifier
            .padding(7.5.dp)
            .shadow(5.dp, MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
                )
            },
        contentAlignment = Center
    ) {
        Column() {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .crossfade(true)
                    .build()
                ,
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .scale(0.5f)
                            .align(Center)
                    )
                },
                onSuccess = {
                            viewModel.calcDominantColor(it.result.drawable) {
                                dominantColor = it
                            }

                }
                ,
                contentDescription = entry.pokemonName,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(CenterHorizontally)
            )
            Text(
                text = entry.pokemonName,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun PokedexListEntry(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {

    var isLoading by remember {
        viewModel.isLoading
    }
    var endReached by remember {
        viewModel.endReached
    }
    val loadingError by remember {
        viewModel.loadError
    }
    val pokemonList = remember {
        viewModel.pokemonList
    }
    var isSearching by remember {
        viewModel.isSearching
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(7.5.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(pokemonList.value.size) {
            if (it >= pokemonList.value.size - 1 && !endReached && !isLoading && !isSearching) {
                LaunchedEffect(key1 = true, block = {
                    viewModel.loadPokemonPaginated()
                })
            }
            PokedexEntry(entry = pokemonList.value[it], navController = navController)
        }
    }
    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (loadingError.isNotEmpty()) {
            RetrySection(error = loadingError) {
                viewModel.loadPokemonPaginated()
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}