package com.routines.game.domain.repositories

import com.routines.game.data.data_base.Database
import com.routines.game.data.data_base.entities.CommentDB
import com.routines.game.data.data_base.entities.DateDB
import com.routines.game.data.data_base.entities.TaskDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class NewCommentRepository {
    suspend fun createComment(
        day: Int,
        month: Int,
        title: String,
        description: String,
    ) {
        withContext(Dispatchers.Default) {
            val getDate = Database.instance.dao().getDate(day, month)
            Database.instance.dao().addComment(
                CommentDB(
                    0,
                    getDate!!.id,
                    description,
                    title
                )
            )
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