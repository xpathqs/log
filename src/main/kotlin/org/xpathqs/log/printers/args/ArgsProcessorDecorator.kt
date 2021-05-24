package org.xpathqs.log.printers.args

import org.xpathqs.log.abstracts.IArgsProcessor
import org.xpathqs.log.message.IMessage

abstract class ArgsProcessorDecorator(
    protected val origin: IArgsProcessor
) : IArgsProcessor {
    override fun processArgs(msg: IMessage): String {
        return process(msg) + origin.processArgs(msg)
    }

    abstract fun process(msg: IMessage): String
}