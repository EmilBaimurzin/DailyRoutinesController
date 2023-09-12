package com.routines.game.domain.repositories

import com.routines.game.data.data_base.Database
import com.routines.game.data.data_base.entities.DateDB
import com.routines.game.data.data_base.entities.TaskDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class NewTaskRepository {
    suspend fun createTask(day: Int, month: Int, title: String, description: String, time: String) {
        withContext(Dispatchers.Default) {
            val date = Database.instance.dao().getDate(day, month)
            if (date == null) {
                Database.instance.dao().createDate(
                    DateDB(0, day, month)
                )
                delay(20)
                val getDate = Database.instance.dao().getDate(day, month)
                Database.instance.dao().addTask(
                    TaskDB(0, getDate!!.id, title, description, time, false)
                )
            } else {
                val getDate = Database.instance.dao().getDate(day, month)
                Database.instance.dao().addTask(
                    TaskDB(0, getDate!!.id, title, description, time, false)
                )
            }
        }
    }

    suspend fun createDate(day: Int, month: Int) {
        withContext(Dispatchers.Default) {
            val date = Database.instance.dao().getDate(day, month)
            if (date == null) {
                Database.instance.dao().createDate(DateDB(0, day, month))
            }
        }
    }
}