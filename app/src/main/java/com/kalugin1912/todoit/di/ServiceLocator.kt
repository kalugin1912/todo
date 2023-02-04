package com.kalugin1912.todoit.di

import com.kalugin1912.todoit.view.tasks.repository.TasksRepository
import com.kalugin1912.todoit.view.tasks.repository.TasksRepositoryImpl
import com.kalugin1912.todoit.view.tasks.viewmodel.TasksViewModelFactory

object ServiceLocator {

    val tasksViewModelFactory: TasksViewModelFactory by lazy(LazyThreadSafetyMode.NONE) {
        TasksViewModelFactory(tasksRepository)
    }

    private val tasksRepository: TasksRepository by lazy(LazyThreadSafetyMode.NONE) {
        TasksRepositoryImpl()
    }

}