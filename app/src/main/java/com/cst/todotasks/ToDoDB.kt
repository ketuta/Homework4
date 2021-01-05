package com.cst.todotasks

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ToDoModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): DAO
}