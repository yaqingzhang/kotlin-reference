package com.runningmessage.kotref.kotlin.coroutines

import com.runningmessage.kotref.utils.log
import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.sLog
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * 协程上下文与调度器
 *
 * Created by Lorss on 18-11-28.
 */
class ContextDispatchers {

    companion object {

        /**调度器与线程*/
        fun t01() = wrap {

            runBlocking {

                launch {
                    mPrintln("main bunBlocking : I'm working in thread ${Thread.currentThread().name}")
                }

                launch(Dispatchers.Unconfined) {
                    mPrintln("Unconfined : I'm working in thread ${Thread.currentThread().name}")
                }

                launch(Dispatchers.Default) {
                    mPrintln("Default : I'm working in thread ${Thread.currentThread().name}")
                }


                launch(newSingleThreadContext("MyOwnThread")) {
                    mPrintln("Default : I'm working in thread ${Thread.currentThread().name}")
                }

            }
            /**[t01]*/
        }


        /**非受限调度器vs受限调度器*/
        fun t02() = wrap {

            runBlocking {

                launch(Dispatchers.Unconfined) {

                    mPrintln("Unconfined: I'm working in thread ${Thread.currentThread().name}")

                    delay(500)
                    mPrintln("Unconfined: After delay in thread ${Thread.currentThread().name}")
                }

                launch {

                    mPrintln("main runBlocking: I'm working in thread ${Thread.currentThread().name}")

                    delay(1000)
                    mPrintln("main runBlocking: After delay in thread ${Thread.currentThread().name}")
                }
            }
            /**[t02]*/
        }

        /**调试协程与线程*/
        fun t03() = wrap {

            runBlocking {

                val a = async {
                    log("I'm computing a piece of the answer")
                    6
                }

                val b = async {
                    log("I'm computing another piece of the answer")
                    7
                }

                log("The answer is ${a.await() + b.await()}")
            }
            /**[t03]*/
        }

        /**在不同线程间跳转*/
        fun t04() = wrap {

            newSingleThreadContext("Ctx1").use { ctx1 ->

                newSingleThreadContext("Ctx2").use { ctx2 ->

                    runBlocking(ctx1) {
                        log("Started in ctx1")

                        withContext(ctx2) {
                            log("Working in ctx2")
                        }

                        log("Back to ctx1")
                    }
                }

            }
            /**[t04]*/
        }


        /**上下文中的任务*/
        fun t05() = wrap {

            runBlocking {
                mPrintln("My job is ${coroutineContext[Job]}")
            }
            /**[t05]*/
        }


        /**子协程*/
        fun t06() = wrap {

            runBlocking {
                val request = launch {

                    GlobalScope.launch {
                        mPrintln("job1: I run in GlobalScope and execute independently")
                        delay(1000)
                        mPrintln("job1: I am not affected by cancellation of the parent")
                    }

                    launch {
                        delay(100)
                        mPrintln("job2: I am child of request coroutine")
                        delay(1000)
                        mPrintln("job2: I will not execute this line if my parent request is cancelled")
                    }
                }
                delay(500)
                request.cancel()
                delay(1000)
                mPrintln("main: Who was survived request cancellation?")
            }
            /**[t06]*/
        }

        /**父协程的职责*/
        fun t07() = wrap {

            runBlocking {
                val request = launch {
                    repeat(3) { i ->
                        launch {
                            delay((i + 1) * 200L)
                            mPrintln("Coroutine $i is done")
                        }
                    }
                    mPrintln("request : I'm done and I don't explicitly join my children that are still active")
                }

                request.join()
                mPrintln("Now processing of the request is complete")

            }
            /**[t07]*/
        }

        /**命名协程以用于调试*/
        fun t08() = wrap {

            runBlocking(CoroutineName("main")) {

                val v1 = async(CoroutineName("v1coroutine")) {

                    delay(500)
                    sLog("Computing v1")
                    252
                }

                val v2 = async(CoroutineName("v2coroutine")) {

                    delay(1000)
                    sLog("Computing v2")
                    6
                }

                sLog("The answer for v1/v2 = ${v1.await() / v2.await()}")
            }
            /**[t08]*/
        }

        /**组合上下文中的元素*/
        fun t09() = wrap {

            runBlocking(Dispatchers.Default + CoroutineName("test")) {

                launch {
                    sLog("I'm working .. ")
                }
            }
            /**[t09]*/
        }


        /**通过显式任务取消*/
        fun t10() = wrap {

            runBlocking {
                val act = MyActivity()
                act.create()
                act.doSomething(this@wrap)
                mPrintln("Launched coroutines")

                delay(500L)
                mPrintln("Destroying activity!")
                act.destroy()
                delay(1000)
            }
            /**[t10]*/
        }


        class MyActivity : CoroutineScope {

            lateinit var job: Job


            override val coroutineContext: CoroutineContext
                get() = Dispatchers.Default + job

            fun create() {
                job = Job()
            }

            fun destroy() {
                job.cancel()
            }

            fun doSomething(outer: StringBuilder) {
                repeat(10) { i ->
                    launch {
                        delay((i + 1) * 200L)
                        outer.mPrintln("Coroutine $i is done")
                    }
                }
            }
            /**[MyActivity]*/
        }

        private val threadLocal = ThreadLocal<String?>()

        /**线程局部数据*/
        fun t11() = wrap {

            runBlocking {
                threadLocal.set("main")
                sLog("Pre-main, thread local value: '${threadLocal.get()}'")//main

                val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {

                    sLog("Launch start, thread local value: '${threadLocal.get()}'")//launch

                    yield()

                    sLog("After yield, thread local value: '${threadLocal.get()}'")//launch
                }

                job.join()

                sLog("Post-main, thread local value: '${threadLocal.get()}'")//main
            }
            /**[t11]*/
        }
    }

}