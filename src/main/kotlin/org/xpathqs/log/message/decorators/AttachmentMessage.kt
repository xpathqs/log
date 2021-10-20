package org.xpathqs.log.message.decorators

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.LogAttributes
import org.xpathqs.log.message.MessageDecorator

class AttachmentMessage(
    val data: Array<Byte>,
    origin: IMessage
) : MessageDecorator(origin) {

    override val selfAttributes: LogAttributes
        get() = hashMapOf(
            "attachment" to data
        )
}