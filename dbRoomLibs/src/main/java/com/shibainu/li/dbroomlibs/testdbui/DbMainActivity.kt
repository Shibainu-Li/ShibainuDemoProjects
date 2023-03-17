package com.shibainu.li.dbroomlibs.testdbui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import com.shibainu.li.dbroomlibs.AppDatabase
import com.shibainu.li.dbroomlibs.R
import com.shibainu.li.dbroomlibs.bean.User
import kotlin.concurrent.thread

class DbMainActivity : AppCompatActivity() {

    lateinit var mUs:List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db_main)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database-lh"
        ).build()
        val userDao = db.userDao()

        findViewById<View>(R.id.find_user).setOnClickListener {
            thread {
                mUs = userDao.getAll()
                Log.d("lhh","$mUs")
            }
        }

        findViewById<View>(R.id.add_user).setOnClickListener {
            thread {
                userDao.addUser(User(userName = "狗", userAge = System.currentTimeMillis().toInt(), userSex = '女'))
            }
        }

        findViewById<View>(R.id.delet_user).setOnClickListener {
            thread {
                userDao.delete(*mUs.toTypedArray())
            }
        }

        findViewById<View>(R.id.find_user_uid).setOnClickListener {
            thread {
                Log.d("lhh","${userDao.getUser(3)}")
            }
        }

    }
}