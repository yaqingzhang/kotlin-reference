package com.runningmessage.kotref.kotlin.`class`

/**
 * 对象表达式与对象声明
 *
 * Created by Lorss on 18-12-29.
 */
class Objects {

    // 对象表达式 TODO object A, B(param){  override foo(){} }
    // TODO 如果只需要 '一个对象而已' 可以简单的写
    fun foo() {
        val ho = object {
            var x = 0
            var y = 0
        }

        print(ho.x + ho.y)
    }

    // TODO 匿名对象 可以用作只在本地和私有作用域中声明的类型
    // TODO 如果作为公有函数返回类型, 则实际类型是匿名对象的超类型, 如果没有声明任何超类型, 就会时Any

    // TODO 对象表达式中代码可以访问来自包含它的作用域的变量. (与 java 不同的是, 这不仅限于 final 变量)

    // 对象声明 单例模式
    // TODO 注意: 对象声明不能在局部作用域(即直接嵌套在函数内部), 但是他们可以嵌套到其他对象声明或非内部类中

    // 伴生对象 TODO companion
    // TODO 请注意: 即使伴生对象的成员看起来像其他语言的静态成员, 运行时仍然是真实对象的实例成员
    // TODO @JvmStatic

    // 对象表达式和对象声明之间的语义差异
    // TODO 对象表达式是在使用他们的地方立即执行(及初始化)的
    // TODO 对象声明是在第一次被访问到时延迟初始化的
    // TODO 伴生对象的初始化是在相应的类被加载(解析)时, 与Java 静态初始化器的语义相匹配


}