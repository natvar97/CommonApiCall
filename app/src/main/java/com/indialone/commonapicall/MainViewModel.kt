package com.indialone.commonapicall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indialone.commonapicall.network.NetworkResult
import com.indialone.commonapicall.repository.impls.MainRepositoryImpl
import com.indialone.commonapicall.states.RecipesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepositoryImpl
): ViewModel() {

    private val _response = MutableStateFlow(RecipesUiState.SearchedRecipeState(null))
    val response: StateFlow<RecipesUiState.SearchedRecipeState> = _response

    private val _errorSharedFlow = MutableSharedFlow<RecipesUiState.Error>()
    val errorSharedFlow: SharedFlow<RecipesUiState.Error> = _errorSharedFlow

    private val _isLoading = MutableStateFlow(RecipesUiState.Loading(false))
    val isLoading: StateFlow<RecipesUiState.Loading> = _isLoading

    init {
        viewModelScope.launch {
            search()
        }
    }

    private suspend fun search() {
        repository.search().collectLatest { apiResponse ->
            when(apiResponse) {
                is NetworkResult.Loading -> {
                    _isLoading.emit(RecipesUiState.Loading(isLoading = true))
                }
                is NetworkResult.Success -> {
                    _isLoading.emit(RecipesUiState.Loading(isLoading = false))
                    _response.update {
                        _response.value.copy(response = apiResponse.data)
                    }
                }
                is NetworkResult.Error -> {
                    _isLoading.emit(RecipesUiState.Loading(isLoading = false))
                    // handle another case for error
                    _errorSharedFlow.emit(RecipesUiState.Error(apiResponse.code, apiResponse.message))
                }
                is NetworkResult.Exception -> {
                    _isLoading.emit(RecipesUiState.Loading(isLoading = false))
                    // handler another state flow for exception
                }
            }
        }
    }

}