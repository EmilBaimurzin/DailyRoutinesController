package com.routines.game.data.data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.routines.game.data.data_base.entities.CommentDB
import com.routines.game.data.data_base.entities.DateDB
import com.routines.game.data.data_base.entities.DateWithRelations
import com.routines.game.data.data_base.entities.TaskDB

@Dao
interface TasksDao {
    @Transaction
    @Query("SELECT * FROM DateDB WHERE day == :day AND month == :month")
    fun getUsersWithPlaylists(day: Int, month: Int): DateWithRelations?

    @Insert
    fun createDate(date: DateDB)

    @Query("SELECT * FROM DateDB WHERE day = :day AND month = :month")
    fun getDate(day: Int, month: Int): DateDB?

    @Insert
    fun addTask(task: TaskDB)

    @Insert
    fun addComment(commentDB: CommentDB)

    @Update
    fun doneTask(task: TaskDB)

    @Delete
    fun deleteTask(task: TaskDB)

    @Query("SELECT * FROM TaskDB WHERE title == :title AND description == :description")
    fun getTaskByContent(title: String, description: String): TaskDB?
}