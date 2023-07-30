package com.indialone.commonapicall.repository.impls

import com.indialone.commonapicall.models.search.SearchResponse
import com.indialone.commonapicall.network.ApiService
import com.indialone.commonapicall.network.BaseDataSource
import com.indialone.commonapicall.network.NetworkResult
import com.indialone.commonapicall.repository.interfaces.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : BaseDataSource(), MainRepository {
    override fun search(): Flow<NetworkResult<SearchResponse>> = flow {
        val response = handleApi { apiService.searchRecipes("pizza") }
        emit(response)
    }
}