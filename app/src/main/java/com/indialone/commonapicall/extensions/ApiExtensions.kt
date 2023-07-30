package com.indialone.commonapicall.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <T : Any> ViewModel.scopeForLiveData(liveData: MutableLiveData<T>, execute: suspend () -> T) {
    viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            execute()
        }
        liveData.postValue(response)
    }
}

fun <T : Any> AppCompatActivity.observeLiveData(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(this, observer)
}

fun <T : Any> Fragment.observeLiveData(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(viewLifecycleOwner, observer)
}

fun <T : Any> AppCompatActivity.observeStateFlow(
    stateFlow: StateFlow<T?>,
    collector: FlowCollector<T?>
) {
    lifecycleScope.launchWhenStarted {
        stateFlow.collect(collector)
    }
}

fun <T : Any> Fragment.observeStateFlow(stateFlow: StateFlow<T?>, collector: FlowCollector<T?>) {
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        stateFlow.collect(collector)
    }
}

fun <T : Any> AppCompatActivity.observeSharedFlow(
    sharedFlow: SharedFlow<T?>,
    collector: FlowCollector<T?>
) {
    lifecycleScope.launchWhenStarted {
        sharedFlow.collect(collector)
    }
}

fun <T : Any> Fragment.observeSharedFlow(sharedFlow: SharedFlow<T?>, collector: FlowCollector<T?>) {
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        sharedFlow.collect(collector)
    }
}