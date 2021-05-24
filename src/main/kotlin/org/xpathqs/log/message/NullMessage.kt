package org.xpathqs.log.message

class NullMessage(
    override val body: String="",
    override val attributes: LogAttributes = emptyMap(),
    override val messages: Collection<IMessage> = emptyList()
) : IMessage {
    override fun open(newMessageThread: IMessage) {}
    override fun add(msg: IMessage) {}

    override val parent: IMessage
        get() = NullMessage()

    override val base: IMessage
        get() = NullMessage()

    override val bodyMessage: Message
        get() = TextMessage("")
}