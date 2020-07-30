package com.runningmessage.kotref.kotlin.`class`

import java.util.*
import java.util.Collections.*

/**
 * 扩展
 *
 * Created by Lorss on 18-12-28.
 */
class Extensions {

    // 扩展不能真正的修改他们所扩展的类

    companion object {

        // 扩展函数

        // 扩展是静态解析的 TODO 跟接收者声明类型有关
        // TODO 没有在类中插入新成员, 仅仅时可以通过该类型的变量 用点表达式 去调用者个函数
        // TODO 如果成员函数 和 扩展函数 :  接收者, 名字, 参数相同, 则总是优先 取成员函数


        // 可空接收者  TODO e.g. Any?.toString()

        // 扩展属性
        // TODO 注意: 由于扩展属性没有实际的将成员插入类中, 因此幕后属性是无效的.
        // TODO 注意: 扩展属性不能有初始化器.  e.g. val Foo.bar = 1 //错误
        // TODO 注意: 他们的行为只能由显示提供的getters/setters定义.

        // 伴生对象的扩展  TODO e.g. fun MyClass.Companion.foo(){...}

        // 扩展的作用域

        // 扩展声明为成员 TODO 有多个接收者 this , 如果无歧义, 可以省略 (this.) foo
        // TODO 扩展接收者优先, 有歧义时
        // TODO this@OutterClass 外部类为分发接收者
        // TODO this@FooReceiver 方法接收者为扩展接收者

        // 关于可见性的说明

        // 动机 TODO java.util.Collections
        fun test() {
            val list = listOf<Int>()
            val otherList = listOf<Int>()

            // Java
            Collections.swap(
                list,
                Collections.binarySearch(list, Collections.max(otherList)),
                Collections.max(list)
            )

            // Java import static 但是不会有自动提醒, 比如 list.(where to auto alert)
            swap(list, binarySearch(list, max(otherList)), max(list))

            // Kotlin extensions
            fun List<Int>.swap(a: Int, b: Int?) = b?.let {
                Collections.swap(this, a, it)
            }

            list.swap(list.binarySearch(otherList.max()), list.max())

        }
    }
}