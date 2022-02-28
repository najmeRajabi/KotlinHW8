package com.example.kotlinhw8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinhw8.databinding.ActivityShowInfoBinding

class ShowInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}