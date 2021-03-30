package com.example.first_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.first_application.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnOK.text = "ok"
        binding.ivicon
    }
}