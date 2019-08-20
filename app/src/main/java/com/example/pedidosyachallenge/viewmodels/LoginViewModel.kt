package com.example.pedidosyachallenge.viewmodels

import android.content.Intent
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModel
import com.example.pedidosyachallenge.PedidosYaApplication
import com.example.pedidosyachallenge.models.AccessTokenResponse
import com.example.pedidosyachallenge.services.PedidosYaAuthService
import com.example.pedidosyachallenge.views.PedidosYaFeedActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authService: PedidosYaAuthService,
    private val app: PedidosYaApplication
) : ViewModel()  {
    companion object {
        const val AccessToken = "access_token"
    }

    fun authenticate(clientId: String, clientSecret: String) {

        //TODO Validate user input.


        authService.getAccessToken(clientId, clientSecret).enqueue(object: Callback<AccessTokenResponse> {
            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                // TODO: show error page.
            }

            override fun onResponse(call: Call<AccessTokenResponse>, response: Response<AccessTokenResponse>) {
                response.body()?.let {
                    saveAccessToken(it.access_token)
                    goToRestaurantFeed()
                }
            }
        })
    }

    private fun saveAccessToken(accessToken: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(app)
        val editor = preferences.edit()
        editor.putString(AccessToken, accessToken)
        editor.apply()
    }

    private fun goToRestaurantFeed() {
        val intent = Intent(app, PedidosYaFeedActivity::class.java)
        app.startActivity(intent)
    }
}