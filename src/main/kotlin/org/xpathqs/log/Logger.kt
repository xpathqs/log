package org.xpathqs.log

import org.xpathqs.log.abstracts.ILogCallback
import org.xpathqs.log.abstracts.IStreamLog
import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.message.BaseMessage
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.printers.NoLogPrinter
import org.xpathqs.log.restrictions.NoRestrictions
import java.util.*
import kotlin.collections.ArrayList

open class Logger(
    protected val streamPrinter: IStreamLog = NoLogPrinter(),
    protected val restrictions: Collection<ILogRestrictions> = emptyList(),
    protected val notifiers: ArrayList<ILogCallback> = ArrayList()
) {
    constructor(
        streamPrinter: IStreamLog = NoLogPrinter(),
        restriction: ILogRestrictions,
        notifiers: ArrayList<ILogCallback> = ArrayList()
    ) : this(streamPrinter, listOf(restriction), notifiers)

    private fun canLog(msg: IMessage, dynamicRestrictions: Stack<Collection<ILogRestrictions>> = Stack()): Boolean {
        return restrictions.find { !it.canLog(msg) } == null && checkDynamicRestriction(msg, dynamicRestrictions)
    }

    fun checkDynamicRestriction(msg: IMessage, dynamicRestrictions: Stack<Collection<ILogRestrictions>>): Boolean {
        dynamicRestrictions.forEach { restrictions ->
            if(restrictions.none {
                    it.canLog(msg)
            }) {
                return false
            }
        }
        return true
    }

    open fun log(msg: IMessage, dynamicRestrictions: Stack<Collection<ILogRestrictions>> = Stack()) {
        val canLog = canLog(msg, dynamicRestrictions)
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
        notifiers.forEach {
            it.onComplete(
                msg,
                canLog(msg)
            )
        }
    }
}