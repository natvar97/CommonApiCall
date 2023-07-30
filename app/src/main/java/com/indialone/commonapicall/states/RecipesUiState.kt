package com.indialone.commonapicall.states

import com.indialone.commonapicall.models.search.SearchResponse

sealed class RecipesUiState {
    data class SearchedRecipeState(val response: SearchResponse?) : RecipesUiState()
    data class Error(val code: Int?, val message: String?) : RecipesUiState()
}
