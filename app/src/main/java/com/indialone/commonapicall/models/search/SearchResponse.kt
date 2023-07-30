package com.indialone.commonapicall.models.search

data class SearchResponse(
    var status: String? = null,
    var results: Int? = null,
    var data: SearchData? = null
)

data class SearchData(
    var recipes: List<RecipesItem?>? = null
)