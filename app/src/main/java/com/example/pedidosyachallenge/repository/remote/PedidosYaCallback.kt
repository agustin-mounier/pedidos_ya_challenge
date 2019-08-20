package com.example.pedidosyachallenge.repository.remote

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PedidosYaCallback<T>(
    private val isLoading: MutableLiveData<Boolean>,
    private val requestErrorType: MutableLiveData<ErrorType>,
    val errorType: ErrorType,
    val onSuccessFun: (T?) -> Unit
) : Callback<T> {

    init {
        isLoading.value = true
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        isLoading.value = false
        requestErrorType.value = errorType
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        isLoading.value = false
        onSuccessFun(response.body())
    }
}