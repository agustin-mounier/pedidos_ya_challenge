package com.example.pedidosyachallenge.viewmodels

import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.repository.PedidosYaRepositoryApi
import com.nhaarman.mockitokotlin2.validateMockitoUsage
import com.nhaarman.mockitokotlin2.verify
import org.codehaus.plexus.util.ReflectionUtils
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class PedidosYaFeedViewModelTest {

    @Mock
    lateinit var repository: PedidosYaRepositoryApi

    private lateinit var viewModel: PedidosYaFeedViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = PedidosYaFeedViewModel(repository)
    }

    @After
    fun tearDown() {
        validateMockitoUsage()
    }

    @Test
    fun getCurrentPage() {
        val nextPage = 1
        ReflectionUtils.setVariableValueInObject(viewModel, "currentPage", nextPage)

        assertEquals(nextPage - 1, viewModel.getCurrentPage())
    }

    @Test
    fun isLoading() {
        viewModel.isLoading()

        verify(repository).isLoading()
    }

    @Test
    fun getErrorType() {
        viewModel.getErrorType()

        verify(repository).getErrorType()
    }

    @Test
    fun getRestaurants() {
        viewModel.getRestaurants()

        verify(repository).getRestaurants()
    }

    @Test
    fun fetchRestaurants() {
        val point = mock(Point::class.java)
        viewModel.setPoint(point)

        viewModel.fetchRestaurants()

        verify(repository).fetchRestaurants(point, viewModel.getCurrentPage())
    }

    @Test
    fun clearRestaurants() {
        viewModel.clearRestaurants()

        verify(repository).clearRestaurants()
        assertEquals(0, viewModel.getCurrentPage())
    }
}