package com.routines.game.domain.repositories

import com.routines.game.data.data_base.Database
import com.routines.game.domain.entities.Comment
import com.routines.game.domain.entities.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TasksRepository {
    suspend fun getRelations(month: Int, day: Int): Pair<List<Task>, List<Comment>> {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Default).launch {
                val relations = Database.instance.dao().getUsersWithPlaylists(day, month)
                val tasksDB = relations?.tasks ?: emptyList()
                val commentsDB = relations?.comments ?: emptyList()
                val tasks = mutableListOf<Task>()
                val comments = mutableListOf<Comment>()

                tasksDB.forEach {
                    tasks.add(
                        Task(
                            it.title,
                            it.description,
                            it.time,
                            it.isDone
                        )
                    )
                }
                commentsDB.forEach {
                    comments.add(
                        Comment(
                            it.description,
                            it.name
                        )
                    )
                }
                continuation.resume(tasks to comments)
            }
        }
    }

    suspend fun deleteTask(task: Task) {
        withContext(Dispatchers.Default) {
            val taskDB = Database.instance.dao().getTaskByContent(task.title, task.description)
            if (taskDB != null) {
                Database.instance.dao().deleteTask(taskDB)
            }
        }
    }

    suspend fun doneTask(task: Task) {
        withContext(Dispatchers.Default) {
            val taskDB = Database.instance.dao().getTaskByContent(task.title, task.description)
            if (taskDB != null) {
                taskDB.isDone = true
                Database.instance.dao().doneTask(taskDB)
            }
        }
    }
}