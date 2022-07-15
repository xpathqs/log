package org.xpathqs.log

import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.message.BaseMessage
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.NullMessage
import java.util.Stack

/**
 [MessageProcessor] is a holder class of the loggers.
 It is used to notify all loggers about log messages
 */
open class MessageProcessor(
    protected val loggers: ArrayList<Logger> = arrayListOf(Logger())
) {
    val root = BaseMessage()

    open fun log(msg: IMessage) {
        curMessage.add(msg.bodyMessage)
        loggers.forEach {it.log(msg, resctrictionsStack)}
    }

    open fun addAttachment(value: String, type: String, data: Any) {
    }

    fun <T> start(msg: IMessage, lambda: () -> T, restrictions: Collection<ILogRestrictions> = emptyList()): T {
        start(msg, restrictions)

        if(restrictions.isNotEmpty()) {
            resctrictionsStack.push(restrictions)
        }

        val res = lambda()

        if(restrictions.isNotEmpty()) {
            resctrictionsStack.pop()
        }

        complete(msg)

        return res
    }

    private fun start(msg: IMessage, restrictions: Collection<ILogRestrictions> = emptyList()) {
        loggers.forEach {it.start(msg)}
        if(msg !is NullMessage) {
            log(msg)
        }
        curMessage.open(msg.bodyMessage)
        curMessage = msg.bodyMessage
    }

    private fun complete(msg: IMessage) {
        curMessage = msg.bodyMessage.parent
        loggers.forEach {it.complete(msg)}
    }

    protected var curMessage: IMessage = root
    protected val resctrictionsStack = Stack<Collection<ILogRestrictions>>()

    companion object {
        var consoleLog = ThreadLocal<Logger>()
    }
}