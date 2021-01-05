package com.cst.todotasks

import androidx.room.*
import java.util.*

@Dao
interface DAO {
    @Query("SELECT * FROM todomodel")
    fun getAll(): List<ToDoModel>

    @Query("SELECT * FROM todomodel WHERE uid = (:taskIds)")
    fun selectById(taskIds: Int): ToDoModel
//
//
    @Insert
    fun insertAll(vararg tasks: ToDoModel)
//
    @Delete
    fun delete(task: ToDoModel)
    @Update
    fun updateTask(task:ToDoModel)
}
