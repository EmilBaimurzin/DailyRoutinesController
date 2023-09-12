package com.routines.game.domain.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.routines.game.databinding.ItemTaskBinding
import com.routines.game.domain.entities.Task

class TaskAdapter(private val onClick: (pos: Int, task: Task, isMenu: Boolean, isDone: Boolean, isDelete: Boolean) -> Unit) :
    RecyclerView.Adapter<TaskViewHolder>() {
    var items = mutableListOf<Task>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(items[position])
        holder.onClick = onClick
    }
}

class TaskViewHolder(private val binding: ItemTaskBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {

    var onClick: ((pos: Int, task: Task, isMenu: Boolean, isDone: Boolean, isDelete: Boolean) -> Unit)? =
        null

    fun bind(task: Task) {
        binding.taskTitle.text = task.title
        binding.taskDescription.text = task.description
        binding.taskTime.text = task.time
        binding.taskDone.isVisible = task.isDone

        if (task.isMenuOpened) {
            binding.done.isVisible = true
            binding.delete.isVisible = true
        } else {
            binding.done.isVisible = false
            binding.delete.isVisible = false
        }

        binding.done.setOnClickListener {
            onClick?.invoke(adapterPosition, task, false, true, false)
        }
        binding.delete.setOnClickListener {
            onClick?.invoke(adapterPosition, task, false, false, true)
        }
        binding.menu.setOnClickListener {
            onClick?.invoke(adapterPosition, task, true, false, false)
        }
    }
}