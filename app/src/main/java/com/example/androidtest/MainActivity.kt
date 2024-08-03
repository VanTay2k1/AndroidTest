package com.example.androidtest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtest.databinding.ActivityMainBinding
import com.example.androidtest.ex1.RoomDBActivity
import com.example.androidtest.ex2.LauncherAppActivity
import com.example.androidtest.ex3.AiIntegrationActivity
import com.example.androidtest.ex4.RecordActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()
    }

    private fun onClick() {
        binding.run {
            btnEx1.setOnClickListener {
                startActivity(Intent(this@MainActivity, RoomDBActivity::class.java))
            }
            btnEx2.setOnClickListener {
                startActivity(Intent(this@MainActivity, LauncherAppActivity::class.java))
            }
            btnEx3.setOnClickListener {
                startActivity(Intent(this@MainActivity, AiIntegrationActivity::class.java))
            }
            btnEx4.setOnClickListener {
                startActivity(Intent(this@MainActivity, RecordActivity::class.java))
            }
        }
    }
}
