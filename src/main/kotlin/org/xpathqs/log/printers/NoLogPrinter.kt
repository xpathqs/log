package org.xpathqs.log.printers

import org.xpathqs.log.abstracts.IStreamLog
import org.xpathqs.log.message.IMessage

class NoLogPrinter: IStreamLog {
    override fun onLog(msg: IMessage) {}
}