package org.xpathqs.log.printers.args

import org.xpathqs.log.abstracts.IArgsProcessor
import org.xpathqs.log.message.IMessage

class ClassMethodArgsProcessor(
    origin: IArgsProcessor,
    val printClass: Boolean = true,
    val printMethod: Boolean = true,
    val useSimpleName: Boolean = true
): ArgsProcessorDecorator(origin) {
    override fun process(msg: IMessage): String {
        val cls = msg.attributes["cls"] as String
        val method = msg.attributes["method"] as String

        var res = ""
        if(printClass) {
            res = if(useSimpleName) cls.substringAfterLast(".") else cls
        }
        if(printMethod) {
            res += if(cls.isNotEmpty()) ".$method" else method
        }

        return "[$res]"
    }
}