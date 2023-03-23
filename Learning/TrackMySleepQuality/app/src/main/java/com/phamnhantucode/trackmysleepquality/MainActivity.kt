package com.phamnhantucode.trackmysleepquality

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.phamnhantucode.trackmysleepquality.ui.theme.TrackMySleepQualityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrackMySleepQualityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: SleepTrackerViewModel =
                        viewModel(
                            factory = SleepTrackerViewModelFactory(
                                SleepDatabase.getInstance(
                                    this
                                ).sleepDatabaseDao, application
                            )
                        )
                    SleepDataScreen(
                        sleepData = viewModel.sleepData.collectAsState().value,
                        isStarting = viewModel.isStarting.collectAsState().value,
                        onStart = viewModel::onStart,
                        onClear = viewModel::onClear,
                        onStop = viewModel::onStop,

                    )
                }
            }
        }
    }
}

@Composable
fun SleepDataScreen(
    sleepData: List<SleepNight>,
    isStarting: Boolean,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onClear: () -> Unit,
//    onSleepClick: (SleepNight) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .align(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isStarting) {
                    Button(
                        onClick = onStart,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green
                        )
                    ) {
                        Text(text = stringResource(id = R.string.start))
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Gray
                        )
                    ) {}
                } else {
                    Button(
                        onClick = {}, colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Gray
                        )
                    ) {}
                    Button(
                        onClick = onStop,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green
                        )
                    ) {
                        Text(text = stringResource(id = R.string.stop))
                    }
                }
            }
            Text(text = "HERE IS YOUR SLEEP DATA")
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(sleepData.size) { index ->
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Divider()
                        Row {
                            Text(text = "Start:", fontWeight = FontWeight.SemiBold)
                            Text(text = convertLongToDateString(sleepData[index].startTimeMilli))
                        }
                        if (sleepData[index].startTimeMilli != sleepData[index].endTimeMilli) {
                            Row {
                                Text(text = "End:", fontWeight = FontWeight.SemiBold)
                                Text(text = convertLongToDateString(sleepData[index].endTimeMilli))
                            }
                            Row {
                                Text(text = "Quality:", fontWeight = FontWeight.SemiBold)
                                Text(
                                    text = convertNumericQualityToString(
                                        sleepData[index].sleepQuality,
                                        LocalContext.current.resources
                                    )
                                )
                            }
                        }
                    }

                }
            }
        }
        Button(onClick = onClear, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = "CLEAR")
        }
    }


}