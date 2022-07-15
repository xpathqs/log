@file:Suppress("UNCHECKED_CAST")

package org.xpathqs.log.message

typealias LogAttributes = MutableMap<String, Any>

interface IMessage {
    fun open(newMessageThread: IMessage)
    fun add(msg: IMessage)

    val parent: IMessage
   // val base: IMessage

    val attributes: LogAttributes
    val body: String
    val bodyMessage: Message
    val messages: Collection<IMessage>
}

val IMessage.tag: String
    get() {
        return this.attributes["tag"] as String
    }

val IMessage.cls: String
    get() {
        return this.attributes["cls"] as String
    }

val IMessage.stackTrace: Array<StackTraceElement>
    get() {
        return this.attributes["stackTrace"] as Array<StackTraceElement>
    }

val IMessage.method: String
    get() {
        return this.attributes["method"] as String
    }