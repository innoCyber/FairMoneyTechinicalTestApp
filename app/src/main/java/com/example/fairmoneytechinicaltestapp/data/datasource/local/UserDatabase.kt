package com.example.fairmoneytechinicaltestapp.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fairmoneytechinicaltestapp.data.datasource.local.dao.UserDao
import com.example.fairmoneytechinicaltestapp.data.model.User


@Database(
    entities = [User::class],
    version = 3,
    exportSchema = false
)

abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instance: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            if (instance == null) {
                synchronized(UserDatabase::class.java) {}
                instance =
                    Room.databaseBuilder(context, UserDatabase::class.java, "app_database")
                        .fallbackToDestructiveMigration()
                        .build()
            }

            return instance!!
        }
    }
}
