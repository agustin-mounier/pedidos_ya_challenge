package com.example.pedidosyachallenge.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pedidosyachallenge.PedidosYaApplication
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.models.Restaurant
import com.example.pedidosyachallenge.models.RestaurantsResponse
import com.example.pedidosyachallenge.repository.local.PedidosYaDao
import com.example.pedidosyachallenge.repository.remote.PedidosYaServiceApi
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.validateMockitoUsage
import com.nhaarman.mockitokotlin2.verify
import org.codehaus.plexus.util.ReflectionUtils
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class PedidosYaRepositoryTest {

    @Mock
    private lateinit var service: PedidosYaServiceApi
    @Mock
    private lateinit var dao: PedidosYaDao
    @Mock
    private lateinit var app: PedidosYaApplication

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val restaurants = listOf(mock(Restaurant::class.java), mock(Restaurant::class.java), mock(Restaurant::class.java))
    private lateinit var repository: PedidosYaRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(dao.retrieveRestaurants()).thenReturn(restaurants)

        repository = PedidosYaRepository(app, service, dao)
    }

    @After
    fun tearDown() {
        validateMockitoUsage()
    }

    @Test
    fun isLoading() {
        repository.isLoading()

        verify(service).isLoading()
    }

    @Test
    fun getErrorType() {
        repository.getErrorType()

        verify(service).getErrorType()
    }

    @Test
    fun getRestaurants() {
        val restaurants = repository.getRestaurants()

        assertNotNull(restaurants)
        assertTrue(restaurants is LiveData<List<Restaurant>>)
    }

    @Test
    fun fetchRestaurants_withConectivity() {
        mockNetworkEnable(true)
        val captor = argumentCaptor<(RestaurantsResponse?) -> Unit>()
        val point = mock(Point::class.java)
        val page = 0

        repository.fetchRestaurants(point, page)

        verify(service).getRestaurants(eq(point), eq(page), captor.capture())

        val response = getRestaurantResponse()
        captor.firstValue.invoke(response)

        verify(dao).persistRestaurants(response.data)
        assertEquals(response.data, repository.getRestaurants().value)
    }

    @Test
    fun fetchRestaurants_withoutConnectivity() {
        mockNetworkEnable(false)
        val point = mock(Point::class.java)
        val page = 0

        repository.fetchRestaurants(point, page)

        verify(dao).retrieveRestaurants()
        assertEquals(restaurants, repository.getRestaurants().value)
    }

    @Test
    fun clearRestaurants() {
        val restaurants = mock(MutableList::class.java) as MutableList<Restaurant>
        ReflectionUtils.setVariableValueInObject(repository, "restaurants", restaurants)

        repository.clearRestaurants()

        verify(restaurants).clear()
    }

    private fun getRestaurantResponse(): RestaurantsResponse {
        val data = listOf(mock(Restaurant::class.java), mock(Restaurant::class.java), mock(Restaurant::class.java))
        return RestaurantsResponse(10, "", 10, 10, data)
    }

    private fun mockNetworkEnable(enable: Boolean) {
        val conectivityManager = mock(ConnectivityManager::class.java)
        val networkInfo = mock(NetworkInfo::class.java)
        `when`(networkInfo.isConnected).thenReturn(enable)
        `when`(conectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        `when`(app.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(conectivityManager)
    }
}