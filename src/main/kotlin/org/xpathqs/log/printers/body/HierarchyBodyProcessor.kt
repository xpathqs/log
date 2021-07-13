package org.xpathqs.log.printers.body

import org.xpathqs.log.abstracts.IBodyProcessor
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.Message
import org.xpathqs.log.message.StyledTextMessage
import org.xpathqs.log.style.StyledString

class HierarchyBodyProcessor(
    origin: IBodyProcessor,
    private val padLength: Int = 100
) : BodyProcessorDecorator(origin) {

    override fun selfProcess(msg: IMessage, body: String): String {
        return pad(msg.bodyMessage.spaceString + body, msg.bodyMessage, padLength)
    }

    protected fun pad(str: String, msg: Message, length: Int): String {
        var result = ""
        repeat(length - (msg.toString().length + msg.level * 4)) {
            result += " "
        }
        if(msg is StyledTextMessage) {
            return str + msg.wrapString(result)
        }
        return str
    }

    protected val Message.spaceString: String
        get()  {
            var result = " "
            repeat(level) {
                result += "    "
            }
            if(this is StyledTextMessage) {
                return wrapString(result)
            }
            return result
        }
}