package org.xpathqs.log.printers.args

import org.xpathqs.log.abstracts.IArgsProcessor
import org.xpathqs.log.message.IMessage

class TagArgsProcessor(): IArgsProcessor {
    override fun processArgs(msg: IMessage): String {
        val tag = msg.attributes["tag"] as? String
        return "[${tag?.toUpperCase()?.padEnd(6)}]"
    }
}