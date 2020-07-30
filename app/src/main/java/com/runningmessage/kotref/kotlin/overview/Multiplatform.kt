package com.runningmessage.kotref.kotlin.overview

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import com.runningmessage.sharedcode.createApplicationScreenMessage

/**
 * 多平台
 *
 * Created by Lorss on 18-12-19.
 */
class Multiplatform {


    /** see [https://discuss.kotliner.cn/t/topic/440/4]*/
    data class KModel(val id: Int = 10, val name: String, val third: Int = 30, val forth: Int = 40)
    // 注意 ：  KModel::class 对应的是  KClass 对象， 里面构造方法数量为 1 ， 代表只声明了一个方法
    // 这里没有声明 @JvmOverloads , 所以生成class (注意对应的是 KModel::class.java 即java.reflect.Class对象) 文件里只会有两个构造方法
    // 方法1.  全部参数的构造方法
    // 方法2.  全部参数 + 另外另外两个参数的构造方法 KModel(Int, String, Int, Int, Int, DefaultConstructorMarker)
    //    DefaultConstructorMarker 只是标示了倒数第二个参数, 转换为二进制后, 来表示 , 第几个参数使用默认值
    //    TODO 具体可以参考下面的例子 [tConstructor]

    data class JavaModel @JvmOverloads constructor(val id: Int = 1, val name: String, val third: Int = 3)
    // 这里声明 @JvmOverloads , 所以生成class 文件里会包含所有可能的构造方法, 这里为4个构造方法


    companion object {

        fun tConstructor(): String {


            val sb = StringBuilder()

            val m0 = KModel(name = "1")
            //调用KModel 下注释 方法2 new KModel(0, "1", 0, 0, 13, null);
            // 13=1101 倒序查看第1,3,4位数字为1, 代表 第1, 3, 4 个参数没有传, 使用默认值

            val m11 = KModel(2, name = "1")
            //调用KModel 下注释 方法2 new KModel(2, "1", 0, 0, 12, null);
            // 12=1100 倒序查看第3,4位数字为1, 代表 第3, 4 个参数没有传, 使用默认值

            val m12 = KModel(name = "1", third = 3)
            //调用KModel 下注释 方法2 new KModel(0, "1", 3, 0, 9, null); 9=1001 第1, 4 个参数没有传, 使用默认值

            val m13 = KModel(name = "1", forth = 4)
            //调用KModel 下注释 方法2 new KModel(0, "1", 0, 4, 5, null); 5=0101 第1, 3 个参数没有传, 使用默认值

            val m21 = KModel(name = "1", third = 3, forth = 4)
            //调用KModel 下注释 方法2 new KModel(0, "1", 3, 4, 1, null); 1=0001 第1个参数没有传, 使用默认值

            val m22 = KModel(2, name = "1", third = 3)
            //调用KModel 下注释 方法2 new KModel(2, "1", 3, 0, 8, null); 8=1000 第4个参数没有传, 使用默认值

            val m23 = KModel(2, name = "1", forth = 4)
            //调用KModel 下注释 方法2 new KModel(2, "1", 0, 4, 4, null); 4=0100 第3个参数没有传, 使用默认值

            val m3 = KModel(0, "", 1, 2)

            //以下通过Class 对象里的构造函数对象生成对象
            val con = KModel::class.java.constructors[1].newInstance(2, "con", 0, 0, 12, null)
            sb.appendln().appendln(con).appendln()

            //以下通过KClass 对象里的构造函数对象生成对象
            val conn = KModel::class.constructors.first().let {
                it.callBy(mapOf(it.parameters[0] to 222, it.parameters[1] to "connnn"))
            }
            sb.appendln().appendln(conn).appendln()

            val size = KModel::class.constructors.size// 1
            sb.appendln("Model:class.constructors.size = $size")
            for (c in KModel::class.constructors) {
                sb.appendln(c.toString())
            }
            val size2 = KModel::class.java.constructors.size// 2
            sb.appendln("Model:class..javaconstructors.size = $size2")
            for (c in KModel::class.java.constructors) {
                sb.appendln(c.toString())
            }

            val size3 = JavaModel::class.constructors.size// 1
            sb.appendln("Model:class.constructors.size = $size3")
            for (c in JavaModel::class.constructors) {
                sb.appendln(c.toString())
            }
            val size4 = JavaModel::class.java.constructors.size// 4
            sb.appendln("Model:class..javaconstructors.size = $size4")
            for (c in JavaModel::class.java.constructors) {
                sb.appendln(c.toString())
            }

            return sb.toString()

        }

        fun tString() = wrap {

            mPrintln(createApplicationScreenMessage())

            /** TODO fix build Error : run the command on terminal:
             *
             * sudo xcode-select --switch /Applications/Xcode.app/Contents/Developer/
             *
             * Error:
            Task :SharedCode:compileKotlinIOS
            xcrun: error: SDK "iphonesimulator" cannot be located
            xcrun: error: SDK "iphonesimulator" cannot be located
            xcrun: error: unable to lookup item 'Path' in SDK 'iphonesimulator'
            exception: org.jetbrains.kotlin.konan.KonanExternalToolFailure: The /usr/bin/xcrun command returned non-zero exit code: 1.
             */

        }


    }
}


/*
// 为所有平台编译
enum class LogLevel {
    DEBUG, WARN, ERROR
}

// 预期的平台相关API
internal expect fun writeLogMessage(message: String, logLevel: LogLevel)

// 可在公共代码中使用预期的API
fun logDebug(msg: String) = writeLogMessage(msg, LogLevel.DEBUG)

// ON JVM
internal actual fun writeLogMessage(message: String, logLevel: LogLevel){}
*/

