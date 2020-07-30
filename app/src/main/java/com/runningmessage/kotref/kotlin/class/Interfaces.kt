package com.runningmessage.kotref.kotlin.`class`

/**
 * 接口
 *
 * Created by Lorss on 18-12-29.
 */
class Interfaces {

    // 接口
    // TODO Kotlin 接口与 Java 8 类似, 既包含方法声明也包含实现.
    // TODO 与抽象类不同的是接口无法保存状态
    // TODO 可以有属性但必须声明为抽象或提供访问器实现

    // 接口中的属性
    // TODO 不能有幕后字段, 因此接口中声明的访问器不能访问它们

    interface MyInterface {

        val prop: Int // 抽象的

        val proWithImp: String
            get() = "imp"

        fun foo() {
            // 可选的方法体
        }
    }

    // 接口继承

    // 解决覆盖冲突

    interface A {
        fun foo()

        fun fooSame(): Any = 1
    }

    interface B {
        fun foo() {
            print("B foo")
        }

        fun fooSame() = ""
    }

    class C : A, B {


        override fun fooSame(): String = ""

        override fun foo() {

        }

    }
}