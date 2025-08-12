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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akshat.persistentintuitascoding.ui.theme.PersistentIntuitASCodingTheme

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
    val viewModel: TimerListViewModel = viewModel()
    val timers by viewModel.timers.collectAsState()
    val listState = rememberLazyListState()

    // Detect visible items and update timers
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.map { it.index } }
            .collect { visibleIndexes ->
                viewModel.updateVisibleItems(visibleIndexes)
            }
    }

    LazyColumn(state = listState) {
        itemsIndexed(timers) { index, timerValue ->
            TimerListItem(index, timerValue)
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