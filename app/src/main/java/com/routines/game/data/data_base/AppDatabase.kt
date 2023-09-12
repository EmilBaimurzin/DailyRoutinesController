package com.routines.game.data.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.routines.game.data.data_base.entities.CommentDB
import com.routines.game.data.data_base.entities.DateDB
import com.routines.game.data.data_base.entities.TaskDB

@Database(entities = [CommentDB::class, DateDB::class, TaskDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): TasksDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}