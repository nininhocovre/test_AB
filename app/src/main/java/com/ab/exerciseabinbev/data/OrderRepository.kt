package com.ab.exerciseabinbev.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope

class OrderRepository(val orderDao: OrderDao) {

    val lastOrder: LiveData<OrderWithProduct> = orderDao.getOrderById(10)
}