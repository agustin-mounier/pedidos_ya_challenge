package com.example.pedidosyachallenge.views

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedidosyachallenge.FakeApplication
import com.example.pedidosyachallenge.R
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.models.Restaurant
import com.example.pedidosyachallenge.repository.remote.ErrorType
import com.example.pedidosyachallenge.viewmodels.PedidosYaFeedViewModel
import com.example.pedidosyachallenge.viewmodels.ViewModelFactory
import com.example.pedidosyachallenge.views.adapters.RestaurantsAdapter
import com.google.android.gms.maps.model.LatLng
import com.nhaarman.mockitokotlin2.*
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.validateMockitoUsage
import com.nhaarman.mockitokotlin2.verify
import kotlinx.android.synthetic.main.py_feed_layout.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(application = FakeApplication::class) // Provide a fake application to disable injection.
class PedidosYaFeedActivityTest {

    @Mock
    private lateinit var viewModelFactory: ViewModelFactory
    @Mock
    private lateinit var viewModel: PedidosYaFeedViewModel
    @Mock
    private lateinit var isLoading: LiveData<Boolean>
    @Mock
    private lateinit var errorType: LiveData<ErrorType>
    @Mock
    private lateinit var restaurants: LiveData<List<Restaurant>>
    @Mock
    private lateinit var point: Point

    private lateinit var view: PedidosYaFeedActivity

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val activityController = Robolectric.buildActivity(PedidosYaFeedActivity::class.java)

        `when`(viewModel.getRestaurants()).thenReturn(restaurants)
        `when`(viewModel.getErrorType()).thenReturn(errorType)
        `when`(viewModel.isLoading()).thenReturn(isLoading)
        `when`(viewModel.getPoint()).thenReturn(point)
        `when`(viewModelFactory.create(PedidosYaFeedViewModel::class.java)).thenReturn(viewModel)

        view = activityController.get()
        view.viewModelFactory = viewModelFactory
        activityController.create()
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
        verify(restaurants).observe(eq(view), any()) //TODO: capture the observer and test it
        verify(isLoading).observe(eq(view), any())
        verify(errorType).observe(eq(view), any())
    }

    @Test
    fun onRequestPermissionsResult_permissionsGranted() {
        view = spy(view)
        val grantResult = IntArray(1)
        grantResult[0]=PERMISSION_GRANTED
        val permission = arrayOf<String>()

        view.onRequestPermissionsResult(PedidosYaFeedActivity.RequestAccessLocationServices, permission, grantResult)

        verify(view).getDeviceCoordinates()
    }

    @Test
    fun onRequestPermissionsResult_permissionsNotGranted() {
        view = spy(view)
        val grantResult = IntArray(1)
        grantResult[0] = PERMISSION_DENIED
        val permission = arrayOf<String>()

        view.onRequestPermissionsResult(PedidosYaFeedActivity.RequestAccessLocationServices, permission, grantResult)

        verify(view).showEmpyState()
    }

    @Test
    fun onActivityResult() {
        val position = LatLng(0.0, 0.0)

        view.startActivityForResult(Intent(view, PedidosYaMapActivity::class.java),
            PedidosYaFeedActivity.LocationRequestCode)

        shadowOf(view).receiveResult(
            Intent(view, PedidosYaMapActivity::class.java),
            Activity.RESULT_OK,
            Intent().putExtra(PedidosYaMapActivity.PositionExtra, position))

        verify(viewModel).setPoint(Point(position.latitude,position.longitude))
        verify(viewModel).clearRestaurants()
        verify(viewModel).fetchRestaurants()
    }

    @Test
    fun onCreateOptionsMenu() {
        view = spy(view)
        val menu = mock(Menu::class.java)
        val menuInflater = mock(MenuInflater::class.java)
        `when`(view.menuInflater).thenReturn(menuInflater)

        view.onCreateOptionsMenu(menu)

        verify(menuInflater).inflate(R.menu.feed_menu, menu)
    }

    @Test
    fun onOptionsItemSelected() {
        view = spy(view)
        val captor = argumentCaptor<Intent>()
        val item = mock(MenuItem::class.java)
        `when`(item.itemId).thenReturn(R.id.map_icon)

        view.onOptionsItemSelected(item)

        verify(viewModel).getPoint()
        verify(view).startActivityForResult(captor.capture(), eq(PedidosYaFeedActivity.LocationRequestCode))
        val intent = captor.firstValue
        assertEquals(point, intent.extras?.get(PedidosYaFeedActivity.SelectedLocation) as Point)
    }
}