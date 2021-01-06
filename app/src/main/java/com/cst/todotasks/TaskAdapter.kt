package com.cst.todotasks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cst.todotasks.DBInstance.Companion.toDoDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.graphics.Paint
import android.os.Bundle
import android.widget.*
import com.cst.todotasks.ui.EditTask
import com.cst.todotasks.ui.MainActivity
import kotlinx.coroutines.withContext

class TaskAdapter(
    private var tasks: MutableList<ToDoModel>
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_card, parent, false)
        return TaskViewHolder(view, parent.context)

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        holder.initialize(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class TaskViewHolder(private val view: View, private val context: Context) :
        RecyclerView.ViewHolder(view) {
        private val checkBox: CheckBox = view.findViewById(R.id.checkbox)
        private val text: TextView = view.findViewById(R.id.text)
        fun initialize(item: ToDoModel) {
            text.text = item.title
            checkBox.isChecked = item.isDone!!
            strike(item.isDone!!)
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                item.isDone = isChecked
                strike(isChecked)
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        toDoDB.userDao().updateTask(item)
                    }
                }
            }


            view.setOnClickListener {

                (context as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, EditTask().apply {
                        arguments = Bundle().apply {
                            item.uid?.let { it1 ->
                                putInt(
                                    "uid",
                                    it1
                                )
                            }
                        }
                    }).addToBackStack(null).commit()
            }


        }

        private fun strike(isChecked: Boolean) {

            if (isChecked) {
                text.paintFlags = text.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                text.paintFlags = text.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }


    }

    fun getUID(): Int {
        return tasks[tasks.size - 1].uid
    }

    fun setNewLIst(list: List<ToDoModel>) {
        this.tasks = list as MutableList<ToDoModel>
    }

}