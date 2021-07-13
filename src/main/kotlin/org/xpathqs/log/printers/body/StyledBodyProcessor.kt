package org.xpathqs.log.printers.body

import org.xpathqs.log.abstracts.IBodyProcessor
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.StyledTextMessage
import org.xpathqs.log.style.Style

class StyledBodyProcessor(
    origin: IBodyProcessor,
    private val default: Style? = null,
    private val level1: Style? = null,
    private val level2: Style? = null,
    private val level3: Style? = null
): BodyProcessorDecorator(origin) {
    override fun processBody(msg: IMessage): String {
        if(msg.bodyMessage is StyledTextMessage) {
            if(msg.bodyMessage.level == 0) {
                if(level1 != null) {
                    (msg.bodyMessage as StyledTextMessage).defaultStyle = default?.plus(level1) ?: level1
                }
            }
            else if(msg.bodyMessage.level == 1) {
                if(level2 != null) {
                    (msg.bodyMessage as StyledTextMessage).defaultStyle = default?.plus(level2) ?: level2
                }
            }
            else if(msg.bodyMessage.level == 2) {
                if(level3 != null) {
                    (msg.bodyMessage as StyledTextMessage).defaultStyle = default?.plus(level3) ?: level3
                }
            } else {
                (msg.bodyMessage as StyledTextMessage).defaultStyle = default
            }
        }
        return origin.processBody(msg)
    }

    override fun selfProcess(msg: IMessage, body: String) = ""
}