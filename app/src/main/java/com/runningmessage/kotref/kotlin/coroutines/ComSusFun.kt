package com.runningmessage.kotref.kotlin.coroutines

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * ComposingSuspendingFunction
 * 组合挂起函数
 *
 * Created by Lorss on 18-11-28.
 */

class ComSusFun {

    companion object {

        /**默认顺序调用*/
        fun t01() = wrap {

            runBlocking {

                val time = measureTimeMillis {
                    val one = doSomethingUsefulOne()
                    val two = doSomethingUsefulTwo()
                    mPrintln("The answer is ${one + two}")
                }
                mPrintln("Completed in $time ms")
            }
            /**[t01]*/
        }

        private suspend fun doSomethingUsefulOne(): Int {
            delay(1000)
            return 13
        }

        private suspend fun doSomethingUsefulTwo(): Int {
            delay(1000)
            return 29
        }

        /**使用 async 并发*/
        fun t02() = wrap {

            runBlocking {

                val time = measureTimeMillis {
                    val one = async { doSomethingUsefulOne() }
                    val two = async { doSomethingUsefulTwo() }
                    mPrintln("The answer is ${one.await() + two.await()}")
                }
                mPrintln("Completed in $time ms")
            }
            /**[t02]*/
        }

        /**惰性启动的async*/
        fun t03() = wrap {

            runBlocking {
                val time = measureTimeMillis {
                    val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
                    val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }

                    one.start()
                    two.start()

                    /**如果我们在println中调用了await并且在这个协程中省略调用了start,
                     * 接下来await会开始执行协程并且等待协程执行结束*/
                    mPrintln("The answer is ${one.await() + two.await()}")
                }
                mPrintln("Completed in $time ms")
            }
            /**[t03]*/
        }


        /**async 风格的函数*/
        fun t04() = wrap {

            val one = somethingUsefulOneAsync()
            val two = somethingUsefulTwoAsync()

            runBlocking {
                mPrintln("The answer is ${one.await() + two.await()}")
            }
            /**[t04]*/
        }


        private fun somethingUsefulOneAsync() = GlobalScope.async { doSomethingUsefulOne() }

        private fun somethingUsefulTwoAsync() = GlobalScope.async { doSomethingUsefulTwo() }

        /**使用async的结构化并发*/
        fun t05() = wrap {

            runBlocking {

                val time = measureTimeMillis { mPrintln("The answer is ${concurrentSum()}") }

                mPrintln("Completed in $time ms")
            }
            /**[t05]*/
        }

        private suspend fun concurrentSum() = coroutineScope {
            val one = somethingUsefulOneAsync()
            val two = somethingUsefulTwoAsync()

            one.await() + two.await()
        }


        /**这种情况下,如果在 concurrentSum 函数内部发生了错误,并且它抛出了一个异常,所有在
         *作用域中启动的协程都将会被取消。
         */
        private suspend fun StringBuilder.failedConcurrentSum() = coroutineScope {

            val one = async {

                try {
                    delay(Long.MAX_VALUE)
                    42
                } finally {
                    mPrintln("First child was cancelled")
                }
            }

            val two = async<Int> {
                mPrintln("Second child throws an exception")
                throw ArithmeticException()
            }

            one.await() + two.await()
            /**[failedConcurrentSum]*/
        }

        /**使用async的结构化并发 - 异常处理*/
        fun t051() = wrap {

            runBlocking {
                try {
                    failedConcurrentSum()
                } catch (e: ArithmeticException) {
                    mPrintln("Computation failed with ArithmeticException")
                }
            }
            /**[t051]*/
        }
    }
}