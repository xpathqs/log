package org.xpathqs.log.message

abstract class MessageDecorator(
    private val origin: IMessage
) : IMessage{
    abstract val selfAttributes: LogAttributes

    override fun open(newMessageThread: IMessage) {
        bodyMessage.open(newMessageThread)
    }

    override val parent: IMessage
        get() = bodyMessage.parent

    override val base: IMessage
        get() = bodyMessage.base

    override fun add(msg: IMessage) {
        bodyMessage.add(msg)
    }

    override val messages: Collection<IMessage>
        get() = bodyMessage.messages

    override val attributes: LogAttributes
        get() = (origin.attributes + selfAttributes) as LogAttributes

    override val body: String
        get() = bodyMessage.body

    override val bodyMessage: Message
        get() {
            return if(origin is Message) {
                origin
            } else {
                origin.bodyMessage
            }
        }
}