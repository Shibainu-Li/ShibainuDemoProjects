package com.shibainu.li.dbroomlibs.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "user_age") val userAge: Int?,
    @ColumnInfo(name = "user_sex") val userSex: Char?
)