package com.ab.exerciseabinbev.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "orders_table", primaryKeys = ["order_id", "product_id"])
data class Order(
    @ColumnInfo(name = "order_id") val id: Int,
    @ColumnInfo(name = "product_id") val productId: Int,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "purchased") val purchased: Boolean
)