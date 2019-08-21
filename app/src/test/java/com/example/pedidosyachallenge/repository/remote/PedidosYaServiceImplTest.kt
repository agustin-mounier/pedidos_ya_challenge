package com.example.pedidosyachallenge.repository.remote

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pedidosyachallenge.PedidosYaApplication
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.models.RestaurantsResponse
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import retrofit2.Call

@RunWith(PowerMockRunner::class)
@PowerMockRunnerDelegate(MockitoJUnitRunner::class)
@PrepareForTest(PreferenceManager::class)
class PedidosYaServiceImplTest {

    @Mock
    private lateinit var service: PedidosYaService
    @Mock
    private lateinit var app: PedidosYaApplication
    @Mock
    private lateinit var sharedPreferences: SharedPreferences
    @Mock
    private lateinit var call: Call<RestaurantsResponse>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var serviceImpl: PedidosYaServiceImpl
    private val accessToken = "some token"
    private val country = 3

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        PowerMockito.mockStatic(PreferenceManager::class.java)
        `when`(sharedPreferences.getString(anyString(), anyString())).thenReturn(accessToken)
        `when`(PreferenceManager.getDefaultSharedPreferences(app)).thenReturn(sharedPreferences)
        `when`(service.getRestaurants(anyString(), anyString(), anyInt(), anyInt(), anyInt())).thenReturn(call)

        serviceImpl = PedidosYaServiceImpl(app, service)
    }

    @After
    fun tearDown() {
        validateMockitoUsage()
    }

    @Test
    fun isLoading() {
        assertNotNull(serviceImpl.isLoading())
        assertFalse(serviceImpl.isLoading().value!!)

        serviceImpl.getRestaurants(mock(Point::class.java), 0, {})
        assertTrue(serviceImpl.isLoading().value!!)
    }

    @Test
    fun getErrorType() {
        assertEquals(ErrorType.NONE, serviceImpl.getErrorType().value)
    }

    @Test
    fun getRestaurants() {
        val point = mock(Point::class.java)
        val page = 0
        val onSuccessFun: (RestaurantsResponse?) -> Unit = {}

        serviceImpl.getRestaurants(point, page, onSuccessFun)

        verify(service).getRestaurants(accessToken, point.toString(), country, 0, PedidosYaService.PageSize)
        verify(call).enqueue(any())
    }
}