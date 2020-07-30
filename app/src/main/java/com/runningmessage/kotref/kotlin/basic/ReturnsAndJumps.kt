package com.runningmessage.kotref.kotlin.basic

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.runBlocking

/**
 * Created by Lorss on 18-12-27.
 */
class ReturnsAndJumps {

    companion object {


        // 标签处返回
        // ==================== Code Start =============
        /**标签处返回*/
        fun t01() = wrap {

            runBlocking {

                return00()
                mPrintln("After return00 ... ")

                // same to 'continue'
                // =======================
                return01()
                mPrintln("After return01 ... ")

                return02()
                mPrintln("After return02 ... ")

                return03()
                mPrintln("After return03 ... ")

                // same to 'continue'
                // =======================

                // simulate 'break' by nested lambda
                return04()
                mPrintln("After return04 ... ")

            }
            /**[t01]*/
        }

        private fun StringBuilder.return00() {
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) return
                mPrintln(it)
            }
            mPrintln("this point is unreachable")
        }

        private fun StringBuilder.return01() {
            listOf(1, 2, 3, 4, 5).forEach lit@{
                if (it == 3) return@lit
                mPrintln(it)
            }
            mPrintln("done with explicit label")
        }

        private fun StringBuilder.return02() {
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) return@forEach
                mPrintln(it)
            }
            mPrintln("done with implicit label")
        }

        private fun StringBuilder.return03() {
            listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
                if (value == 3) return
                mPrintln(value)
            })
            mPrintln("done with anonymous function")
        }

        private fun StringBuilder.return04() {

            run loop@{

                listOf(1, 2, 3, 4, 5).forEach {
                    if (it == 3) return@loop
                    mPrintln(it)
                }

            }

            mPrintln("done with nested loop")
        }

        // 标签处返回
        // ==================== Code End =============
    }
}