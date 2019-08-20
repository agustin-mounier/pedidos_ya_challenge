package com.example.pedidosyachallenge.views

import android.os.Bundle
import android.text.Editable
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.pedidosyachallenge.BuildConfig
import com.example.pedidosyachallenge.R
import com.example.pedidosyachallenge.viewmodels.LoginViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.login_layout.*
import javax.inject.Inject

class LoginActivity: DaggerAppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        user.setText(BuildConfig.ClientId, TextView.BufferType.EDITABLE)
        pass.setText(BuildConfig.ClientSecret, TextView.BufferType.EDITABLE)

        login_button.setOnClickListener {
            val clientId = user.text.toString()
            val clientSecret = pass.text.toString()
            viewModel.authenticate(clientId, clientSecret)
        }
    }

}