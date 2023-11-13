package com.example.avitorecyclerview121123

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class NumberViewModel : ViewModel() {

    private val _numberList: MutableStateFlow<List<Int>> =
        MutableStateFlow(getDefaultList(count = 16))
    val numberList: StateFlow<List<Int>> = _numberList.asStateFlow()

    init {
        addElement()
    }

    private fun addElement() {
        var startNumber = numberList.value.last()
        viewModelScope.launch(IO) {
            while (true) {
                delay(2000)
                val randomIndex = Random.nextInt(0, _numberList.value.size - 1)
                _numberList.update { currentList ->
                    currentList.mutate { it.add(randomIndex, ++startNumber) }
                }
            }
        }
    }

    fun removeElement(number: Int) {
        _numberList.update { currentList ->
            currentList.mutate { it.remove(number) }
        }
    }

    private fun <T> List<T>.mutate(function: (MutableList<T>) -> Unit): List<T> {
        return toMutableList().apply {
            function(this)
        }
    }

    private fun getDefaultList(count: Int): List<Int> {
        return List(count) { index ->
            index + 1
        }
    }
}



