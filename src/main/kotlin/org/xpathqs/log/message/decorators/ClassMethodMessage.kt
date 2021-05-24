package org.xpathqs.log.message.decorators

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.LogAttributes
import org.xpathqs.log.message.MessageDecorator

class ClassMethodMessage(
    private val cls: String,
    private val method: String,
    origin: IMessage
) : MessageDecorator(origin) {

    override val selfAttributes: LogAttributes
        get() = mapOf(
            "cls" to cls,
            "method" to method
        )
}