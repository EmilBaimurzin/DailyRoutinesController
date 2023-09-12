package com.routines.game.domain.entities

data class Task (
    val title: String,
    val description: String,
    val time: String,
    var isDone: Boolean,
    var isMenuOpened: Boolean = false,
)