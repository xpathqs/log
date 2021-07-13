package org.xpathqs.log.message

class BaseMessage : Message()
{
    override val attributes: LogAttributes
        get() = HashMap()

    override val body: String
        get() = ""
}