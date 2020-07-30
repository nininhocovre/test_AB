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

                    val orderDao = database.orderDao()
                    orderDao.insert(Order(10, 100, 5))
                    orderDao.insert(Order(10, 101, 1))
                    orderDao.insert(Order(10, 102, 1))
                    orderDao.insert(Order(10, 103, 8))
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