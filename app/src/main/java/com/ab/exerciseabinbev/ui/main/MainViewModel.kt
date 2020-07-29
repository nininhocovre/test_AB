package com.ab.exerciseabinbev.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.exerciseabinbev.data.MainDatabase
import com.ab.exerciseabinbev.data.OrderDao
import com.ab.exerciseabinbev.data.OrderRepository
import com.ab.exerciseabinbev.data.OrderWithProduct

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val orderDao: OrderDao
    val orderRepository: OrderRepository
    val lastOrder: LiveData<OrderWithProduct>

    init {
        val database: MainDatabase = MainDatabase.getDatabase(application, viewModelScope)
        orderDao = database.orderDao()
        orderRepository = OrderRepository(orderDao)
        lastOrder = orderRepository.lastOrder
    }


}