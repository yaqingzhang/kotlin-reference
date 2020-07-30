package com.runningmessage.kotref.kotlin.interop

import com.runningmessage.kotref.kotlin.overview.Multiplatform
import java.io.IOException

/**
 * Created by Lorss on 19-1-4.
 */
class CallKotlin {

    // 属性
    // TODO 如果属性的名称 以 is 开头, 则 isPro  setPro , 无论是不是 Boolean类型

    // 包级函数
    // TODO FileNameKt.foo()
    // TODO 可使用 @file:JvmName("Utils") 自定义修改类名称 FileNameKt
    // TODO 如果多个kotlin 文件 @JvmName 声明对应一个 Java类名, 需要使用 @file:JvmMultifileClass

    // 实例字段 TODO @JvmField

    // 静态字段 TODO 在Java 中对应静态字段
    // TODO @JvmField
    // TODO lateinit
    // TODO const 修饰符

    // 静态方法 TODO 在对象或者伴生对象中  @JvmStatic 既会在相应类中生成静态方法, 也生成对象中的实例方法

    // 可见性

    // KClass

    // 用@JvmName 解决签名冲突  TODO fun List<String>.foo   @JvmName("fooInt") fun List<Int>.foo

    // 生成重载 TODO @JavaOverloads
    /** see[Multiplatform]*/

    // 受检异常 TODO @Throws(IOException::class)
    fun foo(): Nothing = throw IOException()

    // 空安全性

    // 型变的泛型
    // TODO 参数 (如果是final 类型, 比如String不生成)out T = ? extends T ; in T = ? super T;
    // TODO 返回值 不生成, 如果强制需要通配符 使用  @JvmWildcard, 如果强制不需要 @JvmSuppressWildcard

    // Nothing 类型翻译
}