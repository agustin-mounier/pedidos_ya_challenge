package com.example.pedidosyachallenge.repository.remote

import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pedidosyachallenge.PedidosYaApplication
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.models.RestaurantsResponse
import com.example.pedidosyachallenge.viewmodels.LoginViewModel
import javax.inject.Inject

class PedidosYaServiceImpl @Inject constructor(
    private val app: PedidosYaApplication,
    private val pyService: PedidosYaService
) : PedidosYaServiceApi {

    private val isLoading = MutableLiveData(false)
    private val requestErrorAction = MutableLiveData(ErrorType.NONE)
    private val accessToken: String

    init {
        val prefs = PreferenceManager.getDefaultSharedPreferences(app)
        accessToken = prefs.getString(LoginViewModel.AccessToken, "")
    }

    override fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    override fun getRestaurants(point: Point, page: Int, onSuccessFun: (RestaurantsResponse?) -> Unit) {
        val callback =
            PedidosYaCallback<RestaurantsResponse>(
                isLoading,
                requestErrorAction,
                ErrorType.SNACKBAR,
                onSuccessFun
            )
        val restaurantsCall = pyService.getRestaurants(accessToken, point.toString(),3,page * PedidosYaService.PageSize, PedidosYaService.PageSize)
        restaurantsCall.enqueue(callback)
    }

}