package com.akshat.persistentintuitascoding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akshat.persistentintuitascoding.ui.theme.PersistentIntuitASCodingTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PersistentIntuitASCodingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TimeList(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TimeList(modifier: Modifier = Modifier) {
    val listSize = 30
    val timers = remember { MutableList(listSize) { mutableStateOf(0) } }
    val listState = rememberLazyListState()

    // Detect visible items and update timers
    LaunchedEffect(listState) {
        while (isActive) {
            val visibleIndexes = listState.layoutInfo.visibleItemsInfo.map { it.index }
            visibleIndexes.forEach { index ->
                timers[index].value++
            }
            delay(1000)
        }
    }

    LazyColumn(state = listState) {
        itemsIndexed(timers) { index, timerState ->
            TimerListItem(index, timerState.value)
        }
    }
}

@Composable
fun TimerListItem(index: Int, timer: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Item $index")
        Text(text = "$timer s")
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PersistentIntuitASCodingTheme {
        TimeList()
    }
}