package org.xpathqs.log.printers.body

import org.xpathqs.log.abstracts.IBodyProcessor
import org.xpathqs.log.message.IMessage

class BodyProcessorImpl(): IBodyProcessor {
    override fun processBody(msg: IMessage): String {
        return msg.body
    }
}