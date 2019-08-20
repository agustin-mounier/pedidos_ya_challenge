package com.example.pedidosyachallenge.views

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedidosyachallenge.R
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.repository.remote.PedidosYaService
import com.example.pedidosyachallenge.viewmodels.PedidosYaFeedViewModel
import com.google.android.gms.location.*
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.py_feed_layout.*
import javax.inject.Inject


class PedidosYaFeedActivity: DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PedidosYaFeedViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[PedidosYaFeedViewModel::class.java]
    }

    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var scrollListener: InfiniteScrollViewListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.py_feed_layout)
        getCoordinates()
        initView()
        initObservers()
    }


    private fun getCoordinates() { //TODO: refactor a little.

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION),
                100)
            return
        }

        val mLocationRequest: LocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(60000)
            .setFastestInterval(10000)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    val point = Point(location.latitude, location.longitude)
                    viewModel.setPoint(point)
                    viewModel.fetchRestaurants()
                    fusedLocationClient.removeLocationUpdates(locationCallback)
                }
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, null)
    }


    private fun initObservers() {
        viewModel.isLoading().observe(this, Observer {
            if (it && viewModel.getCurrentPage() == 0) showLoading() else hideLoading()
        })

        viewModel.getRestaurants().observe(this, Observer {
            val positionStart = viewModel.getCurrentPage() * PedidosYaService.PageSize
            restaurant_feed.adapter?.notifyItemRangeInserted(positionStart, PedidosYaService.PageSize)
            if (viewModel.getCurrentPage() == 0) restaurant_feed.scheduleLayoutAnimation()
        })
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this)
        val adapter = RestaurantsAdapter(viewModel.getRestaurants(), viewModel.isLoading())
        scrollListener = InfiniteScrollViewListener(layoutManager, viewModel::fetchRestaurants)
        restaurant_feed.layoutManager = layoutManager
        restaurant_feed.addOnScrollListener(scrollListener)
        restaurant_feed.adapter = adapter
    }

    private fun showLoading() {
        loading_spinner.visibility = View.VISIBLE
        loading_text.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading_spinner.visibility = View.GONE
        loading_text.visibility = View.GONE
    }
}