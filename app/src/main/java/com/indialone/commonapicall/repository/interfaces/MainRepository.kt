package com.indialone.commonapicall.repository.interfaces

import com.indialone.commonapicall.models.search.SearchResponse
import com.indialone.commonapicall.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun search(): Flow<NetworkResult<SearchResponse>>

}