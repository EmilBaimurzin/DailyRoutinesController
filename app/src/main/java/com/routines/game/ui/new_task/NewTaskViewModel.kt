package com.routines.game.ui.new_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routines.game.domain.repositories.NewTaskRepository
import kotlinx.coroutines.launch

class NewTaskViewModel : ViewModel() {
    private val repository = NewTaskRepository()
    var hours = 0
    var minutes = 0
    var month = 0
    var day = 0

    fun createTask(day: Int, month: Int, title: String, description: String, time: String) {
        viewModelScope.launch {
            repository.createTask(day, month, title, description, time)
        }
    }

    fun createDate(day: Int, month: Int) {
        viewModelScope.launch {
            repository.createDate(day, month)
        }
    }
}