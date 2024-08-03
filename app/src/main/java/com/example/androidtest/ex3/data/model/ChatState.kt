package com.example.androidtest.ex3.data.model

data class ChatState(
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt: String = ""
)
