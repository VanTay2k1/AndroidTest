package com.example.androidtest.ex3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.ex3.data.ChatUIEvent
import com.example.androidtest.ex3.data.model.Chat
import com.example.androidtest.ex3.data.model.ChatData
import com.example.androidtest.ex3.data.model.ChatState
import kotlinx.coroutines.launch

class AiViewModel : ViewModel() {

    private val _chatState = MutableLiveData(ChatState())
    val chatState: LiveData<ChatState> = _chatState

    fun onEvent(event: ChatUIEvent) {
        when (event) {
            is ChatUIEvent.SendPrompt -> {
                if (event.prompt.isNotEmpty()) {
                    addPrompt(prompt = event.prompt)
                    getResponse(event.prompt)
                }
            }

            is ChatUIEvent.UpdatePrompt -> {
                _chatState.value = _chatState.value?.copy(prompt = event.newPrompt)
            }
        }
    }

    private fun addPrompt(prompt: String) {
        val updatedChatList = _chatState.value?.chatList?.toMutableList() ?: mutableListOf()
        updatedChatList.add(0, Chat(prompt, isFromUser = true))
        _chatState.value = _chatState.value?.copy(chatList = updatedChatList)
    }

    private fun getResponse(prompt: String) {
        viewModelScope.launch {
            val chat = ChatData.getResponse(prompt)
            val updatedChatList = _chatState.value?.chatList?.toMutableList() ?: mutableListOf()
            updatedChatList.add(0, chat)

            _chatState.value = _chatState.value?.copy(chatList = updatedChatList)
        }
    }
}
