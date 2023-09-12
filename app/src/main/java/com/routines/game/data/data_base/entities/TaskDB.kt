package com.routines.game.data.data_base.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskDB (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dateId: Int,
    val title: String,
    val description: String,
    val time: String,
    var isDone: Boolean,
)