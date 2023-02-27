package com.learning.composecourseyt

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.plcoding.meditationuiyoutube.ui.theme.MeditationUIYouTubeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeditationUIYouTubeTheme(darkTheme = false) {
                HomeScreen()
            }
        }
//            Box(
//                modifier = Modifier
//                    .fillMaxSize(),
//                contentAlignment = Alignment.Center,
//            ) {
//                CircularProgressBar(percentage = 0.8f, number = 100, animDuration = 5000)
//            }



//            TODO:================================================================
//            TODO: music knob ui
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(androidx.compose.ui.graphics.Color.Black)
//            ) {
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .border(
//                            1.dp,
//                            androidx.compose.ui.graphics.Color.Green,
//                            RoundedCornerShape(10.dp)
//                        )
//                        .padding(30.dp)
//                ) {
//                    var volume by remember {
//                        mutableStateOf(0f)
//                    }
//                    val barCount = 20
//                    MusicKnob(
//                        modifier = Modifier.size(100.dp)
//                    ) {
//                        volume = it
//                    }
//                    Spacer(modifier = Modifier.size(20.dp))
//                    VolumeBar(
//                        modifier = Modifier.fillMaxWidth()
//                            .height(30.dp),
//                        activeBars = (barCount * volume).toInt(),
//                        barCount = barCount
//                    )
//                }
//            }
//        }
    }
}

//TODO: =======================================================================
//TODO: constraint layout
//@Composable
//fun makeConstraintLayout() {
//    val constraintSet = ConstraintSet { //create a constraint set
//        val redBox = createRefFor("redBox")
//        val greenBox = createRefFor("greenBox")
//        val guideLine1 = createGuidelineFromStart(0.5f) //just create an abstract line for align the item
//        val guideLine2 = createGuidelineFromTop(0.5f)
//        constrain(redBox) {
//            top.linkTo(parent.top)
//            start.linkTo(parent.start)
//            width = Dimension.value(100.dp)
//            height = Dimension.value(100.dp)
//        }
//
//        constrain(greenBox) {
//            centerAround(guideLine1)
//            centerAround(guideLine2) //
//            width = Dimension.value(100.dp)
//            height = Dimension.value(100.dp)
//        }
//
////        createVerticalChain(redBox, greenBox, chainStyle = ChainStyle.Spread) // chain make an relative position for items in layout and it very strong
//    }
//
//    ConstraintLayout(
//        constraintSet = constraintSet,
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        Box(
//            modifier = Modifier
//                .background(androidx.compose.ui.graphics.Color.Red)
//                .width(300.dp) //we set it constraint so we cannot modify width again in here
//                .layoutId("redBox"),
//
//        )
//        Box(
//            modifier = Modifier
//                .background(androidx.compose.ui.graphics.Color.Green)
//                .layoutId("greenBox")
//        ) {
//        }
//    }
//}

//TODO: =======================================================================
//TODO: lazy column
//@Composable
//fun LazyyColumn() {
//    LazyColumn { //Create a list a column lazy
//        items(1000) { //prefer use itemsIndexed
//            Text(text = "item $it",
//                modifier = Modifier
//                    .fillMaxWidth().padding(0.dp),
//                fontSize = 20.sp
//            )
//        }
//    }
//
//}

//TODO: =======================================================================
//TODO: text field, button, snackBar
//@Composable
//fun TextFieldButtonSnackBar() {
//    val scaffoldState = rememberScaffoldState() //get default scaffold state from android
//    var textFiledState by remember { // assign textField to get and set value of mutable state
//        mutableStateOf("")
//    }
//    val scope = rememberCoroutineScope()
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        scaffoldState = scaffoldState
//    ) { //A scaffold is a layout which implements the basic material design layout structure. You can add things like a TopBar, BottomBar, FAB or Drawer
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 30.dp)
//        ) {
//            OutlinedTextField(value = textFiledState,
//                label = {
//                    Text(text = "Enter your name")
//                },
//                onValueChange = {
//                    textFiledState = it
//                },
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(onClick = {
//                scope.launch {
//                    scaffoldState.snackbarHostState.showSnackbar("Hello $textFiledState")
//                }
//            }) {
//               Text(text = "Pls great me")
//            }
//        }
//    }
//}


//TODO: =======================================================================
//TODO: state
//var isFirstTime = true
//@Composable
//fun ColorBox(
//    modifier: Modifier = Modifier,
//    color: Color,
//    update: (Color) -> Unit,
//) {
//    Box(
//        modifier = modifier
//            .background(color = color)
//            .clickable {
//                if (isFirstTime) {
//                    CoroutineScope(Dispatchers.Default).launch {
//                        repeat(100) {
//                            delay(100)
//                            update(
//                                Color(
//                                    Random.nextFloat(),
//                                    Random.nextFloat(),
//                                    Random.nextFloat(),
//                                    1f
//                                )
//                            )
//                        }
//                    }
//                    isFirstTime = false
//                }
//            }
//    )
//
//}
//

//TODO: =======================================================================
//TODO: styling text
//@Composable
//fun StylingText() {
//    val fontFamily = FontFamily(
//        Font(R.font.lexend_variable_font_wght, FontWeight.Thin),
//        Font(R.font.lexend_variable_font_wght, FontWeight.Normal),
//        Font(R.font.lexend_variable_font_wght, FontWeight.Bold),
//        Font(R.font.lexend_variable_font_wght, FontWeight.Light)
//    )
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = Color.Black)
//            .padding(10.dp)
//    ) {
//        Text(
//            text = buildAnnotatedString { //build annotatedstring, a powerful tool for special string
//                withStyle(
//                    style = SpanStyle( //span style for style a specific text
//                        color = Color.Cyan,
//                        fontSize = 50.sp
//                    )
//                ) {
//                    append("H")
//                }
//                append("allo")
//            },
//            color = Color.White,
//            fontSize = 30.sp,
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.Bold,
//            fontStyle = FontStyle.Italic,
//            textDecoration = TextDecoration.LineThrough
//        )
//
//    }
//}


//TODO: =======================================================================
//TODO: make a image card in jetpack composer
//@Composable
//fun makeImageCard() {
//    val painter = painterResource(id = R.drawable.image)
//    val description = "Description"
//    val title = "Title"
//    Box(
//        modifier = Modifier
//            .fillMaxWidth(0.5f)
//            .padding(16.dp)
//    ) {
//        ImageCard(painter = painter, contentDescription = description, title = title)
//    }
//}
//
//@Composable
////name of composable function must upcase the first letter
//fun ImageCard(
//    painter: Painter, //to paint an image resource
//    contentDescription: String, //describe the content of image for screen reader
//    title: String, //title of an image, which will be display on the screen
//    modifier: Modifier = Modifier
//) {
//    Card( //card like a container
//        modifier = modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(15.dp), //rounded for card
//        elevation = 5.dp //make a shadow for card
//    ) {
//        Box(modifier = Modifier.height(200.dp)) { //box make it composer stack from bottom to top
//            Image(
//                painter = painter,
//                contentDescription = contentDescription,
//                contentScale = ContentScale.Crop
//                //Scale the image
//            )
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(
//                        Brush.verticalGradient(
//                            colors = listOf(
//                                Color.Transparent,
//                                Color.Black
//                            ),
//                            startY = 300f
//                        )
//                    )
//            )
//
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(12.dp),
//                contentAlignment = Alignment.BottomStart
//            ) {
//                Text(text = title, style = TextStyle(color = Color.White), fontSize = 16.sp)
//            }
//
//        }
//    }
//}
