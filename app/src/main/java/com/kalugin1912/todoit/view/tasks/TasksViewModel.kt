package com.kalugin1912.todoit.view.tasks

import androidx.lifecycle.ViewModel
import com.kalugin1912.todoit.view.Task
import com.kalugin1912.todoit.view.tasks.repository.TasksRepository
import kotlinx.coroutines.flow.Flow

class TasksViewModel(tasksRepository: TasksRepository) : ViewModel() {

    val tasks: Flow<List<Task>> = tasksRepository.getTasks()

}