package com.runningmessage.kotref.kotlin.discuss

import android.os.Build
import com.runningmessage.kotref.utils.sLog
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*
import kotlin.random.Random

/**
 * Created by Lorss on 19-2-15.
 */
class Discuss004 {


    companion object {

        val t: ThreadLocal<String> =
            if (Build.VERSION.SDK_INT >= 26) ThreadLocal.withInitial { "a" } else ThreadLocal()

        fun testContext1() = wrap {
            runBlocking {
                sLog(t.get())
                withContext(coroutineContext.plus(t.asContextElement("b"))) {
                    sLog(t.get())
                }
                sLog(t.get())
            }
        }

        fun testContext2() = wrap {
            runBlocking {
                sLog(t.get())
                withContext(coroutineContext.plus(t.asContextElement("b"))) {
                    sLog(t.get())
                    delay(Random.nextLong(100))
                    sLog(t.get())
                }
                sLog(t.get())
            }
        }

        fun testContext3() = wrap {
            runBlocking {
                sLog(t.get())
                async(coroutineContext.plus(t.asContextElement("b"))) {
                    sLog(t.get())
                    delay(Random.nextLong(100))
                    sLog(t.get())
                }.await()
                sLog(t.get())
            }
        }
    }
}