package com.runningmessage.kotref.kotlin.overview

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.runBlocking
import java.lang.ref.WeakReference
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * 1.1的新特性
 *
 * Created by Lorss on 18-12-19.
 */
class Feature0101 {

    companion object {

        //JavaScript 目标平台不再是实验性的

        //协程(实验性的)

        //类型别名 typealias 请注意,类型名称(初始名和类型别名)是可互换的 see[https://github.com/Kotlin/KEEP/blob/master/proposals/type-aliases.md]

        //已绑定的可调用引用, 现在可以使用 :: 操作符来获取指向特定对象实例的方法或属性的成员引用
        //TODO m:lorss 可以认为方法引用等价于 lambda 表达式

        //密封类和数据类

        //lambda 表达式中的解构

        //下划线用于未使用的参数 适用于具有多个参数的lambda表达式 , 也适用于解构声明

        //数字字面值中的下划线

        //对于属性的更短语法

        //内联属性访问器

        //局部委托属性

        //委托属性绑定的拦截
        //=============== Code Start =================

        class ResourceDelegate<T> : ReadOnlyProperty<MyUI, T> {
            override fun getValue(thisRef: MyUI, property: KProperty<*>): T {
                throw IllegalStateException("Not Implement")
            }
        }


        class ResourceLoader<T>(id: ResourceID<T>) {

            operator fun provideDelegate(
                thisRef: MyUI
                , property: KProperty<*>
            )
                    : ReadOnlyProperty<MyUI, T> {
                checkProperty(thisRef, property)

                return ResourceDelegate()
            }
        }

        private fun checkProperty(thisRef: MyUI, property: KProperty<*>) {
            throw IllegalStateException("Not Implement")
        }

        class ResourceID<T> {
            companion object {
                val image_id = ResourceID<Int>()
                val text_id = ResourceID<Int>()
            }
        }

        fun <T> bindResource(id: ResourceID<T>): ResourceLoader<T> = ResourceLoader(id)

        open class MyUI {
            val image by bindResource(ResourceID.image_id)
            val text by bindResource(ResourceID.text_id)
        }

        //委托属性绑定的拦截
        //=============== Code End =================

        //泛型枚举值访问
        //=============== Code Start =================

        enum class RGB { RED, GREEN, BLUE }

        inline fun <reified T : Enum<T>> printAllValues() {
            print(enumValues<T>().joinToString { it.name })
        }


        //泛型枚举值访问
        //=============== Code End =================

        //对于 DSL 中隐式接收者的作用域控制 	@DslMarker

        //rem 操作符

        //onEach()

        //also() takeIf() 和 takeUnless()
        //TODO also 就像 apply :它接受接收者、做一些动作、并返回该接收者。
        //二者区别是在 apply 内部的代码块中接收者是 this
        //而在 also 内部的代码块中是 it (并且如果你想的话, 你可以给它另一个名字)。
        //当你不想掩盖来自外部作用域的 this 时这很方便

        //groupingBy()
        //=============== Code Start =================
        fun testGroupingBy() = wrap {
            val words = "one two three four five six seven eight nine ten".split(' ')
            //sampleStart
            val frequencies = words.groupingBy { it.first() }.eachCount()
            //sampleEnd
            mPrintln("Counting first	 letters : $frequencies.")
            //	另一种方式是使用“groupBy”和“mapValues”创建一个中间的映射,
            //	而“groupingBy”的方式会即时计数。
            val groupBy = words.groupBy { it.first() }.mapValues { (_, list) -> list.size }
            mPrintln("Comparing the result with using 'groupBy' : ${groupBy == frequencies}.")
        }
        //groupingBy()
        //=============== Code End =================

        //Map.toMap() 和 Map.toMutableMap()

        //Map.minus(key)  map - "key"

        //minOf() 和 maxOf()

        //类似数组的列表实例化函数 List<T>(size){ index -> T } MutableList<T>(size){ index -> T }

        //Map.getValue() 如果Map 是 withDefault 生成, 则找不到值不再抛异常, 而是返回默认值

        //可变闭包变量
        //=============== Code Start =================
        //TODO see https://discuss.kotliner.cn/t/topic/438/3
        class ThreadUtils {
            companion object {

                fun runOnBackThread(task: () -> Unit) {
                    // do something
                }
            }
        }

        fun shareQuery(sql: String) {

            ThreadUtils.runOnBackThread {
                sql.length//TODO 这里会提示  'value captured in a closure'
            }
        }

        fun shareQueryWrap(sql: String) = wrapWeak(sql) {
            ThreadUtils.runOnBackThread {
                val s = it.get()//TODO 这里仍然会提示  'value captured in a closure'，
                // 但是不会造成内存泄漏，
                // 当然这里的String  是没必要用弱引用包裹的， 只是举个例子
                s?.length
            }
        }

        fun <T> wrapWeak(target: T, block: (WeakReference<T>) -> Unit) {
            val ref = WeakReference(target) // TODO 用弱引用包装， 防止某些对象内存泄漏
            block(ref)
        }
        //可变闭包变量
        //=============== Code End =================

        /**构造方法重载*/
        fun tConstructor() = wrap {

            runBlocking {

                val m1 = Model(name = "1")

                val m2 = Model(2, "2")

                mPrintln(m1.toString() + m2)
            }
            /**[tConstructor]*/
        }

        data class Model(val id: Int = 1, val name: String)

        //javax.scripting 支持 其API允许在运行时求值代码段

        //kotlin.reflect.full

        //TODO JavaScript
    }
}