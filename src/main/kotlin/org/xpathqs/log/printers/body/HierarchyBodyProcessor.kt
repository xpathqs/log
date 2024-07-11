package org.xpathqs.log.printers.body

import org.xpathqs.log.abstracts.IBodyProcessor
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.Message
import org.xpathqs.log.message.StyledTextMessage

class HierarchyBodyProcessor(
    origin: IBodyProcessor,
    private val padLength: Int = 100
) : BodyProcessorDecorator(origin) {

    override fun selfProcess(msg: IMessage, body: String): String {
        return pad(msg.bodyMessage.spaceString + body, msg.bodyMessage, padLength)
    }

    protected fun pad(str: String, msg: Message, length: Int): String {
        var result = ""
        repeat(length - (msg.toString().length + (getLevel(msg)) * 4)) {
            result += " "
        }
        if(msg is StyledTextMessage) {
            return str + msg.wrapString(result)
        }
        return str
    }

    fun getLevel(msg: Message): Int {
        val msg = msg.bodyMessage
        return if(msg.parent.bodyMessage.wasOutOnConsole) {
            msg.level
        } else {
            var parent = msg.parent.bodyMessage as? Message
            while (parent?.wasOutOnConsole == false) {
                parent = parent?.parent?.bodyMessage
            }
            parent?.level ?: 0
        }
    }

    protected val Message.spaceString: String
        get()  {
            var result = " "
            repeat(getLevel(this)) {
                result += "    "
            }
            if(this is StyledTextMessage) {
                return wrapString(result)
            }
            return result
        }
}