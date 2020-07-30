package com.runningmessage.kotref.kotlin.coroutines

import com.runningmessage.kotref.utils.log
import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.sLog
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*
import java.util.concurrent.locks.LockSupport
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

/**
 * 基础
 *
 * Created by Lorss on 18-11-23.
 */
class Basic {


    companion object {


        /**你的第一个协程程序*/
        fun t01() = wrap {

            // 在后台启动一个新的协程并继续
            GlobalScope.launch {

                /**
                 * see [CoroutineStart.invoke],
                 * [kotlinx.coroutines.intrinsics.startCoroutineCancellable],
                 * [kotlin.coroutines.intrinsics.createCoroutineUnintercepted],
                 * [IntrinsicsJvm.kt#createCoroutineUnintercepted],
                 * [kotlin.coroutines.jvm.internal.BaseContinuationImpl],
                 * [kotlin.coroutines.intrinsics.intercepted],
                 * [IntrinsicsJvm.kt#intercepted],
                 * [kotlin.coroutines.jvm.internal.ContinuationImpl.intercepted],
                 * [CoroutineDispatcher.interceptContinuation],
                 * [DispatchedContinuation.resumeWith] dispatcher->
                 * [kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith] computation->
                 * [kotlin.coroutines.jvm.internal.BaseContinuationImpl.invokeSuspend] ->
                 * [AbstractContinuation.resumeWith] completion end
                 *
                 * */
                /***
                 * see [Delay.delay],
                 * [suspendCancellableCoroutine],
                 * [suspendCoroutineUninterceptedOrReturn]
                 *
                 */
                // 非阻塞的等待 1 秒
                delay(1000L)

                mPrintln("World!")
                sLog("#GlobalScope.launch")
            }

            mPrintln("Hello, ")
            log("#Main")

            // 阻塞主线程 2 秒来保证 JVM 存活
            Thread.sleep(2000)
        }


        /**桥接阻塞与非阻塞的世界*/
        fun t02() = wrap {

            GlobalScope.launch {
                delay(1000L)
                mPrintln("World!")
                sLog("#GlobalScope.launch")
            }

            mPrintln("Hello, ")

            /**
             * see [BlockingEventLoop.processNextEvent],
             * [BlockingEventLoop.schedule],
             * [BlockingEventLoop.shouldUnpark]
             *
             * see [BlockingCoroutine.joinBlocking],
             * [TimeSource.parkNanos],
             * [LockSupport.parkNanos]
             *
             * see [BlockingCoroutine.onCompletionInternal],
             * [LockSupport.unpark]
             * */
            runBlocking {

                // 这个表达式阻塞主线程
                delay(2000L)
                sLog("#Main.runBlocking")
            }

        }

        /**等待一个任务*/
        fun t03() = wrap {


            runBlocking {
                val job = GlobalScope.launch {
                    // 启动一个新协程并保持对这个作业的引用
                    delay(1000L)
                    mPrintln("World!")
                }

                mPrintln("Hello, ")
                /**
                 * TODO m:lorss same to [t01] delay
                 *
                 * see [JobSupport.join],
                 * [JobSupport.joinSuspend],
                 * [suspendCancellableCoroutine],
                 * [suspendCoroutineUninterceptedOrReturn]
                 * */
                job.join()
            }


        }

        /**结构化的并发*/
        fun t04() = wrap {


            runBlocking {

                // 在 runBlocking 作用域中启动一个新协程
                // 一个外围的协程(这里是 runBlocking) 只有在它作用域内所有协程执行完毕后才会结束
                launch {
                    delay(1000L)
                    mPrintln("World!")
                }

                mPrintln("Hello, ")
            }


        }

        /**作用域构建器*/
        fun t05() = wrap {


            runBlocking {

                launch {
                    delay(200L)
                    mPrintln("\nTask from runBlocking")
                    sLog("#runBlocking.launch")
                }

                /***
                 * see [suspendCoroutineUninterceptedOrReturn],
                 * [Undispatched.kt#AbstractCoroutine.startUndispatchedOrReturn],
                 * [Undispatched.kt#AbstractCoroutine.undispatchedResult],
                 * [startCoroutineUninterceptedOrReturn],
                 * [IntrinsicsJvm.kt#startCoroutineUninterceptedOrReturn]
                 */
                // 启动一个新的协程作用域并且在所有子协程执行结束后并没有执行完毕
                // 该协程作用域内部等待子协程执行完毕时没有阻塞
                // Warn: 但是 runBlocking 会等待coroutineScope 协程作用域执行完毕,才会继续执行接下去的代码
                // TODO m:lorss 应该可以认为这里相当于调用了  delay(time) 挂起阻塞当前线程
                coroutineScope {
                    //Not blocking when waiting for complete

                    launch {
                        delay(500L)
                        mPrintln("\nTask from nested launch")
                        sLog("#runBlocking.coroutineScope.launch")
                    }

                    delay(100L)
                    mPrintln("\nTask from coroutineScope")
                    sLog("#runBlocking.coroutineScope")
                }

                mPrintln("\nCoroutineScope is over")
                sLog("#runBlocking")
            }


        }


        /**提取函数重构
         *
         * TODO m:lorss what's the meaning?
         *
         * 但是如果提取函数包含了一个调用当前作用域的协程构建器? 在这个示例中仅仅使用
         * suspend 来修饰提取出来的函数是不够的。在 CoroutineScope 调用 doWorld 方法是一种解
         * 决方案,但它并非总是适用,因为它不会使API看起来更清晰。 惯用的解决方法是使
         * CoroutineScope 在一个类中作为一个属性并包含一个目标函数, 或者使它外部的类实现
         * CoroutineScope 接口。 作为最后的手段,CoroutineScope(coroutineContext) 也是可以使用
         * 的,但是这样的结构是不安全的, 因为你将无法在这个作用域内控制方法的执行。只有私有
         * 的API可以使用这样的写法。
         */
        fun t06() = wrap {

            runBlocking {
                launch {
                    doHello()
                }

                createScope()

                mPrintln("Wrold! ")
            }


        }

        private suspend fun StringBuilder.createScope() {
            coroutineScope {
                launch {
                    delay(2000L)
                    mPrintln("CoroutineScope: ")
                }
            }
        }

        private suspend fun StringBuilder.doHello() {
            delay(1000L)
            mPrintln("Hello, ")
        }

        // 像守护线程一样的全局协程
        /** 在 [GlobalScope] 中启动的协程, 不能使它们所在的进程保活*/

    }


}