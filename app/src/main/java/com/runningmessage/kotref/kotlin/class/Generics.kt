package com.runningmessage.kotref.kotlin.`class`

/**
 * 泛型
 *
 * Created by Lorss on 18-12-29.
 */
class Generics {

    // 泛型

    // 型变 TODO 保证的时类型安全, 不可变性完全是另一回事

    // 声明处型变

    // 类型投影

    // 星投影  TODO Foo<*> 等价于 Foo<out T: TUpper>, 也等价于 Foo<in T: Nothing>

    // 泛型函数

    // 泛型约束 TODO fun <T> foo(param: T): Unit where T: CharSequence, T: Comparable<T>

    // 类型擦除 TODO 泛型声明用法执行的类型安全检测仅在编译期进行.
    // TODO 运行期泛型类型的实例不保留关于类型实参的任何信息

}