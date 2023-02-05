package com.kalugin1912.todoit.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.kalugin1912.todoit.R
import com.kalugin1912.todoit.databinding.ItemTaskBinding
import com.kalugin1912.todoit.view.Priority
import com.kalugin1912.todoit.view.Task

class TaskHolder(
    private val binding: ItemTaskBinding,
    private val onTaskClicked: (Task) -> Unit,
    private val onCompletedClicked: (Task, Boolean) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(taskItem: TaskItem) {
        binding.title.text = taskItem.task.name
        binding.description.text = taskItem.task.description
        binding.status.setBackgroundResource(R.drawable.ic_circle)
        binding.status.isSelected = taskItem.task.isCompleted
        binding.root.background = taskItem.borderDrawable
        binding.status.setOnClickListener {
            onCompletedClicked(taskItem.task, !taskItem.task.isCompleted)
        }
        binding.root.setOnClickListener {
            onTaskClicked(taskItem.task)
        }
    }

}