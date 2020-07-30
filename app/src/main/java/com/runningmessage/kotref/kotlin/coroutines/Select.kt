package com.runningmessage.kotref.kotlin.coroutines

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import kotlin.random.Random

/**
 * Select 表达式
 *
 * Created by Lorss on 18-11-30.
 */
class Select {

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    companion object {

        /**在通道中select*/
        fun t01() = wrap {

            runBlocking {
                val fizz = fizz()
                val buzz = buzz()
                repeat(7) {
                    this@wrap.selectFizzBuzz(fizz, buzz)
                }

                coroutineContext.cancelChildren()
            }
            /**[t01]*/
        }

        @ExperimentalCoroutinesApi
        private fun CoroutineScope.fizz() = produce {

            while (true) {
                delay(300)
                send("Fizz")
            }
        }

        @ExperimentalCoroutinesApi
        private fun CoroutineScope.buzz() = produce {

            while (true) {
                delay(500)
                send("Buzz")
            }
        }

        private suspend fun StringBuilder.selectFizzBuzz(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>) {

            select<Unit> {
                fizz.onReceive { mPrintln("fizz -> $it") }
                buzz.onReceive { mPrintln("buzz -> $it") }
            }
        }

        /**通道关闭时select*/
        fun t02() = wrap {

            runBlocking {
                val a = produce {
                    repeat(4) {
                        send("Hello $it")
                    }
                }
                val b = produce {
                    repeat(4) {
                        send("World $it")
                    }
                }

                repeat(8) { mPrintln(selectAorB(a, b)) }

                coroutineContext.cancelChildren()
            }
            /**[t02]*/
        }

        private suspend fun selectAorB(a: ReceiveChannel<String>, b: ReceiveChannel<String>) = select<String> {
            a.onReceiveOrNull {
                if (it == null)
                    "Channel 'a' is closed"
                else
                    "a -> $it"
            }
            b.onReceiveOrNull {
                if (it == null)
                    "Channel 'b' is closed"
                else
                    "b -> $it"
            }
        }

        /**Select 以发送*/
        fun t03() = wrap {

            runBlocking {

                val side = Channel<Int>()

                launch {
                    side.consumeEach {
                        mPrintln("Side channel has $it")
                    }
                }

                produceNumbers(side).consumeEach {
                    mPrintln("Consuming $it")
                    delay(250)
                }
                mPrintln("Done consuming")
                coroutineContext.cancelChildren()
            }
            /**[t03]*/
        }

        private suspend fun CoroutineScope.produceNumbers(side: SendChannel<Int>) = produce<Int> {

            for (num in 1..10) {
                delay(100)
                select<Unit> {
                    onSend(num) {}
                    side.onSend(num) {}
                }

            }
        }

        /**Select 延迟值*/
        fun t04() = wrap {

            runBlocking {
                val list = asyncStringList()
                val result = select<String> {
                    list.withIndex().forEach { (index, deferred) ->
                        deferred.onAwait {
                            "Deferred $index produced answer $it"
                        }
                    }
                }
                mPrintln(result)

                val countActive = list.count { it.isActive }
                mPrintln("$countActive coroutines are still active")

                // add below block to stop coroutines , so that be able to return
                delay(2000)
                coroutineContext.cancelChildren()
            }
            /**[t04]*/
        }

        private fun CoroutineScope.asyncString(time: Int) = async {
            delay(time.toLong())
            "Waited for $time ms"
        }

        private fun CoroutineScope.asyncStringList(): List<Deferred<String>> {
            val random = Random(3)
            return List(12) {
                asyncString(Math.abs(random.nextInt(2000)))
            }
        }

        /**在延迟值上切换*/
        fun t05() = wrap {

            runBlocking {


                val chan = Channel<Deferred<String>>()

                launch {
                    for (s in switchMapDeferred(chan, this@wrap)) mPrintln(s)
                }

                chan.send(asyncString("BEGIN", 100))
                delay(200)    //	充足的时间来生产	"BEGIN"
                chan.send(asyncString("Slow", 500))
                delay(100)    //	不充足的时间来生产	"Slow"
                chan.send(asyncString("Replace", 100))
                delay(500)    //	在最后一个前给它一点时间
                chan.send(asyncString("END", 500))
                delay(1000)    //	给执行一段时间
                chan.close()    //	关闭通道......
                delay(500)    //	然后等待一段时间来让它结束

            }
            /**[t05]*/
        }

        private fun CoroutineScope.asyncString(str: String, time: Long) = async {
            delay(time)
            str
        }

        private fun CoroutineScope.switchMapDeferred(input: ReceiveChannel<Deferred<String>>, outer: StringBuilder) = produce<String> {

            var current = input.receive()

            while (isActive) {

                val next = select<Deferred<String>?> {

                    input.onReceiveOrNull { it }

                    current.onAwait {
                        send(it)
                        input.receiveOrNull()
                    }
                }

                if (next == null) {
                    outer.mPrintln("Channel is closed")
                    break
                } else {
                    current = next
                }
            }
        }

        /**[Select.Companion]*/
    }
}