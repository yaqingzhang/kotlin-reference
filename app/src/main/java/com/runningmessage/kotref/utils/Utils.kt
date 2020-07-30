package com.runningmessage.kotref.utils

import kotlinx.coroutines.CoroutineName
import kotlin.coroutines.coroutineContext

/**
 * Created by Lorss on 18-11-26.
 */
fun wrap(apply: StringBuilder.() -> Unit): StringBuilder {
    val sb = StringBuilder("\n")
    try {
        apply.invoke(sb)
    } catch (e: Exception) {
        sb.mPrintln("#wrap: catch $e")
    } finally {
    }
    return sb
}

/** append String for [obj] with suffix : \n*/
fun StringBuilder.mPrintln(obj: Any?) {
    append(obj?.toString() ?: "").append("\n")
}

/** append String for [obj] with prefix : the name of current Thread*/
fun StringBuilder.log(obj: Any?) {
    append("[ ${Thread.currentThread().name} ] ")
    mPrintln(obj)
}

/** append String for [obj] with prefix : the name of current Thread and the name of current Coroutine*/
suspend fun StringBuilder.sLog(obj: Any?) {
    append("[ ${Thread.currentThread().name}  @${coroutineContext[CoroutineName]} ] ")
    mPrintln(obj)
}

private val defaultUncaughtExceptionHandlerList: ArrayList<(Thread, Throwable) -> Boolean> = ArrayList()

fun defaultUncaughtExceptionHandler(handler: (Thread, Throwable) -> Boolean) {
    if (defaultUncaughtExceptionHandlerList.size == 0) {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            for (h in defaultUncaughtExceptionHandlerList) {
                if (h.invoke(thread, throwable)) break
            }
        }
    }
    defaultUncaughtExceptionHandlerList.add(handler)
}

