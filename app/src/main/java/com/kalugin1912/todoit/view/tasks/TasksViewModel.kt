package com.kalugin1912.todoit.view.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalugin1912.todoit.view.Task
import com.kalugin1912.todoit.view.newtask.NavigationEvent
import com.kalugin1912.todoit.view.tasks.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TasksViewModel(private val tasksRepository: TasksRepository) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    val tasks: Flow<List<Task>> = tasksRepository.getTasks()

    fun onAddNewTask() = viewModelScope.launch {
        _navigationEvent.emit(NavigationEvent.NewTask)
    }

    fun onCloseClicked() = viewModelScope.launch {
        _navigationEvent.emit(NavigationEvent.Close)
    }

    fun onTaskClicked(task: Task) = viewModelScope.launch {
        _navigationEvent.emit(NavigationEvent.UpdateTask(task))
    }

    fun onTaskStatusChanged(task: Task, isCompleted: Boolean) = viewModelScope.launch {
        tasksRepository.updateTask(task.copy(isCompleted = isCompleted))
    }

    sealed interface NavigationEvent {
        object Close : NavigationEvent
        object NewTask : NavigationEvent
        class UpdateTask(val task: Task) : NavigationEvent
    }
}