package com.kalugin1912.todoit

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <T> Flow<T>.collectWhenUIVisible(
    owner: LifecycleOwner,
    context: CoroutineContext = EmptyCoroutineContext,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    block: (suspend (T) -> Unit)? = null,
) = owner.lifecycleScope.launch(
    context = context,
    block = {
        owner.repeatOnLifecycle(lifecycleState) {
            if (block != null) {
                collect(block)
            } else {
                collect()
            }
        }
    },
)