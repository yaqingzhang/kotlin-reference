package com.runningmessage.kotref.kotlin.discuss

import android.widget.Button
import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.lang.reflect.ParameterizedType

/**
 * Created by Lorss on 18-12-25.
 */
class Discuss {

    companion object {


        fun t01() {
            Button(null).onClick { }
        }

        fun <T : BaseModel> execute(): T {
            return CommonModel() as T
        }

        open class BaseModel {}

        class CommonModel : BaseModel() {}

        /***/
        fun t02() = wrap {

            runBlocking {
                demoReified<List<String>>()
            }
            /**[t02]*/
        }

        /***/
        fun t03() {
            StringBuilder().demoReified<List<String>>()
            /**[t03]*/
        }


        private inline fun <reified T> StringBuilder.demoReified() {
            val kClass = T::class
            val kParams = kClass.typeParameters
            val kParam = kParams[0]
            val kParamName = kParam.name
            val kParamIsReified = kParam.isReified

            val jParams = T::class.java.typeParameters
            val jSuperClass = T::class.java.genericSuperclass

            mPrintln(kClass)
            mPrintln(kParams)
            mPrintln("kSize = ${kParams.size}")
            mPrintln(kParam)
            mPrintln(kParamName)
            mPrintln(kParamIsReified)
            mPrintln(jParams)
            mPrintln("jSize = ${jParams.size}")
            mPrintln(jParams[0])
            mPrintln(jSuperClass)


            val obj = object : WrapGeneric<T>() {}
            val javaClass = obj.javaClass
            val parameterizedType = javaClass.genericSuperclass as ParameterizedType
            val inputType = parameterizedType.actualTypeArguments[0]
            mPrintln(inputType)

        }

        open class WrapGeneric<T>

        /**Wrap-Only class*/
        fun t04() = wrap {

            runBlocking {
                BuilderFactory.createBuilder<Test>()
            }
            /**[t04]*/
        }

    }


}

enum class Test

interface BuilderStatus

// Extract an Interface
interface Builder<E : Enum<E>> {
    val type: E?
    val start: Int
    val finish: Int
    val status: BuilderStatus?
}

private class TokenBuilder<E : Enum<E>>(
    override var type: E?, // declare using var
    override var start: Int,
    override var finish: Int,
    override var status: BuilderStatus?
) : Builder<E>


class BuilderFactory {

    companion object {

        fun <E : Enum<E>> createBuilder(): Builder<E> = object : Builder<E> by TokenBuilder<E>(
            null,
            0,
            0,
            null
        ) {}
    }

}



