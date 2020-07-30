package com.runningmessage.kotref.kotlin.overview

import android.os.Build
import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.runBlocking

/**
 * Created by Lorss on 18-12-20.
 */
class Feature0102 {

    companion object {


        //多平台项目

        //注解中的数组字面值

        //lateinit 顶层属性与局部变量

        //检查lateinit变量是否已初始化  isInitialized

        //内联函数带有默认函数式参数

        //源自显式类型转换的信息会用于类型推断

        //智能类型转换改进

        //支持::foo 作为 this::foo的简写

        //阻断性变更:try 块后可靠智能转换 -Xlegacy-smart-cast-after-try 该参数会在Kotlin1.3中弃用

        //数据类弃用copy

        //弃用枚举条目中的嵌套类型  1.2Warn 1.3Error

        //弃用ararg单个命名参数  foo(items = i)  foo(items = *intArrayOf(1))  1.2Warn 1.3Error

        //弃用扩展的Throwable的泛型类的内部类  1.2Warn 1.3Error

        //弃用修改只读属性的幕后字段  1.2Warn 1.3Error

        //Kotlin 标准库构件与拆分包

        //windowed, chunked, zipWithNext
        //================= Code Start ============

        /**Code*/
        fun t01() = wrap {

            runBlocking {
                val items = (1..9).map { it * it }
                val chunkedIntoLists = items.chunked(4)
                val points3d = items.chunked(3) { (x, y, z) -> Triple(x, y, z) }
                val windowed = items.windowed(4)
                val slidingAverage = items.windowed(4) { it.average() }
                val pairwiseDifferences = items.zipWithNext { a, b -> b - a }
                //sampleEnd
                mPrintln("items:	$items\n")
                mPrintln("chunked into lists: $chunkedIntoLists")
                mPrintln("3D points: $points3d")
                mPrintln("windowed by 4: $windowed")
                mPrintln("sliding average by 4: $slidingAverage")
                mPrintln("pairwise differences: $pairwiseDifferences")
            }
            /**[t01]*/
        }

        //windowed, chunked, zipWithNext
        //================= Code End ============

        //fill, replaceAll, shuffle/shuffled
        //================= Code Start ============
        /**Code*/
        fun t02() = wrap {

            runBlocking {
                //sampleStart
                val items = (1..5).toMutableList()
                items.shuffle()
                mPrintln("Shuffled items: $items")
                if (Build.VERSION.SDK_INT >= 24) {
                    items.replaceAll { it * 2 }
                }
                mPrintln("Items doubled: $items")
                items.fill(5)
                mPrintln("Items filled with 5: $items")
                //sampleEnd
            }
            /**[t02]*/
        }

        //fill, replaceAll, shuffle/shuffled
        //================= Code End ============

        //kotlin-stdlib 中的数学运算

        //用于 BigInteger与BigDecimal的操作符与转换

        //浮点数到比特的转换

        //正则表达式现在可序列化

        //如果可用, Closeable.use 会调用 Throwable.addSuppressed 依赖 kotlin-stdlib-jdk7

        //构造函数调用规范化 TODO

        //Java 默认方法调用

        //阻断性变更: 平台类型 x.equals(null) 的一致行为  TODO NPE

        //阻断性变更: 修正平台null透过内联扩展接收者逃逸
    }
}