package com.kalugin1912.todoit.view.adapter

import android.graphics.drawable.Drawable

data class TaskItem(
    val id: Int,
    val name: String,
    val description: String,
    val statusDrawable: Drawable,
)