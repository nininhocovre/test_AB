package com.ab.exerciseabinbev.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: Order)

    @Transaction
    @Query("SELECT * FROM orders_table WHERE order_id == :id")
    fun getOrderById(id: Int): LiveData<OrderWithProduct>

    @Transaction
    @Query("SELECT * FROM orders_table")
    fun getAllOrders(): LiveData<List<OrderWithProduct>>
}