package com.routines.game.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.routines.game.R
import com.routines.game.databinding.FragmentTasksBinding
import com.routines.game.domain.adapters.CalendarAdapter
import com.routines.game.domain.adapters.CommentsAdapter
import com.routines.game.domain.adapters.TaskAdapter
import com.routines.game.ui.other.ActivityViewModel
import com.routines.game.ui.other.ViewBindingFragment
import java.util.Calendar

class FragmentTasks : ViewBindingFragment<FragmentTasksBinding>(FragmentTasksBinding::inflate) {
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var taskAdapter: TaskAdapter
    private val callbackViewModel: ActivityViewModel by activityViewModels()
    private val viewModel: TasksViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendarAdapter()
        initCommentsAdapter()
        initTaskAdapter()

        callbackViewModel.callback = {
            viewModel.updateList(viewModel.calendar.value!!.indexOf(viewModel.calendar.value!!.find { it.isSelected }))
        }

        binding.newTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentTasks_to_fragmentNewTask)
        }

        binding.actionButton.setOnClickListener {
            viewModel.menu()
        }

        binding.newCommentButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentTasks_to_fragmentNewComment)
        }

        viewModel.calendar.observe(viewLifecycleOwner) {
            calendarAdapter.items = it.toMutableList()
            calendarAdapter.notifyDataSetChanged()

            if (it.isNotEmpty()) {
                val date = it.find { it.isSelected }!!.date
                val dateText = when (date.toString().substring(date.toString().length - 1)) {
                    "1" -> "st"
                    "2" -> "nd"
                    "3" -> "rd"
                    "11" -> "th"
                    "12" -> "th"
                    "13" -> "th"
                    else -> "th"
                }
                binding.date.text = when (it.find { it.isSelected }!!.day) {
                    "Sun" -> "Sunday ${date.toString() + dateText}"
                    "Mon" -> "Monday ${date.toString() + dateText}"
                    "Tue" -> "Tuesday ${date.toString() + dateText}"
                    "Wed" -> "Wednesday ${date.toString() + dateText}"
                    "Thu" -> "Thursday ${date.toString() + dateText}"
                    "Fri" -> "Friday ${date.toString() + dateText}"
                    else -> "Saturday ${date.toString() + dateText}"
                }
                if (Calendar.getInstance()
                        .get(Calendar.DAY_OF_MONTH) == it.first { it.isSelected }.date
                ) {
                    binding.todayText.text = "Today"
                } else {
                    val monthText = when (Calendar.getInstance().get(Calendar.MONTH) + 1) {
                        1 -> "January"
                        2 -> "February"
                        3 -> "March"
                        4 -> "April"
                        5 -> "May"
                        6 -> "June"
                        7 -> "July"
                        8 -> "August"
                        9 -> "September"
                        10 -> "October"
                        11 -> "November"
                        else -> "December"
                    }
                    binding.todayText.text = "$monthText ${date.toString() + dateText}"
                }
            }
        }
        viewModel.comments.observe(viewLifecycleOwner) {
            commentsAdapter.items = it.toMutableList()
            commentsAdapter.notifyDataSetChanged()
        }
        viewModel.tasks.observe(viewLifecycleOwner) {
            taskAdapter.items = it.toMutableList()
            taskAdapter.notifyDataSetChanged()
        }

        viewModel.isMenuOpened.observe(viewLifecycleOwner) {
            binding.apply {
                newTaskButton.isVisible = it
                newCommentButton.isVisible = it
                calendar.isVisible = it
            }
        }
    }

    private fun initCalendarAdapter() {
        calendarAdapter = CalendarAdapter { pos, calendar ->
            viewModel.setDay(pos)
            viewModel.updateList(pos)
        }
        with(binding.dayRV) {
            adapter = calendarAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            setHasFixedSize(false)
        }
    }

    private fun initCommentsAdapter() {
        commentsAdapter = CommentsAdapter { pos, comment ->
            if (comment.isAdd) {
                findNavController().navigate(R.id.action_fragmentTasks_to_fragmentNewComment)
            }
        }
        with(binding.notificationsRV) {
            adapter = commentsAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(false)
        }
    }

    private fun initTaskAdapter() {
        taskAdapter = TaskAdapter { pos, task, isMenu, isDone, isDelete ->
            when {
                isMenu -> viewModel.openTaskMenu(pos)
                isDone -> viewModel.doneTask(pos, task)
                isDelete -> viewModel.deleteTask(pos, task)
            }
        }
        with(binding.tasksRV) {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(false)
        }
    }
}