package com.kalugin1912.todoit.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalugin1912.todoit.databinding.ItemNewTaskBinding

class NewTaskAdapter(private val onAddTask: () -> Unit) : RecyclerView.Adapter<NewTaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewTaskViewHolder {
        val binding = ItemNewTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewTaskViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: NewTaskViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onAddTask()
        }
    }
}

class NewTaskViewHolder(newTaskBinding: ItemNewTaskBinding) : RecyclerView.ViewHolder(newTaskBinding.root)
