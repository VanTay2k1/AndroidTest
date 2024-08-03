package com.example.androidtest.ex3

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtest.databinding.ActivityAiIntegrationBinding
import com.example.androidtest.ex3.adapter.ChatAdapter
import com.example.androidtest.ex3.data.ChatUIEvent
import com.example.androidtest.ex3.data.model.ChatState

class AiIntegrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAiIntegrationBinding
    private lateinit var chatAdapter: ChatAdapter
    private val aiViewModel: AiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiIntegrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeChatState()
        binding.sendButton.setOnClickListener {
            sendPrompt()
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(mutableListOf())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@AiIntegrationActivity)
            adapter = chatAdapter
        }
    }

    private fun observeChatState() {
        aiViewModel.chatState.observe(this, Observer { chatSate ->
            updateChat(chatSate)
        })
    }

    private fun sendPrompt() {
        binding.run {
            val prompt = inputField.text.toString().trim()
            if (prompt.isNotEmpty()) {
                aiViewModel.onEvent(ChatUIEvent.SendPrompt(prompt))
                inputField.text.clear()
            }
        }
    }

    private fun updateChat(chatState: ChatState) {
        chatAdapter.updateChat(chatState.chatList)
    }
}
