package com.runningmessage.kotref.kotlin.discuss

import com.runningmessage.kotref.java.discuss.ApiException
import com.runningmessage.kotref.java.discuss.ExtApiException
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1


/**
 * Created by Lorss on 19-2-15.
 */
class Discuss005 {


    companion object {

        fun <R, T> property1(property: KProperty1<R, T>) = property
        fun <R, T> mutableProperty1(property: KMutableProperty1<R, T>) = property

        public val ApiException.throwableMessage: String?
            get() = property1(Throwable::message).get(this)

        public var ApiException.apiMessage
            get() = mutableProperty1<ApiException, String>(ApiException::message).get(this)
            set(value) {
                mutableProperty1<ApiException, String>(ApiException::message).set(this, value)
            }

        /***/
        fun t01() = wrap {

            runBlocking {

                val exception = ApiException(Throwable(), 1)
                exception.code
                //exception.message // TODO compile error

                ExtApiException.getMessage(exception)

                ApiException::class.java.getDeclaredField("message").get(exception)

                exception.throwableMessage
                exception.apiMessage

            }
            /**[t01]*/
        }


    }


}