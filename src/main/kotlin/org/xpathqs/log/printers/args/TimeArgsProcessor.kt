package org.xpathqs.log.printers.args

import org.xpathqs.log.abstracts.IArgsProcessor
import org.xpathqs.log.message.IMessage
import java.text.SimpleDateFormat
import java.util.*

class TimeArgsProcessor(
    origin: IArgsProcessor,
    private val format: SimpleDateFormat = SimpleDateFormat("HH:mm:ss.SSS")
) : ArgsProcessorDecorator(origin) {

    override fun process(msg: IMessage): String {
        val time = msg.attributes["timestamp"] as Long
        return "[${format.format(Date(time))}]"
    }
}