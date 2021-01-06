package com.cst.todotasks.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cst.todotasks.DBInstance
import com.cst.todotasks.R
import com.cst.todotasks.extensions.replaceFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        replaceFragment(R.id.fragment_container, TaskListFragment.createInstance())

        DBInstance.dBBuilder(this)
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
            }
        }

    }

}