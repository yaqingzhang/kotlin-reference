package com.runningmessage.kotref.kotlin.`class`

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.runBlocking

/**
 * Created by Lorss on 18-12-27.
 */
class ClassAndInheritance {

    companion object {

        // 派生类初始化顺序
        // ==================== Code Start =============
        /**派生类初始化顺序
         *
         * 基类构造函数执行时,派生类中声明或覆盖的属性都还没有初始化。如果在基类
         * 初始化逻辑中(直接或通过另一个覆盖的 open 成员的实现间接)使用了任何一个这种属
         * 性,那么都可能导致不正确的行为或运行时故障。设计一个基类时,应该避免在构造函数、
         * 属性初始化器以及 init 块中使用 open 成员。
         * */
        fun t01() = wrap {

            runBlocking {

                mPrintln("Constructing Derived(\"hello\", \"world\")")
                val d = Derived(this@wrap, "hello", "world")
            }
            /**[t01]*/
        }

        open class Base(val sb: StringBuilder, val name: String) {

            init {
                sb.mPrintln("Initializing Base")
            }

            open val size: Int =
                name.length.also { sb.mPrintln("Initializing size in Base: $it") }
        }

        class Derived(
            sb: StringBuilder,
            name: String,
            val lastName: String
        ) : Base(sb, name.capitalize().also { sb.mPrintln("Argument for Base: $it") }) {

            init {
                sb.mPrintln("Initializing Derived")
            }

            override val size: Int = (super.size + lastName.length)
                .also { sb.mPrintln("Initializing size in Derived: $it") }
        }

        // 派生类初始化顺序
        // ==================== Code End =============
    }
}