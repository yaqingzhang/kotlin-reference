package com.runningmessage.kotref.kotlin.overview

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.runBlocking
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Created by Lorss on 18-12-20.
 */
class Feature0103 {

    companion object {

        //多平台项目

        //契约
        // 智能转换 - 通过声明函数调用的结果与所传参数之间的关系来改进智能转换分析
        //============== Code Start ============
        /**智能转换*/
        @ExperimentalContracts
        fun t01() = wrap {

            runBlocking {
                var s: String? = ""
                require(s is String)
                mPrintln(s.length)
            }
            /**[t01]*/
        }


        // 智能转换 - 通过声明函数调用的结果与所传参数之间的关系来改进智能转换分析
        //============== Code End ============

        // 智能转换 - 高阶函数的情况下改进变量初始化的分析
        // ==================== Code Start =============
        /**智能转换*/
        @ExperimentalContracts
        fun t02() = wrap {

            runBlocking {
                val x: Int = 1
// TODO m:lorss 以下代码 compile 会报错, 先注掉
//                synchronize(this) {
//                    x = 42 // 编译器知道传给 "synchronize" 的 lambda 表达式刚好
//                    // 只调用了一次, 因此不会报重复赋值错
//                }
                mPrintln(x)// 编译器知道一定会调用该 lambda 表达式而执行
                // 初始化操作, 因此可以认为"x" 在这里已初始化
            }
            /**[t02]*/
        }

        // 智能转换 - 高阶函数的情况下改进变量初始化的分析
        // ==================== Code End =============

        //标准库中的契约 e.g. kotlin/text/Strings.kt # CharSequence?.isNullOrEmpty()

        //将 when 主语捕获到变量中 when(val response = executeRequest()){ response.foo()}
        //限制 作用域, 防止命名空间污染

        //接口中伴生对象的 @JvmStatic 与 @JvmField

        //注解类 中的内嵌声明

        //无参的 main

        //更多元的函数 Function22
        fun t(block: (Any, Any, Any, Any, Any, Any, Any, Any, Any, Any,
                      Any, Any, Any, Any, Any, Any, Any, Any, Any, Any,
                      Any, Any, Any, Any, Any, Any, Any, Any, Any, Any,
                      Any, Any, Any, Any, Any, Any, Any, Any, Any, Any,
                      Any, Any, Any, Any, Any, Any, Any, Any, Any, Any,
                      Any, Any, Any, Any, Any, Any, Any, Any, Any, Any
        ) -> Unit) {
            block.invoke("", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "")
        }

        //渐进式模式 代码稳定性和向后兼容性

        //内联类  常规类的限制版, 值得一提的是, 内联类必须有一个确切的属性
        // TODO 编译器将会利用这个限制, 积极提升内联类在运行时的表现, 并且使用底层的属性值来替换内联类的实例
        // TODO 用于省略构造方法调用, 减小 GC 压力, 开启其他优化操作

        /**内联类*/
        fun t03() = wrap {

            runBlocking {
                val name = Name("inline class")
                mPrintln(name.s)
            }
            /**[t03]*/
        }

        //无符号整型

        //@JvmDefault

        //多平台随机数

        // isNullOrEmpty 与 orEmpty

        // 非空数组间拷贝元素
        // ==================== Code Start =============
        /**拷贝数组*/
        fun t04() = wrap {

            runBlocking {
                val sourceArr = arrayOf("k", "o", "t", "l", "i", "n")
                val targetArr = sourceArr.copyInto(arrayOfNulls<String>(6),
                        3, startIndex = 3, endIndex = 6)
                mPrintln(targetArr.contentToString())
                sourceArr.copyInto(targetArr, startIndex = 0, endIndex = 3)
                mPrintln(targetArr.contentToString())

            }
            /**[t04]*/
        }

        // 非空数组间拷贝元素
        // ==================== Code End =============

        // associateWith
        // ==================== Code Start =============
        /**associateWith*/
        fun t05() = wrap {

            runBlocking {
                val keys = 'a'..'f'

                val map = keys.associateWith {
                    it.toString().repeat(5).capitalize()
                }

                map.forEach {
                    mPrintln(it)
                }
            }
            /**[t05]*/
        }

        // associateWith
        // ==================== Code End =============

        // ifEmpty   ifBlank
        // ==================== Code Start =============
        /**ifEmpty ifBlank: 判断String 是否全为空格*/
        fun t06() = wrap {

            runBlocking {
                printAllUp(listOf("foo", "Bar"))
                printAllUp(listOf("FOO", "BAR"))
            }
            /**[t06]*/
        }

        fun StringBuilder.printAllUp(data: List<String>) {
            val result = data
                    .filter { it.all { c -> c.isUpperCase() } }
                    .ifEmpty { listOf("<no uppercase>") }
            result.forEach { mPrintln(it) }
        }

        // ifEmpty   ifBlank
        // ==================== Code End =============

        //反射中的密封类 KClass.sealedSubclasses 用于列出所有密封类的子类型

        //微小的改变
        //Boolean	 	类型现在支持伴生对象。
        //Any?.hashCode()	 	扩展函数会在	 	null	 	的时候返回	0。
        //Char	 	类型现在提供了	 	MIN_VALUE	 / 	MAX_VALUE	 	常量。
        //SIZE_BYTES	 	和	 	SIZE_BITS	 	会作为原生类型伴生对象常量。

        //IDE 中的代码风格支持

        //kotlinx.serialization  实现性

        //脚本更新

        //草稿文件支持  .kts 可以在编译器里直接运行和获取计算结果
        
    }
}

@ExperimentalContracts
fun require(condition: Boolean) {
    // 这是一种语法格式, 告诉编译器:
    // "如果这个函数成功返回, 那么传入的'condition' 为 "true"
    contract { returns() implies condition }
    if (!condition) throw IllegalStateException("")
}

@ExperimentalContracts
fun synchronize(lock: Any?, block: () -> Unit) {
    // 告诉编译器:
    // "这个函数会在此时此处调用 'block', 并且刚好只调用一次"
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
}

inline class Name(val s: String)