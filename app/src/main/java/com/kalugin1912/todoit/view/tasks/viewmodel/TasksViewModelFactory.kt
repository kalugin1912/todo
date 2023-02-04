package com.kalugin1912.todoit.view.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kalugin1912.todoit.view.tasks.TasksViewModel
import com.kalugin1912.todoit.view.tasks.repository.TasksRepository

class TasksViewModelFactory(private val tasksRepository: TasksRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TasksViewModel(tasksRepository) as T
    }
}