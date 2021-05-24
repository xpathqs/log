package org.xpathqs.log.message

class BaseMessage : Message()
{
    override val attributes: LogAttributes
        get() = emptyMap()

    override val body: String
        get() = ""
}