package com.ab.exerciseabinbev.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Product::class, Order::class), version = 1, exportSchema = false)
public abstract class MainDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun orderDao(): OrderDao

    private class MainDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val productDao = database.productDao()

                    productDao.insert(Product(100, "Pepsi Lata", "120ml", "1 unidad", 10.20f))
                    productDao.insert(Product(101, "Pepsi Light 24/120NZ Lata", "120ml", "24 unidades", 603.90f))
                    productDao.insert(Product(102, "Seven Up Lata", "120ml", "24 unidades", 200.90f))
                    productDao.insert(Product(103, "Pepsi Lata", "350ml", "1 unidad", 13.20f))
                    productDao.insert(Product(104, "Pepsi Lata", "120ml", "2 unidad", 10.20f))
                    productDao.insert(Product(105, "Pepsi Light 24/120NZ Lata", "120ml", "24 unidades", 603.90f))
                    productDao.insert(Product(106, "Seven Up Lata", "120ml", "24 unidades", 200.90f))
                    productDao.insert(Product(107, "Pepsi Lata", "350ml", "2 unidad", 13.20f))
                    productDao.insert(Product(108, "Pepsi Lata", "120ml", "2 unidad", 10.20f))
                    productDao.insert(Product(109, "Pepsi Light 24/120NZ Lata", "120ml", "24 unidades", 603.90f))
                    productDao.insert(Product(110, "Seven Up Lata", "120ml", "24 unidades", 200.90f))
                    productDao.insert(Product(111, "Pepsi Light 24/120NZ Lata", "120ml", "24 unidades", 603.90f))
                    productDao.insert(Product(112, "Seven Up Lata", "120ml", "24 unidades", 200.90f))
                    productDao.insert(Product(113, "Pepsi Lata", "350ml", "3 unidad", 13.20f))
                    productDao.insert(Product(114, "Pepsi Lata", "120ml", "3 unidad", 10.20f))
                    productDao.insert(Product(115, "Pepsi Light 24/120NZ Lata", "120ml", "24 unidades", 603.90f))
                    productDao.insert(Product(116, "Seven Up Lata", "120ml", "24 unidades", 200.90f))
                    productDao.insert(Product(117, "Pepsi Lata", "350ml", "1 unidad", 13.20f))
                    productDao.insert(Product(118, "Pepsi Lata", "120ml", "1 unidad", 10.20f))
                    productDao.insert(Product(119, "Pepsi Light 24/120NZ Lata", "120ml", "24 unidades", 603.90f))
                    productDao.insert(Product(120, "Seven Up Lata", "120ml", "24 unidades", 200.90f))

                    val orderDao = database.orderDao()
                    orderDao.insert(Order(10, 100, 5, false))
                    orderDao.insert(Order(10, 101, 1, false))
                    orderDao.insert(Order(10, 102, 1, false))
                    orderDao.insert(Order(10, 103, 8, false))
                    orderDao.insert(Order(10, 104, 5, false))
                    orderDao.insert(Order(10, 105, 1, false))
                    orderDao.insert(Order(10, 106, 2, false))
                    orderDao.insert(Order(10, 107, 4, false))
                    orderDao.insert(Order(10, 108, 5, false))
                    orderDao.insert(Order(10, 109, 7, false))
                    orderDao.insert(Order(10, 110, 6, false))
                    orderDao.insert(Order(10, 111, 9, false))
                    orderDao.insert(Order(10, 112, 12, false))
                    orderDao.insert(Order(10, 113, 8, false))
                    orderDao.insert(Order(10, 114, 3, false))
                    orderDao.insert(Order(10, 115, 2, false))
                    orderDao.insert(Order(10, 116, 1, false))
                    orderDao.insert(Order(10, 117, 4, false))
                    orderDao.insert(Order(10, 118, 5, false))
                    orderDao.insert(Order(10, 119, 1, false))
                    orderDao.insert(Order(10, 120, 1, false))

                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MainDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "main_database"
                )
                    .addCallback(MainDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}