package com.runningmessage.kotref.kotlin.coroutines

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/**
 * 通道
 *
 * Created by Lorss on 18-11-27.
 */
class Channels {

    companion object {

        /**通道基础*/
        fun t01() = wrap {

            runBlocking {

                val channel = Channel<Int>()

                launch {
                    for (i in 1..5) {
                        channel.send(i * i)
                    }
                }

                repeat(5) {
                    mPrintln(channel.receive())
                }

                mPrintln("Done!")

            }
        }

        /**关闭与迭代通道*/
        fun t02() = wrap {

            runBlocking {

                val channel = Channel<Int>()

                launch {
                    for (i in 1..5) {
                        channel.send(i * i)
                    }
                    channel.close()
                }

                for (x in channel) mPrintln(x)

                mPrintln("Done!")

            }
        }

        /** 构建通道生产者 */
        fun t03() = wrap {


            runBlocking {
                val squares = GlobalScope.produce {
                    for (x in 1..5) send(x * x)
                }

                squares.consumeEach { mPrintln(it) }

                mPrintln("Done!")
            }
        }

        /**管道*/
        fun t04() = wrap {

            runBlocking {

                val numbers = produce<Int> {
                    var x = 1
                    while (true) send(x++)
                }

                val squares = produce {
                    for (x in numbers) send(x * x)
                }

                for (i in 1..5) mPrintln(squares.receive())

                mPrintln("Done!")

                //cancel all Children of the Job
                coroutineContext.cancelChildren()
            }
        }


        /**使用管道的素数*/
        fun t05() = wrap {
            runBlocking {

                var cur = numberFrom(2)
                for (i in 1..10) {
                    val prime = cur.receive()// 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
                    mPrintln(prime)
                    cur = filter(cur, prime)//3, 5, 7, 9, 11,
                }

                coroutineContext.cancelChildren()
            }
        }

        fun CoroutineScope.numberFrom(start: Int) = produce {
            var x = start
            while (true) send(x++)
        }

        fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce {
            for (x in numbers) if (x % prime != 0) send(x)
        }


        /**扇出*/
        fun t06() = wrap {

            runBlocking {

                val numbers = produce {

                    var x = 1

                    while (true) {
                        send(x++)
                        delay(100)
                    }
                }

                repeat(5) {
                    launch {
                        //launchProcessor
                        /**
                         *注意我们如何使用for循环显式迭代通道以在launchProcessor代码中执行扇出。
                         *与[t03] consumeEach不同,这个for循环是安全完美地使用多个协程的。如果其中一个处理者
                         *协程执行失败,其它的处理器协程仍然会继续处理通道,而通过consumeEach编写的处理器
                         *始终在正常或非正常完成时消耗(取消)底层通道。
                         * */
                        for (msg in numbers) mPrintln("Processor #$it received $msg")
                    }
                }

                delay(1050)
                numbers.cancel()

            }
        }//t06


        /**扇入*/
        fun t07() = wrap {

            runBlocking {

                val channel = Channel<String>()

                launch { sendString(channel, "foo", 200L) }
                launch { sendString(channel, "BAR!", 500) }
                repeat(6) {
                    mPrintln(channel.receive())
                }

                coroutineContext.cancelChildren()

            }
            /**[t07]*/
        }


        private suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
            while (true) {
                delay(time)
                channel.send(s)
            }
            /**[sendString]*/
        }


        /**带缓冲的通道*/
        fun t08() = wrap {
            runBlocking {
                val channel = Channel<Int>(4)

                val sender = launch {
                    repeat(10) {
                        mPrintln("Sending $it")
                        channel.send(it)
                    }
                }

                delay(1000L)
                sender.cancel()

            }
            /**[t08]*/
        }

        /**通道是公平的*/
        fun t09() = wrap {

            runBlocking {

                val table = Channel<Ball>()

                launch { mPrintln(player("A1", table)) }
                launch { mPrintln(player("B1", table)) }

                launch { mPrintln(player("A2", table)) }
                launch { mPrintln(player("B2", table)) }

                table.send(Ball())
                delay(2000)

                coroutineContext.cancelChildren()

            }
            /**[t09]*/
        }

        data class Ball(var hits: Int = 0, var from: String = "Referee")

        private suspend fun StringBuilder.player(name: String, table: Channel<Ball>) {
            for (ball in table) {
                ball.hits++

                mPrintln("$name hit the $ball")

                ball.from = name

                delay(300)

                table.send(ball)

            }
            /**[player]*/
        }


        /**计时器通道*/
        fun t10() = wrap {

            runBlocking {

                val tickerChannel = ticker(delayMillis = 100, initialDelayMillis = 0)

                var nextElement = withTimeoutOrNull(1) {
                    tickerChannel.receive()
                }

                mPrintln("Initial element is available immediately: $nextElement")

                nextElement = withTimeoutOrNull(50) {
                    tickerChannel.receive()
                }//	所有随后到来的元素都经过了100毫秒的延迟

                mPrintln("Next element is not ready in 50 ms: $nextElement")

                nextElement = withTimeoutOrNull(60) {
                    tickerChannel.receive()
                }

                mPrintln("Next element is ready in 100 ms: $nextElement")

                //模拟大量消费延迟
                mPrintln("Consumer pause for 150ms")
                delay(150)

                //下一个元素立即可用 :: 因为间隔已经大于100ms
                nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
                println("Next element is available immediately after large consumer delay: $nextElement")

                //	请注意,`receive`	调用之间的暂停被考虑在内,下一个元素的到达速度更快
                nextElement = withTimeoutOrNull(60) {
                    tickerChannel.receive()
                }
                mPrintln("Next element is ready in 50ms after consumer pause in 150ms: $nextElement")

                tickerChannel.cancel()
            }
            /**[t10]*/
        }

    }
}