package com.test.volvoxcase.network

import com.test.volvoxcase.BuildConfig
import com.test.volvoxcase.model.NewsResponse
import com.test.volvoxcase.utils.Const
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String = Const.countryTR,
        @Query("category") category: String = "technology",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<NewsResponse>
}