package com.runningmessage.kotref.kotlin.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Lorss on 19-1-7.
 */
class Test {


    private suspend fun CoroutineScope.step1() = "step1: result"

    private suspend fun CoroutineScope.step2(param: String) = "step2: result"

    fun test() {

        GlobalScope.launch {
            val r1 = step1()

            val r2 = step2(r1)

            delay(2000)

            print(r2)
        }
    }


}