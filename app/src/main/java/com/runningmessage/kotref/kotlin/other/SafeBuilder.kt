package com.runningmessage.kotref.kotlin.other

/**
 * 类型安全的构建器
 *
 * Created by Lorss on 19-1-3.
 */
class SafeBuilder {

    // 类型安全的构建器 TODO DSL (HTML|XML, Anko, Ktor)

    // 一个类型安全的构建器示例

    // 作用域控制: @DslMarker  TODO  被标记后, 如果要调用外部接收者成员, 必须用标记明确指定接收者
    @DslMarker
    annotation class HtmlTagMarker


}