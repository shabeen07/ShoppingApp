package com.example.shopping_app.respository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shopping_app.models.ProductItem
import com.example.shopping_app.respository.local.dao.CartDao

@Database(entities = [ProductItem::class], version = 1, exportSchema = true)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        // if the INSTANCE is not null, then return it, if it is, then create the database
        fun getDatabase(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        LocalDatabase::class.java,
                        "app_database"
                    )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }

}