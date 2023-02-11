package com.learning.composetutorial

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learning.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {

    val conversationSample = listOf(
        Message(
            "Colleague",
            "Test...Test...Test..."
        ),
        Message(
            "Colleague",
            "List of Android versions:\n" +
                    "Android KitKat (API 19)\n" +
                    "Android Lollipop (API 21)\n" +
                    "Android Marshmallow (API 23)\n" +
                    "Android Nougat (API 24)\n" +
                    "Android Oreo (API 26)\n" +
                    "Android Pie (API 28)\n" +
                    "Android 10 (API 29)\n" +
                    "Android 11 (API 30)\n" +
                    "Android 12 (API 31)\n"
        ),
        Message(
            "Colleague",
            "I think Kotlin is my favorite programming language.\n" +
                    "It's so much fun!"
        ),
        Message(
            "Colleague",
            "Searching for alternatives to XML layouts..."
        ),
        Message(
            "Colleague",
            "Hey, take a look at Jetpack Compose, it's great!\n" +
                    "It's the Android's modern toolkit for building native UI." +
                    "It simplifies and accelerates UI development on Android." +
                    "Less code, powerful tools, and intuitive Kotlin APIs :)"
        ),
        Message(
            "Colleague",
            "It's available from API 21+ :)"
        ),
        Message(
            "Colleague",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        Message(
            "Colleague",
            "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Colleague",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Colleague",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        Message(
            "Colleague",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Colleague",
            "Previews are also interactive after enabling the experimental setting"
        ),
        Message(
            "Colleague",
            "Have you tried writing build.gradle with KTS?"
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LazyColumn {
                        this.items(conversationSample) {
                            conversationSample -> MessageCard(message = conversationSample)
                        }
                    }

                }
            }
        }
    }
    @Composable
    fun MessageCard(message: Message) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(painter = painterResource(id = R.drawable.image1), contentDescription = "Image Avatar", modifier = Modifier
                .size(40.dp, 40.dp)
                .clip(
                    CircleShape
                )
                .border(1.dp, color = MaterialTheme.colors.primary, CircleShape)
            )
            var isExpand by remember { mutableStateOf(false) }
            val surfaceColor by animateColorAsState(
                if (isExpand) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column(modifier = Modifier.clickable { isExpand = !isExpand }) {
                Text(text = "Hello ${message.author}!", color = MaterialTheme.colors.primary, style = MaterialTheme.typography.subtitle2)

                androidx.compose.material.Surface(elevation = 1.dp, shape = MaterialTheme.shapes.medium, color = surfaceColor ) {
                    Text(
                        text = "Hellp ${message.body}",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(3.dp),
                        maxLines = if (isExpand) Int.MAX_VALUE else 1

                    )
                }

            }
        }
    }
    @Preview(showBackground = true)
    @Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark mode")
    @Composable
    fun DefaultPreview() {
        LazyColumn {
            this.items(conversationSample) {
                    conversationSample -> MessageCard(message = conversationSample)
            }
        }


    }
}

data class Message(val author: String, val body: String)

