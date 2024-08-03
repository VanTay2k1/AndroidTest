package com.example.androidtest.ex3.data.model

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatData {
    private const val api_key = "AIzaSyCt1KNE9G0BkAbOeu9-XDX7ro_BslXVMcw"

    suspend fun getResponse(prompt: String): Chat {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro", apiKey = api_key
        )
        return try {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }
            Chat(
                message = response.text ?: "error",
                isFromUser = false
            )
        } catch (e: Exception) {
            Chat(
                message = e.message ?: "error",
                isFromUser = false
            )
        }
    }
}
