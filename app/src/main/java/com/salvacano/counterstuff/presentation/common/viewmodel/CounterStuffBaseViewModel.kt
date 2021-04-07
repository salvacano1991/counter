package com.salvacano.counterstuff.presentation.common.viewmodel

import androidx.lifecycle.ViewModel
import arrow.core.Either
import kotlinx.coroutines.*

abstract class CounterStuffBaseViewModel : ViewModel() {

    private var coroutineContext: CoroutineDispatcher = Dispatchers.IO
    private var coroutineContextReturn: CoroutineDispatcher = Dispatchers.Main
    private val job = SupervisorJob()

    fun runOnBackground(
        exception: ((Exception) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            @Suppress("DeferredResultUnused")
            GlobalScope.async {
                try {
                    block.invoke(this)
                } catch (ex: Exception) {
                    exception?.invoke(ex)
                }
            }
        }
    }

    fun <T> launch(
        success: (T) -> Unit,
        error: ((Throwable) -> Unit)? = null,
        block: suspend () -> Either<Throwable, T>
    ) {
        CoroutineScope(coroutineContext + job).launch(coroutineContext) {
            block().fold({
                launch(coroutineContextReturn) {
                    error?.invoke(it)
                }
            }) {
                launch(coroutineContextReturn) {
                    success(it)
                }
            }
        }
    }

}
