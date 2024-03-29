package com.example.pedidosyachallenge.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.pedidosyachallenge.BuildConfig
import com.example.pedidosyachallenge.R
import com.example.pedidosyachallenge.repository.remote.ErrorType
import com.example.pedidosyachallenge.utils.NetworkUtils
import com.example.pedidosyachallenge.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.login_layout.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        initView()
        initObservers()
    }

    private fun initView() {

        login_button.setOnClickListener {
            if (!NetworkUtils.isNetworkAvailable(this)) {
                goToRestaurantsActivity()
            } else {
                val clientId = user.text.toString()
                val clientSecret = pass.text.toString()
                viewModel.authenticate(clientId, clientSecret)
            }
        }
    }

    private fun initObservers() {
        viewModel.isLoading().observe(this, Observer {
            if (it) showLoading() else hideLoading()
        })

        viewModel.getErrorType().observe(this, Observer {
            if (ErrorType.SNACKBAR == it) {
                Snackbar.make(logo, getString(R.string.authentication_error), Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.getStartActivity().observe(this, Observer {
            if (it) goToRestaurantsActivity()
        })
    }

    fun goToRestaurantsActivity() {
        val intent = Intent(this, PedidosYaFeedActivity::class.java)
        startActivity(intent)
    }

    fun showLoading() {
        loading_spinner.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loading_spinner.visibility = View.INVISIBLE
    }
}