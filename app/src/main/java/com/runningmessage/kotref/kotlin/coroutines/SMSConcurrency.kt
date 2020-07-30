package com.runningmessage.kotref.kotlin.coroutines

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * Shared Mutable State and Concurrency
 * 共享可变状态与并发
 *
 * Created by Lorss on 18-11-30.
 */

class SMSConcurrency {

    @ObsoleteCoroutinesApi
    companion object {

        /***
         * 如果使用volatile 这段代码运行速度更慢了,但我们最后仍然没有得到“Counter=100000”这个结果,因为
         * volatile变量保证可线性化(这是“原子”的技术术语)读取和写入变量,但在大量动作(在我
         * 们的示例中即“递增”操作)发生时并不提供原子性。
         */
        @Volatile    //在Kotlin中`volatile`是一个注解
        private var counter: Int = 0

        /**问题*/
        fun t01() = wrap {

            runBlocking {

                //val mtContext = newFixedThreadPoolContext(2, "mtPool")    //明确地用两个线程自定义上下文

                //below scope can replace with 'GlobalScope(mtContext)'

                GlobalScope.massiveRun(this@wrap) {
                    counter++
                }

                mPrintln("Counter = $counter")
            }
            /**[t01]*/
        }

        private suspend fun CoroutineScope.massiveRun(outer: StringBuilder, action: suspend () -> Unit) {
            val num = 100
            val times = 1000

            val time = measureTimeMillis {

                val jobs = List(num) {
                    launch {
                        repeat(times) { action() }
                    }
                }

                jobs.forEach {
                    it.join()
                }
            }

            outer.mPrintln("Completed ${num * times} actions in $time ms")

        }

        /**线程安全的数据结构*/
        fun t02() = wrap {

            val counter = AtomicInteger()
            runBlocking {
                GlobalScope.massiveRun(this@wrap) {
                    counter.incrementAndGet()
                }

                mPrintln("Counter = $counter")
            }
            /**[t02]*/
        }

        /**以细粒度限制线程*/
        fun t03() = wrap {

            val counterContext = newSingleThreadContext("CounterContext")
            counter = 0
            runBlocking {
                GlobalScope.massiveRun(this@wrap) {
                    withContext(counterContext) {
                        counter++
                    }
                }

                mPrintln("Counter = $counter")
            }
            /**[t03]*/
        }

        /**以粗粒度限制线程*/
        fun t04() = wrap {
            val counterContext = newSingleThreadContext("CounterContext")

            runBlocking {
                CoroutineScope(counterContext).massiveRun(this@wrap) {
                    counter++
                }

                mPrintln("Counter = $counter")
            }
            /**[t04]*/
        }

        /**互斥*/
        fun t05() = wrap {

            val mutext = Mutex()
            runBlocking {
                GlobalScope.massiveRun(this@wrap) {
                    mutext.withLock {
                        counter++
                    }
                }
                mPrintln("Counter = $counter")
            }
            /**[t05]*/
        }

        /**Actors*/
        fun t06() = wrap {

            runBlocking {

                val counter = counterActor()
                GlobalScope.massiveRun(this@wrap) {
                    counter.send(IncCounter)
                }

                val response = CompletableDeferred<Int>()
                counter.send(GetCounter(response))

                mPrintln("Counter = ${response.await()}")
                counter.close()
            }
            /**[t06]*/
        }

        private fun CoroutineScope.counterActor() = actor<CounterMsg> {

            var counter = 0
            for (msg in channel) {
                when (msg) {
                    is IncCounter -> counter++
                    is GetCounter -> msg.response.complete(counter)
                }
            }
        }


        /**[SMSConcurrency.Companion]*/
    }


}

sealed class CounterMsg
object IncCounter : CounterMsg()
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()

