package com.cst.todotasks

import android.content.Context
import androidx.room.Room

class DBInstance {
    companion object {
        lateinit var toDoDB : AppDatabase
        fun dBBuilder(applicationContext:Context) {
            toDoDB = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "todomodel"
            ).build()
        }
    }
}