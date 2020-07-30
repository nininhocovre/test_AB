package com.ab.exerciseabinbev.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ab.exerciseabinbev.data.MainDatabase
import com.ab.exerciseabinbev.data.OrderRepository
import com.ab.exerciseabinbev.data.OrderWithProduct
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val orderRepository: OrderRepository
    val lastOrder: LiveData<List<OrderWithProduct>>

    init {
        val database: MainDatabase = MainDatabase.getDatabase(application, viewModelScope)
        val orderDao = database.orderDao()
        orderRepository = OrderRepository(orderDao)
        lastOrder = orderRepository.lastOrder
    }

    fun quantityChanged(orderWithProduct: OrderWithProduct, newValue: Int) {
        viewModelScope.launch {
            orderRepository.quantityChanged(orderWithProduct.order, newValue)
        }
    }

    fun adjustQuantity(orderWithProduct: OrderWithProduct, diff: Int) {
        viewModelScope.launch {
            orderRepository.adjustQuantity(orderWithProduct.order, diff)
        }
    }

    fun onAddButtonClicked(orderWithProduct: OrderWithProduct) {
        viewModelScope.launch {
            orderRepository.onAddedChanged(orderWithProduct.order)
        }
    }

}