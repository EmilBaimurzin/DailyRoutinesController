package com.routines.game.domain.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.routines.game.databinding.ItemCalendarBinding
import com.routines.game.domain.entities.Calendar

class CalendarAdapter(private val onClick: (pos: Int, calendar: Calendar) -> Unit): RecyclerView.Adapter<CalendarViewHolder>() {
    var items = mutableListOf<Calendar>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(items[position])
        holder.onClick = onClick
    }
}

class CalendarViewHolder(private val binding: ItemCalendarBinding) : RecyclerView.ViewHolder(binding.root) {

    var onClick: ((pos: Int, calendar: Calendar) -> Unit)? = null

    fun bind(calendar: Calendar) {
        binding.day.text = calendar.day
        binding.date.text = calendar.date.toString()

        binding.selection.isVisible = calendar.isSelected

        binding.root.setOnClickListener {
            onClick?.invoke(adapterPosition, calendar)
        }
    }
}