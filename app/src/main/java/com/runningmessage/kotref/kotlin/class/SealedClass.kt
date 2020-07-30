package com.runningmessage.kotref.kotlin.`class`

/**
 * 密封类
 *
 * Created by Lorss on 18-12-29.
 */
class SealedClass {

    // 密封类 TODO 受限制的类继承结构
    // TODO 某种意义上是枚举类的扩展 : 枚举类型的值集合也是受限的
    // TODO 每个枚举常量只存在一个实例,  而密封类的一个子类可以有多个实例

    // TODO 所有子类必须在与密封类自身相同的文件中声明(--Kotlin 1.1之前, 更严格,必须嵌套类声明内部--)

    // TODO 一个密封类是自身抽象的
    // TODO 密封类不允许有 非 - private 构造函数, 应该是避免外部创建(匿名)对象
    // TODO 扩展密封类子类的类(间接继承者)可以放在任何位置

    // TODO 使用密封类关键好处 在于 使用 when 表达式, 可以验证所有情况
}