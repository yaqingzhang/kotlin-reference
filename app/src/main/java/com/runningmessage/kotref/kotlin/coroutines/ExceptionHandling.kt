package com.runningmessage.kotref.kotlin.coroutines

import android.os.Build
import android.support.annotation.RequiresApi
import com.runningmessage.kotref.utils.defaultUncaughtExceptionHandler
import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.sLog
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*
import java.io.IOException

/**
 * 异常处理
 *
 * Created by Lorss on 18-11-29.
 */
class ExceptionHandling {

    companion object {

        /**异常的传播*/
        fun t01() = wrap {

            runBlocking {

                defaultUncaughtExceptionHandler { _, it ->
                    launch { sLog("UncaughtException: $it") }
                    false
                }
                val job = GlobalScope.launch {
                    mPrintln("Throwing exception from launch")
                    throw IndexOutOfBoundsException()
                }

                job.join()
                mPrintln("Joined failed job")

                val deferred = GlobalScope.async {
                    mPrintln("Throwing exception from async")
                    throw ArithmeticException()
                }

                try {
                    deferred.await()
                    mPrintln("Unreached")
                } catch (e: ArithmeticException) {
                    mPrintln("Caught ArithmeticException")
                }
            }
            /**[t01]*/
        }

        /**CoroutineExceptionHandler*/
        fun t02() = wrap {

            runBlocking {
                val handler = CoroutineExceptionHandler { _, exception ->
                    mPrintln("Caught $exception")
                }

                val job = GlobalScope.launch(handler) {
                    throw AssertionError()
                }

                val deferred = GlobalScope.async(handler) {
                    throw ArithmeticException()
                }

                joinAll(job, deferred)

                //mPrintln("${deferred.await()}") // m:lorss exec this line to print deferred ArithmeticException
            }
            /**[t02]*/
        }


        /**取消与异常*/
        fun t03() = wrap {

            /**
             * 当一个协程在没有任何理由的情况下使用	Job.cancel	取消的时候,它会被终止,但
             * 是它不会取消它的父协程。	无理由取消是父协程取消其子协程而非取消其自身的机制。
             */
            runBlocking {

                val parent = launch {

                    val child = launch {
                        try {
                            delay(Long.MAX_VALUE)
                        } finally {
                            mPrintln("Child is cancelled")
                        }
                    }

                    yield()
                    mPrintln("Cancelling child")
                    child.cancel()
                    child.join()
                    yield()
                    mPrintln("Parent is not cancelled")

                }
                parent.join()

            }
            /**[t03]*/
        }

        /**取消与异常 - handler where to use*/
        fun t031() = wrap {

            runBlocking {
                val handler = CoroutineExceptionHandler { _, exception ->
                    mPrintln("Caught $exception")
                }

                val job = GlobalScope.launch(handler) {

                    launch {
                        //第一个协程
                        try {
                            delay(Long.MAX_VALUE)
                        } finally {
                            withContext(NonCancellable) {
                                mPrintln("Children are cancelled, but exception is not handled until all children terminate")
                                delay(100)
                                mPrintln("The first child finished its non cancellable block")
                            }
                        }
                    }

                    launch {
                        delay(10)
                        mPrintln("Second child throws an exception")
                        throw ArithmeticException()
                    }

                }

                job.join()

            }
            /**[t031]*/
        }

        /**异常聚合*/
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun t04() = wrap {

            runBlocking {
                val handler = CoroutineExceptionHandler { _, exception ->
                    mPrintln("Caught $exception with suppressed ${exception.suppressed.contentToString()}")

                }

                val job = GlobalScope.launch(handler) {
                    launch {
                        try {
                            delay(Long.MAX_VALUE)
                        } finally {
                            throw ArithmeticException()
                        }
                    }

                    launch {
                        delay(100)
                        throw IOException()
                    }

                    delay(Long.MAX_VALUE)
                }

                job.join()
            }
            /**[t04]*/
        }


        /**异常聚合-CancellationException 解包*/
        fun t041() = wrap {

            runBlocking {
                val handler = CoroutineExceptionHandler { _, exception ->
                    mPrintln("Caught original $exception")
                }

                val job = GlobalScope.launch(handler) {

                    val inner = launch {

                        launch {

                            launch {

                                throw IOException()
                            }
                        }
                    }


                    try {
                        inner.join()
                    } catch (e: CancellationException) {
                        mPrintln("Rethrowing CancellationException with original cause")
                        throw e// throw CancellationException but handler catches original cause exception
                    }

                }

                job.join()
            }
            /**[t041]*/
        }

        /**监督任务*/
        fun t05() = wrap {

            runBlocking {
                val supervisor = SupervisorJob()

                with(CoroutineScope(coroutineContext + supervisor)) {

                    val firstChild = launch(CoroutineExceptionHandler { _, _ -> }) {
                        mPrintln("First child is failing")
                        throw AssertionError("First child is cancelled")
                    }

                    val secondChild = launch {

                        firstChild.join()

                        mPrintln("First child is cancelled : ${firstChild.isCancelled}, " +
                                "but second child is still active")

                        try {
                            delay(Long.MAX_VALUE)
                        } finally {
                            mPrintln("Second child is cancelled because supervisor is cancelled")
                        }
                    }
                    firstChild.join()
                    mPrintln("Cancelling supervisor")
                    supervisor.cancel()
                    secondChild.join()

                }
            }
            /**[t05]*/
        }

        /**监督作用域*/
        fun t06() = wrap {

            runBlocking {
                try {
                    supervisorScope {
                        val child = launch {
                            try {
                                mPrintln("Child is sleeping")
                                delay(Long.MAX_VALUE)
                            } finally {
                                mPrintln("Child is cancelled")
                            }
                        }

                        yield()// 使用yield 来给我们的子任务一些执行时间

                        mPrintln("Throwing exception from scope")
                        throw AssertionError()
                    }
                } catch (e: AssertionError) {
                    mPrintln("Caught assertion error")
                }
            }
            /**[t06]*/
        }

        /**监督协程中的异常*/
        fun t07() = wrap {

            runBlocking {
                val handler = CoroutineExceptionHandler { _, exception ->
                    mPrintln("Caught $exception")

                }
                /**
                 * 常规的任务和监督任务之间的另一个重要区别是异常处理。	每一个子任务应该通过异常处理
                 * 机制处理自身的异常。	这种差异来自于子任务的执行失败不会传播给它的父任务的事实。
                 * */
                supervisorScope {
                    val child = launch(handler) {
                        mPrintln("Child throws an exception")
                        throw AssertionError()
                    }
                    mPrintln("Scope is completing")
                }
                mPrintln("Scope is completed")

            }
            /**[t07]*/
        }

        /**[ExceptionHandling.Companion]*/
    }
}
