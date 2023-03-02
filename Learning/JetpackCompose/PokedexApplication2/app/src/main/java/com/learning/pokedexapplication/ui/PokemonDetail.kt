package com.learning.pokedexapplication.ui

import com.learning.pokedexapplication.R
import android.widget.Space
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.Coil
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.learning.pokedexapplication.data.remote.responses.Pokemon
import com.learning.pokedexapplication.data.remote.responses.Type
import com.learning.pokedexapplication.ui.viewmodel.PokemonDetailViewModel
import com.learning.pokedexapplication.util.*
import timber.log.Timber

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonName: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value //get value of state

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)
    ) {
        PokemonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(
                    TopCenter
                )
        )
        PokemonDetailStateWrapper(
            pokemonInfo = pokemonInfo,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = topPadding + pokemonSize / 2f,
                    bottom = 16.dp
                )
                .shadow(10.dp, MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colors.surface)
                .padding(16.dp),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Center)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = topPadding + pokemonSize / 2f,
                    bottom = 16.dp
                )
        )
        Box(
            contentAlignment = TopCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (pokemonInfo is Resource.Success) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(String.format(Constants.BASE_IMGURL_POKE, pokemonInfo.data!!.id))
                        .crossfade(true)
                        .build(),
                    contentDescription = pokemonInfo.data.name,
                    modifier = Modifier
                        .size(pokemonSize)
                        .offset(y = topPadding)
                )

            }
        }

    }
}

@Composable
fun PokemonDetailTopSection(
    navController: NavController,
    modifier: Modifier
) {
    Box(
        contentAlignment = TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack() //back to previous fragment
                }

        )
    }
}

@Composable
fun PokemonDetailStateWrapper(
    pokemonInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (pokemonInfo) {
        is Resource.Success -> {
            PokemonDetailSection(pokemon = pokemonInfo.data!!, modifier = modifier)

        }

        is Resource.Error -> {
            Text(
                text = pokemonInfo.message.toString(),
                color = Color.Red,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )

        }


    }

}

@Composable
fun PokemonDetailSection(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    yOffset: Dp = 70.dp
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .offset(y = yOffset)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "#${pokemon.id} ${pokemon.name.capitalize(Locale.current)}",
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
        )
        PokemonTypeSection(types = pokemon.types)
        PokemonDataSection(pokemonWeight = pokemon.weight, pokemonHeight = pokemon.height)
        PokemonBaseStats(pokemon = pokemon)
    }
}

@Composable
fun PokemonTypeSection(types: List<Type>) {
    Row(
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(35.dp)

            ) {
                Text(
                    text = type.type.name.capitalize(Locale.current),
                    textAlign = TextAlign.Center,
                    color = White,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun PokemonDataSection(
    pokemonWeight: Int,
    pokemonHeight: Int,
    sectionHeight: Dp = 80.dp
) {
    val pokemonWeightInKg = remember {
        pokemonWeight / 10f
    }
    val pokemonHeightInMeter = remember {
        pokemonHeight / 10f
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        PokemonDetailDataItem(
            dataValue = pokemonWeightInKg,
            dataUnit = "kg",
            dataIcon = painterResource(R.drawable.ic_weight),
            modifier = Modifier.weight(1f)
        )
        PokemonDetailDataItem(
            dataValue = pokemonHeightInMeter,
            dataUnit = "m",
            dataIcon = painterResource(R.drawable.ic_height),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float,
    dataUnit: String,
    dataIcon: Painter,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(painter = dataIcon, contentDescription = null, tint = MaterialTheme.colors.onSurface)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$dataValue$dataUnit")
    }
}

@Composable
fun PokemonStat(
    startName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animPlayed by remember {
        mutableStateOf(false)
    }

    val curPercent = animateFloatAsState(
        targetValue = if (animPlayed) {
            statValue / statMaxValue.toFloat()
        } else 0f, //stat with 0f and start animate to curPercent if animPlayed is set to true value
    animationSpec = tween(animDuration, animDelay)
    )

    LaunchedEffect(key1 = true) { //make sure that it run one time
        animPlayed = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) { //to check systemTheme
                    Color(0xFF505050)
                } else {
                    LightGray
                }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value) //width mutable
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 10.dp)
        ) {
            Text(
                text = startName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = (curPercent.value * statMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PokemonBaseStats(
    pokemon: Pokemon,
    animDelayPerItem: Int = 100
) {
    val maxBaseStat = remember {
        pokemon.stats.maxOf { it.base_stat }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Base stat",
            style = MaterialTheme.typography.h2,
        )
        Spacer(modifier = Modifier.height(4.dp))
        for(i in pokemon.stats.indices) {
            val stat = pokemon.stats[i]
            PokemonStat(startName = parseStatToAbbr(stat), statValue = stat.base_stat, statMaxValue = maxBaseStat, statColor = parseStatToColor(stat), animDelay = animDelayPerItem)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}