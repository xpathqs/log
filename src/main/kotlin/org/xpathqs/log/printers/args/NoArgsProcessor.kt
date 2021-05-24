package org.xpathqs.log.printers.args

import org.xpathqs.log.abstracts.IArgsProcessor
import org.xpathqs.log.message.IMessage

class NoArgsProcessor(): IArgsProcessor {
    override fun processArgs(msg: IMessage) = ""
}