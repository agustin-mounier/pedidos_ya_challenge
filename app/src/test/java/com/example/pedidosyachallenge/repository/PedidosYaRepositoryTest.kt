package com.example.pedidosyachallenge.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.models.Restaurant
import com.example.pedidosyachallenge.models.RestaurantsResponse
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
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class PedidosYaRepositoryTest {

    @Mock
    private lateinit var service: PedidosYaServiceApi

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: PedidosYaRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = PedidosYaRepository(service)
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
    fun fetchRestaurants() {
        val captor = argumentCaptor<(RestaurantsResponse?) -> Unit>()
        val point = mock(Point::class.java)
        val page = 0

        repository.fetchRestaurants(point, page)

        verify(service).getRestaurants(eq(point), eq(page), captor.capture())

        val response = getRestaurantResponse()
        captor.firstValue.invoke(response)

        assertEquals(response.data, repository.getRestaurants().value)
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
}