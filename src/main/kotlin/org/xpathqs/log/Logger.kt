package org.xpathqs.log

import org.xpathqs.log.abstracts.ILogCallback
import org.xpathqs.log.abstracts.IStreamLog
import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.message.BaseMessage
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.printers.NoLogPrinter
import org.xpathqs.log.restrictions.NoRestrictions

open class Logger(
    protected val streamPrinter: IStreamLog = NoLogPrinter(),
    protected val restrictions: ILogRestrictions = NoRestrictions(),
    protected val notifiers: ArrayList<ILogCallback> = ArrayList()
) {
    open fun log(msg: IMessage) {
        val canLog = restrictions.canLog(msg)
        notifiers.forEach { it.onLog(msg, canLog) }
        if(canLog) {
            streamPrinter.onLog(msg)
        }
    }

    open fun addAttachment(value: String, type: String, data: Any) {}

    open fun start(msg: IMessage) {
        notifiers.forEach { it.onStart(msg) }
    }

    open fun complete(msg: IMessage) {
        notifiers.forEach { it.onComplete(msg, restrictions.canLog(msg)) }
    }
}