package com.routines.game.ui.new_comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routines.game.domain.repositories.NewCommentRepository
import kotlinx.coroutines.launch

class NewCommentViewModel: ViewModel() {
    private val repository = NewCommentRepository()
    var month = 0
    var day = 0

    fun createTask(day: Int, month: Int, title: String, description: String) {
        viewModelScope.launch {
            repository.createComment(day, month, title, description)
        }
    }

    fun createDate(day: Int, month: Int) {
        viewModelScope.launch {
            repository.createDate(day, month)
        }
    }
}