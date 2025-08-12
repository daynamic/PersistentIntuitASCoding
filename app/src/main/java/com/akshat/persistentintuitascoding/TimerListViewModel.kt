package com.akshat.persistentintuitascoding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TimerListViewModel : ViewModel() {
    private val _timers = MutableStateFlow(List(100) { 0 })
    val timers: StateFlow<List<Int>> = _timers.asStateFlow()

    private val visibleItems = mutableSetOf<Int>()

    init {
        startTicking()
    }

    fun updateVisibleItems(indexes: List<Int>) {
        visibleItems.clear()
        visibleItems.addAll(indexes)
    }

    private fun startTicking() {
        viewModelScope.launch {
            while (isActive) {
                if (visibleItems.isNotEmpty()) {
                    _timers.update { current ->
                        current.mapIndexed { index, value ->
                            if (visibleItems.contains(index)) value + 1 else value
                        }
                    }
                }
                delay(1000)
            }
        }
    }
}