package com.example.pedidosyachallenge.viewmodels

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pedidosyachallenge.PedidosYaApplication
import com.example.pedidosyachallenge.models.AccessTokenResponse
import com.example.pedidosyachallenge.repository.remote.ErrorType
import com.example.pedidosyachallenge.repository.remote.PedidosYaCallback
import com.example.pedidosyachallenge.services.PedidosYaAuthService
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    app: PedidosYaApplication,
    private val authService: PedidosYaAuthService
) : ViewModel() {
    companion object {
        const val AccessToken = "access_token"
    }

    private val isLoading = MutableLiveData<Boolean>()
    private val errorType = MutableLiveData<ErrorType>()
    private val startActivity = MutableLiveData<Boolean>()
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)

    fun authenticate(clientId: String, clientSecret: String) {
        //TODO Validate user input.

        val callback = PedidosYaCallback<AccessTokenResponse>(isLoading, errorType, ErrorType.SNACKBAR) {
            saveAccessToken(it!!.access_token)
            startActivity.value = true
        }
        authService.getAccessToken(clientId, clientSecret).enqueue(callback)
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun getErrorType(): LiveData<ErrorType> {
        return errorType
    }

    fun getStartActivity(): LiveData<Boolean> {
        return startActivity
    }

    private fun saveAccessToken(accessToken: String) {
        val editor = preferences.edit()
        editor.putString(AccessToken, accessToken)
        editor.apply()
    }
}