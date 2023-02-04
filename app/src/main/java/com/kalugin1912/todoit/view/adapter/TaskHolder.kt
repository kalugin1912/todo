package com.kalugin1912.todoit.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.kalugin1912.todoit.databinding.ItemTaskBinding

class TaskHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: TaskItem) {
        binding.title.text = task.name
        binding.description.text = task.description
        binding.status.background = task.statusDrawable
    }

}