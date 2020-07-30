package com.runningmessage.kotref.kotlin.other


import com.runningmessage.kotref.kotlin.`class`.PropertyField
import com.runningmessage.kotref.kotlin.function.Function

/**
 * 操作符重载
 *
 * Created by Lorss on 19-1-3.
 */
class OperatorOverloading {


    // 操作符重载

    // 一元操作
    // 一元前缀操作符
    // TODO +a a.unaryPlus()
    // TODO -a a.unaryMinus()
    // TODO !a a.not()
    // 递增与递减
    // TODO a++ a.inc()
    // TODO a-- a.dec()
    // 二元操作
    // TODO a + b a.plus(b)
    // TODO a - b a.minus(b)
    // TODO a * b a.times(b)
    // TODO a / b a.div(b)
    // TODO a % b a.rem(b)   a.mod(b)(已弃用)
    // TODO a..b  a.rangeTo(b)
    // In 操作符
    // TODO a in b  b.contains(a)
    // TODO a !in b !b.contains(a)
    // 索引访问操作符
    // TODO a[i]                    a.get(i)
    // TODO a[i, j]                 a.get(i, j)
    // TODO a[i_1, ..., i_n]        a.get(i_1, ..., i_n)
    // TODO a[i] = b                a.set(i, b)
    // TODO a[i, j] = b             a.set(i, j, b)
    // TODO a[i_1, ..., i_n] = b    a.set(i_1, ..., i_n, b)
    // 调用操作符
    // TODO a()                     a.invoke()
    // TODO a(i)                    a.invoke(i)
    // TODO a(i, j)                 a.invoke(i, j)
    // TODO a(i_1, ..., i_n)        a.invoke(i_1, ..., i_n)
    // 广义赋值
    // TODO a += b a.plusAssign(b)
    // TODO a -= b a.minusAssign(b)
    // TODO a *= b a.timesAssign(b)
    // TODO a /= b a.divAssign(b)
    // TODO a %= b a.remAssign(b)   a.modAssign(b)(已弃用)
    // 相等与不相等操作符
    // TODO a == b a?.equals(b)?:(b === null)
    // TODO a != b !(a?.equals(b)?:(b === null))
    // 比较操作符
    // TODO a > b  a.compareTo(b) > 0
    // TODO a < b  a.compareTo(b) < 0
    // TODO a >= b a.compareTo(b) >= 0
    // TODO a >= b a.compareTo(b) <= 0
    // 属性委托操作符
    /** see [PropertyField]*/
    // TODO by  provideDelegate getValue setValue
    // 命名函数的中缀调用
    /** see [Function]*/
}