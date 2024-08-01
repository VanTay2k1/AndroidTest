package com.example.androidtest.ex1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.databinding.ItemStudentBinding
import com.example.androidtest.ex1.model.Student

class StudentAdapter(private val student: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(student[position])
    }

    override fun getItemCount() = student.size

    class ItemHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Student) {
            binding.run {
                tvName.text = "Name: " + item.name
                tvAge.text = "Age: " + item.age.toString()
                tvAddress.text = "Address: " + item.address
                tvGender.text = "Gender: " + item.gender
            }
        }
    }
}
