package org.xpathqs.log.message

class TextMessage(
    private val msg: String
) : Message()
{
    override val attributes: LogAttributes
        get() = HashMap()

    override val body: String
        get() = msg

    override fun toString() = body
}