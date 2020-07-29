package com.ab.exerciseabinbev.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "size") val size: String,
    @ColumnInfo(name = "package_size") val packageSize: String,
    @ColumnInfo(name = "value") val value: Float
)