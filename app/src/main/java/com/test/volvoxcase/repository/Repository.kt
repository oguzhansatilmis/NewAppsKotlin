package com.test.volvoxcase.repository

import android.util.Log
import com.test.volvoxcase.model.NewsResponse
import com.test.volvoxcase.network.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class Repository @Inject constructor(private val newsApiService: NewsApiService) {
    suspend fun getResponse(): Flow<Response<NewsResponse>> = flow {

        val response = newsApiService.getNews()

        if (response.isSuccessful) {
            emit(response)
        } else {
            Log.e("Repository response error", "${response.code()}")
            emit(response)
        }

    }.catch { e ->
        Log.d("error", "$e")


    }.flowOn(Dispatchers.IO)
}