package org.xpathqs.log

import org.xpathqs.log.abstracts.IStreamLog
import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.message.BaseMessage
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.printers.NoLogPrinter
import org.xpathqs.log.restrictions.NoRestrictions

open class Logger(
    protected val streamPrinter: IStreamLog = NoLogPrinter(),
    protected val restrictions: ILogRestrictions = NoRestrictions()
) {

    val root = BaseMessage()
    protected var curMessage: IMessage = root

    protected open fun log(msg: IMessage) {
        if(restrictions.canLog(msg)) {
            curMessage.add(msg.bodyMessage)
            streamPrinter.onLog(msg)
        }
    }

    protected fun <T> start(msg: IMessage, lambda: () -> T): T {
        start(msg)
        val res = lambda()
        complete(msg)
        return res
    }

    protected fun start(msg: IMessage) {
        log(msg)
        curMessage.open(msg.bodyMessage)
        curMessage = msg.bodyMessage
    }

    protected fun complete(msg: IMessage) {
        curMessage = msg.bodyMessage.base
    }
}