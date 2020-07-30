package com.runningmessage.kotref.kotlin.coroutines

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*

/**
 * 取消与超时
 *
 * Created by Lorss on 18-11-26.
 */
class CancelTimeout {


    companion object {


        /**取消协程的执行*/
        fun t01() = wrap {
            runBlocking {

                val job = launch {

                    repeat(2000) {
                        append("\nI'm Sleeping $it")
                        delay(500)
                    }
                }

                delay(1500L)
                mPrintln("\nmain: I'm tired of waiting!")
                // 取消该任务
                job.cancel()
                // 等待任务执行结束
                job.join()
                mPrintln("main: Now I can quit!")

            }
        }

        /**取消是协作的*/
        fun t02() = wrap {
            runBlocking {
                val startTime = System.currentTimeMillis()
                val job = launch(Dispatchers.Default) {

                    var nextPrintTime = startTime

                    var i = 0

                    // 一个执行计算的循环
                    // 会打印5次直到结束, 因为没有地方检查取消
                    // 使计算代码可取消 :
                    // -> 定期调用挂起函数
                    // -> 显式检查取消状态
                    /** [CoroutineScope.isActive]*/
                    while (i < 5) {
                        if (System.currentTimeMillis() >= nextPrintTime) {
                            mPrintln("I'm sleeping ${i++} ...")
                            nextPrintTime += 500L
                        }
                    }

                }

                delay(1300L)
                mPrintln("main: I'm tired of waiting!")
                // 协程的取消是协作的
                // 一段代码必须协作才能被取消
                // 所有 kotlinx.coroutines 中的挂起函数都是可被取消的
                // 他们检查协程的取消, 并在取消时抛出 CancellationException
                // 然而如果协程正在执行计算任务, 并且没有检查取消的话, 那么它是不能被取消的
                job.cancelAndJoin()
                mPrintln("main: Now I can quit.")
            }
        }


        /**在 finally 中释放资源*/
        fun t03() = wrap {
            runBlocking {

                val job = launch {

                    try {
                        repeat(2000) {
                            append("\nI'm Sleeping $it")
                            delay(500)
                        }
                    } finally {
                        mPrintln("I'm running finally...")
                    }
                }

                delay(1500L)
                mPrintln("\nmain: I'm tired of waiting!")
                // join 和 cancelJoin 等待了所有的终结动作执行完毕
                // 因为这时没有调用挂起函数
                job.cancelAndJoin()
                mPrintln("main: Now I can quit!")

            }
        }


        /**运行不能取消的代码块*/
        fun t04() = wrap {
            runBlocking {

                val job = launch {
                    try {
                        repeat(1000) {
                            mPrintln("I'm sleeping $it ...")
                            delay(500L)
                        }
                    } finally {
                        // 运行不能取消的代码块
                        withContext(NonCancellable) {
                            mPrintln("I'm running finally")
                            delay(1000L)
                            mPrintln("And I've just delayed for 1 sec because I'm non-cacellable")
                        }
                    }
                }

                delay(1500L)
                mPrintln("\nmain: I'm tired of waiting!")
                job.cancelAndJoin()
                mPrintln("main: Now I can quit!")
            }
        }

        /**超时*/
        fun t05() = wrap {


            runBlocking {

                //withTimeoutOrNull not throw Exception but return null
                //TODO m:lorss analyse below function
                /** [JobSupport.invokeOnCompletion]*/
                withTimeout(1300L) {
                    repeat(1000) {
                        mPrintln("I'm sleeping $it ...")
                        delay(500)
                    }
                }
            }
        }
    }


}