package com.ab.exerciseabinbev.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.exerciseabinbev.data.*
import kotlinx.coroutines.launch
import kotlin.math.max

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val orderDao: OrderDao
    val orderRepository: OrderRepository
    var lastOrder: LiveData<List<OrderWithProduct>>

    init {
        val database: MainDatabase = MainDatabase.getDatabase(application, viewModelScope)
        orderDao = database.orderDao()
        orderRepository = OrderRepository(orderDao)
        lastOrder = orderRepository.lastOrder
    }

    fun quantityChanged(orderWithProduct: OrderWithProduct, newValue: Int) {
        if (orderWithProduct.order.quantity != newValue) {
            viewModelScope.launch {
                val order = orderWithProduct.order
                val newOrder = Order(order.id, order.productId, newValue)
                orderDao.insert(newOrder)
            }
        }
    }

    fun adjustQuantity(orderWithProduct: OrderWithProduct, diff: Int) {
        viewModelScope.launch {
            val order = orderWithProduct.order
            val newValue = max(0, order.quantity + diff)
            val newOrder = Order(order.id, order.productId, newValue)
            orderDao.insert(newOrder)
            lastOrder = orderRepository.lastOrder
        }
    }

}