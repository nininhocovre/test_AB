package com.ab.exerciseabinbev.data

import androidx.lifecycle.LiveData
import kotlin.math.max

class OrderRepository(val orderDao: OrderDao) {
    val lastOrder: LiveData<List<OrderWithProduct>> = orderDao.getOrderById(10)

    suspend fun quantityChanged(order: Order, newValue: Int) {
        if (order.quantity != newValue) {
            val newOrder = Order(order.id, order.productId, newValue, order.purchased)
            orderDao.insert(newOrder)
        }
    }

    suspend fun adjustQuantity(order: Order, diff: Int) {
        val newValue = max(0, order.quantity + diff)
        val newOrder = Order(order.id, order.productId, newValue, order.purchased)
        orderDao.insert(newOrder)
    }

    suspend fun onAddedChanged(order: Order) {
        orderDao.insert(Order(order.id, order.productId, order.quantity, !order.purchased))
    }

}