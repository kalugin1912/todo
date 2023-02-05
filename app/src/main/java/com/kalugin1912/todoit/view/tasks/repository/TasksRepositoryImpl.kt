package com.kalugin1912.todoit.view.tasks.repository

import com.kalugin1912.todoit.view.Priority
import com.kalugin1912.todoit.view.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class TasksRepositoryImpl : TasksRepository {

    private val flow = MutableStateFlow(
        listOf(
            Task(
                id = 0,
                name = "Task 0",
                description = "Description 0",
                priority = Priority.LOW
            ),
            Task(
                id = 1,
                name = "Task 1",
                description = "Description 1",
                priority = Priority.HIGH
            ),
            Task(
                id = 2,
                name = "Task 2",
                description = "Description 2",
                priority = Priority.MEDIUM
            ),
            Task(
                id = 3,
                name = "Task 3",
                description = "Description 3",
                priority = Priority.MEDIUM
            ),
            Task(
                id = 4,
                name = "Task 4",
                description = "Description 4",
                priority = Priority.HIGH
            ),
        )
    )

    override fun getTasks(): Flow<List<Task>> {
        return flow

    }

    override suspend fun addTask(task: Task) {
        flow.update {
            val list = it.toMutableList()
            list.add(task.copy(id = Random(Int.MAX_VALUE).nextInt()))
            list
        }
    }

}