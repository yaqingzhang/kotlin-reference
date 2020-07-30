package com.runningmessage.kotref.kotlin.`class`

/**
 * 内联类
 *
 * Created by Lorss on 18-12-29.
 */
class InlineClass {

    // 内联类 TODO 内联类必须含有唯一的一个属性在主构造函数中初始化

    // 成员
    // TODO 内联类不能含有 init 代码快
    // TODO 内联类不能含有 inner 类
    // TODO 内联类不能含有 幕后字段 因此, 内联类只能含有简单的计算属性(不能含有延迟初始化/委托属性)

    // 继承
    // TODO 内联类允许 继承 接口
    // TODO 内联类不能继承其他类而且必须是final

    // 表示方式
    // TODO 在生成的代码中, Kotlin 编译器为每个内联类保留一个包装器.
    // TODO 内联类的实例可以在运行时表示为包装器或者基础类型
    // TODO 这就类似于 Int 可以表示为 原生类型int 或者包装类型 Integer
    // TODO 为了生成最优的代码, Kotlin 编译更倾向于使用基础类型而不是包装器

    // TODO 一般来说, 只要将内联类用作另一种类型, 它们就会被装箱

    fun asInline(f: Foo) {}
    fun <T> asGeneric(x: T) {}
    fun asInterface(i: I) {}
    fun asNullable(i: Foo?) {}
    fun <T> id(x: T): T = x

    fun test() {

        val f = Foo(42)

        asInline(f)     // 拆箱操作: 用作 Foo 本身

        asGeneric(f)    // 装箱操作: 用作泛型类型 T

        asInterface(f)  // 装箱操作: 用作类型 I

        asNullable(f)   // 装箱操作: 用作不同于 Foo 的可空类型 Foo?

        // 在下面这里例子中,'f' 首先会被装箱(当它作为参数传递给 'id' 函数时)
        // 然后又被拆箱(当它从'id' 函数中被返回时)
        // 最后, 'c' 中就包含了被拆箱后的内部表达(也就是 '42'), 和 'f' 一样

        val c = id(f)
    }

    // TODO 因为内联类既可以表示为基础类型又可以表示为包装器,
    // TODO 引用相等对于内联类而言毫无意思, 因此这也是禁止的

    // 名字修饰
    // TODO 为了缓解这种问题,一般会通过在函数名后面拼接一些稳定的哈希码来重命名函数
    /** TODO [compute] 将会被表示为 public final void compute-<hashcode>(int x)*/
    // TODO 在 Java 中 - 是一个 无效的 符号,
    // TODO 也就是说在 Java 中不能调用使用内联类作为 形参的函数。 {:.note}

    //内联类与类型别名

    //内联类的实验性状态 TODO


}

inline class UInt(val x: Int)

// 在 JVM 平台上被表示为'public final void compute(int x)'
fun compute(x: Int) {}

// 同理,在 JVM 平台上也被表示为'public final void compute(int x)'!
fun compute(x: UInt) {}


interface I

inline class Foo(val i: Int) : I {

    val length: Int
        get() = i
}