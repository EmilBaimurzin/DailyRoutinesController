package com.routines.game.data.data_base.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DateDB (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val day: Int,
    val month: Int
)