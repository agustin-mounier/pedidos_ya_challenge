package com.example.pedidosyachallenge.views

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
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
import com.example.pedidosyachallenge.repository.remote.ErrorType
import com.example.pedidosyachallenge.repository.remote.PedidosYaService
import com.example.pedidosyachallenge.viewmodels.PedidosYaFeedViewModel
import com.example.pedidosyachallenge.views.PedidosYaLocationMapActivity.Companion.PositionExtra
import com.example.pedidosyachallenge.views.adapters.RestaurantsAdapter
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.py_feed_layout.*
import javax.inject.Inject

class PedidosYaFeedActivity : DaggerAppCompatActivity() {

    companion object {
        const val SelectedLocation = "selected_location"
        const val LocationRequestCode = 101
        const val RequestAccessLocationServices = 100
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PedidosYaFeedViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[PedidosYaFeedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.py_feed_layout)
        checkLocationPermissions()
        initView()
        initObservers()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == RequestAccessLocationServices) {
            if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) { // Permission was granted
                getDeviceCoordinates()
            } else { // Permission was not granted
                showEmpyState()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LocationRequestCode && resultCode == Activity.RESULT_OK) {
            val selectedPosition = data?.extras!![PositionExtra] as LatLng
            val point = Point(selectedPosition.latitude, selectedPosition.longitude)
            viewModel.setPoint(point)
            viewModel.clearRestaurants()
            viewModel.fetchRestaurants()
        }
    }

    private fun checkLocationPermissions() {
        val accessFineLocation = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
        val accessCoarseLocation = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
        if (accessFineLocation != PERMISSION_GRANTED && accessCoarseLocation != PERMISSION_GRANTED) {
            val permissions = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            ActivityCompat.requestPermissions(this, permissions, RequestAccessLocationServices)
            return
        }
        getDeviceCoordinates()
    }

    private fun getDeviceCoordinates() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest: LocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(60000)
            .setFastestInterval(10000)

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    it.locations.forEach { location ->
                        val point = Point(location.latitude, location.longitude)
                        viewModel.setPoint(point)
                        viewModel.fetchRestaurants()
                        fusedLocationClient.removeLocationUpdates(this)
                    }
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun initObservers() {
        viewModel.isLoading().observe(this, Observer {
            if (it && viewModel.getCurrentPage() == 0) showLoading() else hideLoading()
        })

        viewModel.getErrorType().observe(this, Observer {
            if (ErrorType.SNACKBAR == it) {
                Snackbar.make(restaurant_feed, getString(R.string.restaurants_error_snack), Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.getRestaurants().observe(this, Observer {
            if (it.isEmpty()) {
                showEmpyState()
            } else {
                val positionStart = viewModel.getCurrentPage() * PedidosYaService.PageSize
                val count = it.size - positionStart
                restaurant_feed.adapter?.notifyItemRangeInserted(positionStart, count)
                if (viewModel.getCurrentPage() == 0) restaurant_feed.scheduleLayoutAnimation()
            }
        })
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this)
        val adapter = RestaurantsAdapter(viewModel.getRestaurants(), viewModel.isLoading())
        val scrollListener = InfiniteScrollViewListener(layoutManager, viewModel::fetchRestaurants)
        restaurant_feed.layoutManager = layoutManager
        restaurant_feed.addOnScrollListener(scrollListener)
        restaurant_feed.adapter = adapter

        map_button.setOnClickListener {
            val intent = Intent(this, PedidosYaRestaurantsMapActivity::class.java)
            intent.putExtra(SelectedLocation, viewModel.getPoint())
            startActivity(intent)
        }

        change_location_button.setOnClickListener {
            val intent = Intent(this, PedidosYaLocationMapActivity::class.java)
            intent.putExtra(SelectedLocation, viewModel.getPoint())
            startActivityForResult(intent, LocationRequestCode)
        }
    }

    private fun showLoading() {
        loading_spinner.visibility = View.VISIBLE
        loading_text.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading_spinner.visibility = View.GONE
        loading_text.visibility = View.GONE
    }

    private fun showEmpyState() {
        empty_state.visibility = View.VISIBLE
    }
}
