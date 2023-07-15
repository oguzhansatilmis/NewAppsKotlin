package com.test.volvoxcase.viewmodel

import com.test.volvoxcase.model.Result
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.volvoxcase.model.NewsResponse
import com.test.volvoxcase.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _newsResponseState = MutableStateFlow<Result<NewsResponse>>(Result.Loading())

    val newsResponseState: StateFlow<Result<NewsResponse>> = _newsResponseState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _newsResponseState.value = Result.Loading()
            try {
                repository.getResponse()
                    .collect { response ->
                        if (response.isSuccessful) {
                            val body = response.body()
                            _newsResponseState.value = Result.Success(body)
                        } else {
                            _newsResponseState.value = Result.Error("Error fetching data")
                        }
                    }
            } catch (e: Exception) {
                _newsResponseState.value = Result.Error("An error occurred")
            }
        }
    }
}