package com.kalugin1912.todoit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kalugin1912.todoit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}