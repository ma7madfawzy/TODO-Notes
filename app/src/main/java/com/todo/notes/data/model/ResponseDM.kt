package com.todo.notes.data.model

 class ResponseDM<out T>(
    val status: String,
    val code: String,
    val message: String,
    val totalResults:Int,
    val articles:T
)
