package com.cst.todotasks.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.cst.todotasks.DBInstance.Companion.toDoDB
import com.cst.todotasks.R
import com.cst.todotasks.ToDoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EditTask() : Fragment() {
    lateinit var task: ToDoModel
    lateinit var text: EditText
    lateinit var title: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uid = arguments!!.getInt("uid")
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                task = toDoDB.userDao().selectById(uid)
                withContext(Dispatchers.Main) {
                    text = activity!!.findViewById<EditText>(R.id.textText)
                    title = activity!!.findViewById<EditText>(R.id.titleText)
                    text.text = Editable.Factory.getInstance().newEditable(task.text)
                    title.text = Editable.Factory.getInstance().newEditable(task.title)
                }

            }
        }
        val submitButton = activity!!.findViewById<ImageButton>(R.id.submitButton)
        submitButton.setOnClickListener {
            task.title = title.text.toString()
            task.text = text.text.toString()
            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    toDoDB.userDao().updateTask(task)
                }
            }
            activity!!.supportFragmentManager.popBackStack();
        }

    }


}