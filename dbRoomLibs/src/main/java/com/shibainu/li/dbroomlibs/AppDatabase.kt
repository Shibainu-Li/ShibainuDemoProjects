package com.shibainu.li.dbroomlibs

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shibainu.li.dbroomlibs.bean.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}