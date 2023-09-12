package com.routines.game.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routines.game.domain.entities.Comment
import com.routines.game.domain.entities.Task
import com.routines.game.domain.repositories.TasksRepository
import kotlinx.coroutines.launch
import java.util.Calendar

class TasksViewModel : ViewModel() {
    private val repository = TasksRepository()
    private val _calendar =
        MutableLiveData<List<com.routines.game.domain.entities.Calendar>>(emptyList())
    val calendar: LiveData<List<com.routines.game.domain.entities.Calendar>> = _calendar

    private val _comments = MutableLiveData<List<Comment>>(emptyList())
    val comments: LiveData<List<Comment>> = _comments

    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>> = _tasks

    private val _isMenuOpened = MutableLiveData(false)
    val isMenuOpened: LiveData<Boolean> = _isMenuOpened

    init {
        createCalendarListForCurrentMonth()
    }

    fun updateList(index: Int) {
        viewModelScope.launch {
            val relations = repository.getRelations(
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                _calendar.value!![index].date
            )
            _tasks.postValue(relations.first!!)
            _comments.postValue(relations.second!! + listOf(Comment("", "", true)))
        }
    }

    fun menu() {
        _isMenuOpened.postValue(!_isMenuOpened.value!!)
    }

    fun openTaskMenu(index: Int) {
        val currentList = _tasks.value!!.toMutableList()
        currentList[index].isMenuOpened = !currentList[index].isMenuOpened
        _tasks.postValue(currentList)
    }

    fun doneTask(index: Int, task: Task) {
        val currentList = _tasks.value!!.toMutableList()
        currentList[index].isDone = true
        currentList[index].isMenuOpened = false
        _tasks.postValue(currentList)

        viewModelScope.launch {
            repository.doneTask(task)
        }
    }

    fun deleteTask(index: Int, task: Task) {
        val currentList = _tasks.value!!.toMutableList()
        currentList.removeAt(index)
        _tasks.postValue(currentList)

        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    private fun createCalendarListForCurrentMonth() {
        viewModelScope.launch {
            val calendarList = mutableListOf<com.routines.game.domain.entities.Calendar>()
            val currentDate = Calendar.getInstance()
            currentDate.set(Calendar.DAY_OF_MONTH, 1)

            val currentMonth = currentDate.get(Calendar.MONTH)

            while (currentDate.get(Calendar.MONTH) == currentMonth) {
                val dayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK)
                val dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)

                val dayOfWeekString = when (dayOfWeek) {
                    Calendar.SUNDAY -> "Sun"
                    Calendar.MONDAY -> "Mon"
                    Calendar.TUESDAY -> "Tue"
                    Calendar.WEDNESDAY -> "Wed"
                    Calendar.THURSDAY -> "Thu"
                    Calendar.FRIDAY -> "Fri"
                    else -> "Sat"
                }

                val myCalendar = com.routines.game.domain.entities.Calendar(
                    dayOfWeekString,
                    dayOfMonth,
                    dayOfMonth == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                )

                val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                val month = Calendar.getInstance().get(Calendar.MONTH) + 1

                val lists = repository.getRelations(month, day)
                _tasks.postValue(lists.first!!)
                _comments.postValue(lists.second!! + listOf(Comment("", "", true)))

                calendarList.add(myCalendar)

                currentDate.add(Calendar.DAY_OF_MONTH, 1)
            }

            _calendar.postValue(calendarList)
        }
    }

    fun setDay(index: Int) {
        val currentList = _calendar.value!!.toMutableList()
        currentList.map {
            it.isSelected = false
        }
        currentList[index].isSelected = true
        _calendar.postValue(currentList)
    }
}