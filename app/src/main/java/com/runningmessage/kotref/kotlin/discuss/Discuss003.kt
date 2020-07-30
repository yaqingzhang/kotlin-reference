package com.runningmessage.kotref.kotlin.discuss

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap

/**
 * Created by Lorss on 19-1-3.
 */
class Discuss003 {


    companion object {

        fun t01() = wrap {

            val list = listOf<Int>()

            list.indices.forEach { index ->
                if (index == 2) return@forEach

                val item = list[index]

                // do something for item
            }
        }


        fun t02() = wrap {

            for (i in A()) {
                mPrintln(i)
            }
        }

        @Throws(Exception::class)
        fun t03(): Nothing = throw AssertionError()

        fun t04() {
            try {
                t03()
            } catch (e: Throwable) {

            }
        }
    }

}

class A {

    private var i = 0

    operator fun hasNext() = i < 10

    operator fun next() = i++


}

operator fun A.iterator() = this