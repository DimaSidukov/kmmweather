package com.example.kmmweather.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

interface Cancellable {
    fun cancel()
}

class AnyFlow<T>(source: Flow<T>) : Flow<T> by source {
    fun collect(
        onEach: (T) -> Unit,
        onCompletion: (cause: Throwable?) -> Unit
    ): Cancellable {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        scope.launch {
            try {
                collect {
                    onEach(it)
                }

                onCompletion(null)
            } catch (e: Throwable) {
                onCompletion(e)
            }
        }

        return object : Cancellable {
            override fun cancel() {
                scope.cancel()
            }
        }
    }
}

fun <T> Flow<T>.wrapToAny(): AnyFlow<T> = AnyFlow(this)