package com.kalugin1912.todoit.view.newtask

import androidx.lifecycle.ViewModel
import com.kalugin1912.todoit.view.Priority
import com.kalugin1912.todoit.view.tasks.repository.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewTaskViewModel(tasksRepository: TasksRepository) : ViewModel() {

    private val _selectedPriority = MutableStateFlow(Priority.LOW)

    val selectedPriority = _selectedPriority.asStateFlow()


    fun changePriority(priority: Priority) {
        _selectedPriority.value = priority
    }

}