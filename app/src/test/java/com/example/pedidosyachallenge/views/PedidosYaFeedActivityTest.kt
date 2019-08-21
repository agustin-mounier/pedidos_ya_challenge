package com.example.pedidosyachallenge.views

import android.view.MenuItem
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedidosyachallenge.R
import com.example.pedidosyachallenge.viewmodels.PedidosYaFeedViewModel
import com.example.pedidosyachallenge.views.adapters.RestaurantsAdapter
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.validateMockitoUsage
import com.nhaarman.mockitokotlin2.verify
import kotlinx.android.synthetic.main.py_feed_layout.*
import org.codehaus.plexus.util.ReflectionUtils
import org.junit.*

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PedidosYaFeedActivityTest {

    private lateinit var view: PedidosYaFeedActivity

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        view = Robolectric.buildActivity(PedidosYaFeedActivity::class.java).create().get()

    }

    @After
    fun tearDown() {
        validateMockitoUsage()
    }

    @Test
    fun onCreate() {
        val layoutManager = view.restaurant_feed.layoutManager
        val adapter = view.restaurant_feed.adapter

        assertNotNull(layoutManager)
        assertNotNull(adapter)
        assertTrue(layoutManager is LinearLayoutManager)
        assertTrue(adapter is RestaurantsAdapter)
    }

    @Test
    fun onRequestPermissionsResult() {
    }

    @Test
    fun onActivityResult() {
    }

    @Test
    fun onCreateOptionsMenu() {
    }

    @Test
    fun onOptionsItemSelected() {
        val item = mock(MenuItem::class.java)
        `when`(item.itemId).thenReturn(R.id.map_icon)

        view.onOptionsItemSelected(item)

        verify(view).startActivityForResult(any(), any())
    }
}