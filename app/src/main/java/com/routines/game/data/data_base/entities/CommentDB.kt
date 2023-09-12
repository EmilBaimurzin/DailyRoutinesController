package com.routines.game.data.data_base.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommentDB (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dateId: Int,
    val description: String,
    val name: String,
)