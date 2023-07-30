package com.indialone.commonapicall.network

import com.indialone.commonapicall.models.search.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(ApiConstants.SEARCH_END_POINT)
    suspend fun searchRecipes(@Query("search") q: String): Response<SearchResponse>

}