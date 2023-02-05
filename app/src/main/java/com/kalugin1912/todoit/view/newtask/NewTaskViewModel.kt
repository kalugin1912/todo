package com.kalugin1912.todoit.view.newtask

import androidx.lifecycle.ViewModel
import com.kalugin1912.todoit.view.Priority
import com.kalugin1912.todoit.view.Task
import com.kalugin1912.todoit.view.tasks.repository.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class NewTaskViewModel(tasksRepository: TasksRepository) : ViewModel() {

    private companion object {
        private const val DEBOUNCE = 200L
    }

    private val _selectedPriority = MutableStateFlow(Priority.LOW)

    val selectedPriority = _selectedPriority.asStateFlow()

    private val _title = MutableStateFlow("")
    private val title = _title.asStateFlow().debounce(DEBOUNCE)

    val enableAddButton = title
        .map { title -> title.isNotBlank() }
        .distinctUntilChanged()

    private val _description = MutableStateFlow("")

    private val task = combine(
        title,
        _description.debounce(DEBOUNCE),
        selectedPriority
    ) { title, descriptiom, priority ->
        Task(
            id = -1,
            name = title,
            description = descriptiom,
            priority = priority
        )
    }

    fun changePriority(priority: Priority) {
        _selectedPriority.value = priority
    }

    fun changeTitle(title: String) {
        _title.value = title
    }

    fun changeDescription(description: String) {
        _description.value = description
    }

}