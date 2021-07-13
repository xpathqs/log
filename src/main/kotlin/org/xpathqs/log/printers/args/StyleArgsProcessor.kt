package org.xpathqs.log.printers.args

import org.xpathqs.log.abstracts.IArgsProcessor
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.style.Style

class StyleArgsProcessor(
    origin: IArgsProcessor,
    private val style: Style
): ArgsProcessorDecorator(origin) {
    override fun processArgs(msg: IMessage): String {
        return style.apply(super.processArgs(msg))
    }

    override fun process(msg: IMessage) = ""
}