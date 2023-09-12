package com.routines.game.domain.entities

data class Comment(
    val description: String,
    val name: String,
    val isAdd: Boolean = false
)