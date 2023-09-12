package com.routines.game.domain.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.routines.game.R
import com.routines.game.databinding.ItemCalendarBinding
import com.routines.game.databinding.ItemNotificationsBinding
import com.routines.game.domain.entities.Calendar
import com.routines.game.domain.entities.Comment

class CommentsAdapter(private val onClick: (pos: Int, comment: Comment) -> Unit): RecyclerView.Adapter<CommentsViewHolder>() {
    var items = mutableListOf<Comment>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(ItemNotificationsBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(items[position])
        holder.onClick = onClick
    }
}

class CommentsViewHolder(private val binding: ItemNotificationsBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {

    var onClick: ((pos: Int, comment: Comment) -> Unit)? = null

    fun bind(comment: Comment) {
        binding.name.text = comment.name
        binding.description.text = comment.description

        if (comment.isAdd) {
            binding.name.setTextColor(context.resources.getColor(R.color.textColor))
            binding.description.setTextColor(context.resources.getColor(R.color.textColor))
            binding.commentImg.setImageResource(R.drawable.ic_plus_circle)
            binding.name.text = "Add a new comment"
            binding.description.text = "add a comment that will be displayed throughout the day"
        }

        binding.root.setOnClickListener { onClick?.invoke(adapterPosition, comment) }
    }
}