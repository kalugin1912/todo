package com.kalugin1912.todoit.view

data class Task(
    val id: Int,
    val name: String,
    val description: String,
    val priority: Priority,
)

enum class Priority {
    HIGH,
    MEDIUM,
    LOW,
}