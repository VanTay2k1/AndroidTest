package com.example.androidtest.ex1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtest.databinding.ActivityRoomBinding
import com.example.androidtest.ex1.adapter.StudentAdapter
import com.example.androidtest.ex1.model.Student
import kotlinx.coroutines.launch

class RoomDBActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomBinding
    private lateinit var database: AppDatabase
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            addData()
            initView()
        }
    }

    private suspend fun initView() {
        val studentDao = database.studentDao()
        val students = studentDao.getAll()
        studentAdapter = StudentAdapter(students)
        binding.rlListStudent.adapter = studentAdapter
    }

    private suspend fun addData() {
        val studentDao = database.studentDao()
        studentDao.insert(Student(name = "John A1", age = 20, gender = "Male"))
        studentDao.insert(Student(name = "John Ư2", age = 20, gender = "Male"))
        studentDao.insert(Student(name = "Doe G3", age = 20, gender = "Male"))
        studentDao.insert(Student(name = "John JV4", age = 20, gender = "Male"))
    }
}
