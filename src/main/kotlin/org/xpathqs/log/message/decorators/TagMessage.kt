package org.xpathqs.log.message.decorators

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.LogAttributes
import org.xpathqs.log.message.MessageDecorator

class TagMessage(
    private val tag: String,
    origin: IMessage
) : MessageDecorator(origin) {

    override val selfAttributes: LogAttributes
        get() = mapOf(
            "tag" to tag
        )
}