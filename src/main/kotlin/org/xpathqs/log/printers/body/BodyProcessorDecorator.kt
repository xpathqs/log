package org.xpathqs.log.printers.body

import org.xpathqs.log.abstracts.IBodyProcessor
import org.xpathqs.log.message.IMessage

abstract class BodyProcessorDecorator(
    protected val origin: IBodyProcessor
) : IBodyProcessor
{
    override fun processBody(msg: IMessage): String {
        return selfProcess(msg, origin.processBody(msg))
    }

    abstract fun selfProcess(msg: IMessage, body: String): String
}