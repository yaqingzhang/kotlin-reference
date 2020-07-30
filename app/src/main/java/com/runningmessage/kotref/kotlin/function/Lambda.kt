package com.runningmessage.kotref.kotlin.function

import java.io.File

/**
 * 高阶函数与 lambda 表达式
 *
 * Created by Lorss on 19-1-3.
 */
class Lambda {

    companion object {

        // 高阶函数与 lambda 表达式

        // 高阶函数

        // 函数类型 TODO -> 右结合

        // 函数类型实例化
        // TODO 带与不带接收者的函数类型非字面值可以互换, 其中接收者可以替代第一个参数
        // TODO (A, B) -> C   ==  A.(B) -> C

        // 函数类型实例调用

        // 内联函数

        // Lambda 表达式与匿名函数

        // Lambda 表达式语法

        // 将 lambda 表达式传给最后一个参数

        // it : 单个参数的隐式名称

        // 从 lambda 表达式中返回一个值

        // 下划线用于未使用的变量

        // 在 lambda 表达式中解构

        // 匿名函数
        // TODO 匿名函数参数总是在括号内传递. 允许将函数留在圆括号外的简写语法仅适用于lambda 表达式

        // TODO Lambda表达式与匿名函数之间的另一个区别:
        // TODO 一个不带标签的 return 语句总是在用 fun 关键字声明的函数中返回
        // TODO 这意味着lambda 表达式 中的return 将从包含它的函数返回, 而 匿名函数中return 将从匿名函数自身返回

        // 闭包 TODO 与java不同的是可以修改闭包中捕获的变量

        // 带有接收者的函数字面值

        // 内联函数 TODO inline

        // 禁用内联

        // 非局部返回 TODO lambda 表达式内部禁用 return
        // TODO crossinline 间接传入的函数体中不允许非局部控制流


        fun ordinaryFunction(block: () -> Unit) {
            block()
        }

        fun foo() {

            ordinaryFunction {
                //return // TODO error
            }
            listOf<Int>().forEach { if (it > 0) return }
        }

        // 具体化的类型参数
        /**具体化类型参数*/
        fun t01() {

            File("").findParent<File>()
            /**[t01]*/
        }

        private inline fun <reified T> File.findParent(): T? {
            var p = parentFile
            while (p != null && p !is T) {
                p = p.parentFile
            }
            return p as T?
        }

        // 内联属性

        // 公有API内联函数的限制
    }
}