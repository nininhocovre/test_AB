package com.ab.exerciseabinbev.data

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithProduct(

    @Embedded val order: Order,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "id"
    )
    val products: List<Product>
)