package com.cst.todotasks.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.RoomDatabase
import com.cst.todotasks.DBInstance
import com.cst.todotasks.DBInstance.Companion.toDoDB
import com.cst.todotasks.R
import com.cst.todotasks.ToDoModel
import com.cst.todotasks.extensions.replaceFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        replaceFragment(R.id.fragment_container, TaskListFragment.createInstance())

        DBInstance.dBBuilder(this)
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                toDoDB.userDao().insertAll(ToDoModel(0,"asd","asd"))
                //Log.d("asd",toDoDB.userDao().getAll().toString())
            }
        }

    }

}