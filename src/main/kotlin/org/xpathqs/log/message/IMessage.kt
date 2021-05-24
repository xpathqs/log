package org.xpathqs.log.message

typealias LogAttributes = Map<String, Any>

interface IMessage {
    fun open(newMessageThread: IMessage)
    fun add(msg: IMessage)

    val parent: IMessage
    val base: IMessage

    val attributes: LogAttributes
    val body: String
    val bodyMessage: Message
    val messages: Collection<IMessage>
}
