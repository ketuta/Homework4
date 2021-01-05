package com.cst.todotasks.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cst.todotasks.DBInstance
import com.cst.todotasks.DBInstance.Companion.toDoDB
import com.cst.todotasks.R
import com.cst.todotasks.TaskAdapter
import com.cst.todotasks.ToDoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by nikolozakhvlediani on 12/24/20.
 */
class TaskListFragment : Fragment() {
    lateinit var taskList: MutableList<ToDoModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_clear -> {


                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        taskList.forEach {
                            if (it.isDone!!) {
                                toDoDB.userDao().delete(it)
                            }
                        }
                        taskList = toDoDB.userDao().getAll() as MutableList<ToDoModel>
                        withContext(Dispatchers.Main) {
                            val taskAdapter = TaskAdapter(taskList)
                            activity?.findViewById<RecyclerView>(R.id.recyclerview)?.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = taskAdapter
                            }
                        }

                    }

                }



                true
            }
            R.id.menu_filter -> {
                showFilteringPopUpMenu()
                true
            }
            else -> false
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                taskList = DBInstance.toDoDB.userDao().getAll() as MutableList<ToDoModel>
                val taskAdapter = TaskAdapter(taskList)

                activity?.findViewById<RecyclerView>(R.id.recyclerview)?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = taskAdapter
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tasks_fragment_menu, menu)
    }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_tasks, menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.active -> {
                        // TODO თქვენი კოდი
                    }
                    R.id.completed -> {
                        // TODO თქვენი კოდი
                    }
                    else -> {
                        // TODO თქვენი კოდი
                    }
                }
                true
            }
            show()
        }
    }

    companion object {

        fun createInstance() = TaskListFragment()

    }

}