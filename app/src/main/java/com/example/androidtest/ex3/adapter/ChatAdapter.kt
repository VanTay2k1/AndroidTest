package com.example.androidtest.ex3.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.databinding.ChatItemModelBinding
import com.example.androidtest.databinding.ChatItemUserBinding
import com.example.androidtest.ex3.data.model.Chat

class ChatAdapter(private val chat: MutableList<Chat>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_MODEL = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_USER) {
            ChatViewHolderUser(
                ChatItemUserBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ChatViewHolderModel(
                ChatItemModelBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (chat[position].isFromUser) VIEW_TYPE_USER else VIEW_TYPE_MODEL
    }

    override fun getItemCount(): Int = chat.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("xxxx", "Binding item at position: $position")
        if (holder is ChatViewHolderUser) {
            holder.bind(chat[position])
        } else if (holder is ChatViewHolderModel) {
            holder.bind(chat[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateChat(newChatList: List<Chat>) {
        chat.clear()
        chat.addAll(newChatList)
        notifyDataSetChanged()
    }

    class ChatViewHolderUser(
        private val binding: ChatItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.messageTextView.text = chat.message
        }
    }

    class ChatViewHolderModel(
        private val binding: ChatItemModelBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.messageTextView.text = chat.message
        }
    }
}
