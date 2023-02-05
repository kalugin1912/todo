package com.kalugin1912.todoit.view.tasks.repository

import com.kalugin1912.todoit.view.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasks() : Flow<List<Task>>

    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)
}