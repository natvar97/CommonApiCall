package com.indialone.commonapicall

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.indialone.commonapicall.databinding.ActivityMainBinding
import com.indialone.commonapicall.extensions.observeSharedFlow
import com.indialone.commonapicall.extensions.observeStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModelObservers()
    }

    private fun viewModelObservers() {
        observeStateFlow(mViewModel.response) {
            it?.let {
                Log.e(TAG, "viewModelObservers: response -> ${it}")
            }
        }
        observeSharedFlow(mViewModel.errorSharedFlow) {
            it?.let {
                Log.e(TAG, "viewModelObservers: error -> ${it.code}, message -> ${it.message}")
            }
        }
        observeStateFlow(mViewModel.isLoading) {
            it?.let {
                if (it.isLoading) {
                    Log.e(TAG, "viewModelObservers: Loading Data")
                } else {
                    Log.e(TAG, "viewModelObservers: Data Loaded")
                }
            }
        }
    }
}