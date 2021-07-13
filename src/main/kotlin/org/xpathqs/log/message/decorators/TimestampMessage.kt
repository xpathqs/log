package org.xpathqs.log.message.decorators

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.LogAttributes
import org.xpathqs.log.message.MessageDecorator

class TimestampMessage(
    origin: IMessage
) : MessageDecorator(origin) {
    private var ts: Long = System.currentTimeMillis()

    override val selfAttributes: LogAttributes
        get() = hashMapOf("timestamp" to ts)
}