package com.runningmessage.kotref.kotlin.`class`

import android.text.TextUtils
import com.runningmessage.kotref.kotlin.overview.Feature0101
import kotlin.properties.Delegates
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KMutableProperty1

/**
 * 属性与字段
 *
 * Created by Lorss on 18-12-29.
 */
class PropertyField {

    // 声明属性

    // 幕后字段

    var varPro = ""

    val valPro = ""

    val size
        get() = valPro.length

    // 幕后属性

    private var _table: Map<String, Int>? = null

    private var varPri = ""
        set(value) {
            if (!TextUtils.isEmpty(value)) {
                field = value
            }
        }

    private val valPri = ""

    public val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap()//类型参数已推断出
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }


    // 编译期常量

    // 延迟初始化属性与变量
    // TODO 该修饰符只能用于在类体中的属性(不是在主构造函数中声明的var属性, 并且该属性没有自定义getter )
    // TODO 自 Kotlin 1.2 起, 也用于顶层属性与局部变量
    // TODO 该属性或者变量必须为非空类型, 并且不能是原生类型
    // TODO 在初始化前访问一个 lateinit 属性会抛出一个特定异常, 该异常明确标识该属性被访问及它没有初始化的事实

    // 检测一个 lateinit var 是否已初始化(自 1.2 起)
    // TODO 在 属性引用 上使用 inline KProperty0<*>.isInitialized: Boolean
    // TODO e.g. instance::bar.isInitialized
    // TODO not Class::bar : KProperty1
    // TODO 仅词法级访问的属性可用, 即声明位于一个类型内, 位于其中一个外围类型中, 或者位于相同文件的顶层的属性


    lateinit var varLate: String

    var varLateInited = false
        get() = this::varLate.isInitialized

    fun testProperty() {
        val p: KMutableProperty1<PropertyField, String> = PropertyField::varLate

        val p1: KMutableProperty0<String> = this::varLate
    }

    // 覆盖属性

    // 委托属性
    // TODO 属性的委托不必实现任何接口, 但是需要提供一个 getValue() (与 setValue()  - 对于 var 属性)

    // 标准委托
    // ========== [Jump page183] Start ==========
    // TODO 延迟属性Lazy
    /**
     *  [LazyThreadSafetyMode.NONE],
     *  [LazyThreadSafetyMode.SYNCHRONIZED]
     *  [LazyThreadSafetyMode.PUBLICATION]
     *  */
    val varLazy by lazy { "Helllo" }
    // TODO 可观察属性

    var name: String by Delegates.observable("<no name>") { _, old, new ->
        print("$old -> $new")// TODO 如果想拦截一个赋值, 使用 Delegates.vetoable()
    }
    // TODO 把属性存储在映射中
    /**
     * inline MutableMap.getValue,
     * inline MutableMap.setValue  from kotlin/collections/MapAccessors.kt
     * */
    var varByMap by HashMap<String, String>().apply {
        setValue(
            this@PropertyField,
            this@PropertyField::varByMap,
            ""
        )
        getValue(this@PropertyField, this@PropertyField::varByMap)
    }

    // 局部属性委托
    // TODO 变量只会在第一次访问时计算
    // TODO if(someCondition && varByLazy.foo()) , 如果someCondition 失败, 那么该变量根本不会计算

    // 属性委托要求
    // TODO getValue: (in Owner, in KProperty<*>) -> out ValueType
    // TODO setValue: (in Owner, in KProperty<*>, in ValueType) -> out ValueType
    // TODO 两函数都需要 operator 关键字
    /** TODO 委托类可以实现包含所需 operator 方法的
     * [ReadOnlyProperty],
     * 或者 [ReadWriteProperty]
     * */

    // 提供委托
    // TODO provideDelegate 的参数与 getValue 一样

    class AnotherUI : Feature0101.Companion.MyUI() {
        val anotherImage by Feature0101.bindResource(Feature0101.Companion.ResourceID.image_id)
    }

    // ========== [Jump page183] End ==========


}