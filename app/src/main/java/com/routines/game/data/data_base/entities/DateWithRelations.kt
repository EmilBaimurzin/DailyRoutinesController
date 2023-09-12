package com.routines.game.data.data_base.entities

import androidx.room.Embedded
import androidx.room.Relation


data class DateWithRelations (
    @Embedded
    val date: DateDB,
    @Relation(
        parentColumn = "id",
        entityColumn = "dateId"
    )
    val comments: List<CommentDB>,
    @Relation(
        parentColumn = "id",
        entityColumn = "dateId"
    )
    val tasks: List<TaskDB>,
)