package com.example.androidtest.ex3.data

sealed class ChatUIEvent {
    data class UpdatePrompt(val newPrompt: String) : ChatUIEvent()
    data class SendPrompt(val prompt: String) : ChatUIEvent()
}