package com.example.pedidosyachallenge.repository.local

import com.example.pedidosyachallenge.models.Restaurant
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.validateMockitoUsage
import com.nhaarman.mockitokotlin2.verify
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PedidosYaDaoImplTest {

    @Mock
    private lateinit var realm: Realm
    @Mock
    private lateinit var query: RealmQuery<Restaurant>
    @Mock
    private lateinit var results: RealmResults<Restaurant>
    @Mock
    private lateinit var restaurants: List<Restaurant>

    private lateinit var dao: PedidosYaDaoImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(query.findAll()).thenReturn(results)
        `when`(realm.where(Restaurant::class.java)).thenReturn(query)
        `when`(realm.copyFromRealm(results)).thenReturn(restaurants)

        dao = PedidosYaDaoImpl(realm)
    }

    @After
    fun tearDown() {
        validateMockitoUsage()
    }

    @Test
    fun retrieveRestaurants() {
        val retrievedRestaurants = dao.retrieveRestaurants()

        verify(realm).copyFromRealm(results)
        assertEquals(restaurants, retrievedRestaurants)
    }

    @Test
    fun persistRestaurants() {
        val captor = argumentCaptor<Realm.Transaction>()
        dao.persistRestaurants(restaurants)

        verify(realm).executeTransactionAsync(captor.capture())
        val transaction = captor.firstValue
        transaction.execute(realm)
        verify(realm).insertOrUpdate(restaurants)
    }
}