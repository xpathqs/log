package org.xpathqs.log.printers.body

import org.xpathqs.log.abstracts.IBodyProcessor
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.Message

class HierarchyBodyProcessor(
    origin: IBodyProcessor
) : BodyProcessorDecorator(origin) {

    override fun selfProcess(msg: IMessage, body: String): String {
        return msg.bodyMessage.spaceString + body
    }

    protected val Message.spaceString: String
        get()  {
            var result = ""
            repeat(level) {
                result += "    "
            }
            return result
        }
}