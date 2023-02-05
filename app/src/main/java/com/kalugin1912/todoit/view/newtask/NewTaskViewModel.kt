package com.kalugin1912.todoit.view.newtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalugin1912.todoit.view.Priority
import com.kalugin1912.todoit.view.Task
import com.kalugin1912.todoit.view.tasks.repository.TasksRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewTaskViewModel(private val tasksRepository: TasksRepository) : ViewModel() {

    private companion object {
        private const val DEBOUNCE = 200L
    }

    val navigationEvent = MutableSharedFlow<NavigationEvent>()

    private val _task = MutableStateFlow<Task?>(null)
    private val _selectedPriority = MutableStateFlow(Priority.LOW)

    val selectedPriority = _selectedPriority.asStateFlow()

    private val _title = MutableStateFlow("")
    private val title = _title.debounce(DEBOUNCE)

    private val _description = MutableStateFlow("")
    private val description = _description.debounce(DEBOUNCE)


    val enableAddButton = title
        .map { title -> title.isNotBlank() }
        .distinctUntilChanged()


    init {
        combine(
            title,
            description,
            selectedPriority
        ) { title, description, priority ->
            Task(
                id = -1,
                name = title,
                description = description,
                priority = priority
            )
        }
            .onEach { task -> _task.emit(task) }
            .launchIn(viewModelScope)
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

    fun save() = viewModelScope.launch {
        val task = _task.value
        if (task != null) {
            tasksRepository.addTask(task)
        }
        navigationEvent.emit(NavigationEvent.Close)
    }

    fun close() = viewModelScope.launch {
        navigationEvent.emit(NavigationEvent.Close)
    }

}

sealed interface NavigationEvent {
    object Close : NavigationEvent
}