package com.kalugin1912.todoit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kalugin1912.todoit.databinding.ActivityMainBinding
import com.kalugin1912.todoit.view.tasks.TasksFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_container, TasksFragment.newTasksFragment())
            .commit()
    }
}