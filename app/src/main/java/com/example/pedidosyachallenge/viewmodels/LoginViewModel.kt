package com.example.pedidosyachallenge.viewmodels

import android.content.Intent
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pedidosyachallenge.PedidosYaApplication
import com.example.pedidosyachallenge.models.AccessTokenResponse
import com.example.pedidosyachallenge.repository.remote.ErrorType
import com.example.pedidosyachallenge.repository.remote.PedidosYaCallback
import com.example.pedidosyachallenge.services.PedidosYaAuthService
import com.example.pedidosyachallenge.views.PedidosYaFeedActivity
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authService: PedidosYaAuthService,
    private val app: PedidosYaApplication
) : ViewModel() {
    companion object {
        const val AccessToken = "access_token"
    }

    private val isLoading = MutableLiveData<Boolean>()
    private val errorType = MutableLiveData<ErrorType>()


    fun authenticate(clientId: String, clientSecret: String) {
        //TODO Validate user input.

        val callback = PedidosYaCallback<AccessTokenResponse>(isLoading, errorType, ErrorType.SNACKBAR) {
            saveAccessToken(it!!.access_token)
            goToRestaurantFeed()
        }
        authService.getAccessToken(clientId, clientSecret).enqueue(callback)
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun getErrorType(): LiveData<ErrorType> {
        return errorType
    }

    private fun saveAccessToken(accessToken: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(app)
        val editor = preferences.edit()
        editor.putString(AccessToken, accessToken)
        editor.apply()
    }

    private fun getAccessToken(): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(app)
        return preferences.getString(AccessToken, null)
    }

    private fun goToRestaurantFeed() {
        val intent = Intent(app, PedidosYaFeedActivity::class.java)
        app.startActivity(intent)
    }
}