package com.kalugin1912.todoit.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kalugin1912.todoit.databinding.ItemTaskBinding

class TaskAdapter : ListAdapter<TaskItem, TaskHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(layoutInflater, parent, false)
        return TaskHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount() = currentList.size

    private companion object {

        private val ITEM_CALLBACK = object : DiffUtil.ItemCallback<TaskItem>() {
            override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
                return oldItem == newItem
            }

        }
    }

}

