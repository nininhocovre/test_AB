package com.ab.exerciseabinbev.data

import androidx.lifecycle.LiveData
import kotlin.math.max

class OrderRepository(val orderDao: OrderDao) {
    val lastOrder: LiveData<List<OrderWithProduct>> = orderDao.getOrderById(10)

    suspend fun quantityChanged(orderWithProduct: OrderWithProduct, newValue: Int) {
        if (orderWithProduct.order.quantity != newValue) {
            val order = orderWithProduct.order
            val newOrder = Order(order.id, order.productId, newValue)
            orderDao.insert(newOrder)
        }
    }

    suspend fun adjustQuantity(orderWithProduct: OrderWithProduct, diff: Int) {
        val order = orderWithProduct.order
        val newValue = max(0, order.quantity + diff)
        val newOrder = Order(order.id, order.productId, newValue)
        orderDao.insert(newOrder)
    }

}