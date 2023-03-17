package com.shibainu.li.dbroomlibs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.shibainu.li.dbroomlibs.bean.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid = :id")
    fun getUser(id:Int): User?


    @Insert
    fun addUser(user: User)

    @Delete
    fun delete(vararg user: User)

}