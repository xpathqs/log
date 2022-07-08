package org.xpathqs.log

import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.message.BaseMessage
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.NullMessage
import java.util.Stack

open class MessageProcessor(
    protected val loggers: ArrayList<Logger> = arrayListOf(Logger())
) {

    val root = BaseMessage()
    protected var curMessage: IMessage = root

    protected open fun log(msg: IMessage) {
        curMessage.add(msg.bodyMessage)
        loggers.forEach {it.log(msg, resctrictionsStack)}
    }

    open fun addAttachment(value: String, type: String, data: Any) {
    }

    val resctrictionsStack = Stack<Collection<ILogRestrictions>>()

    protected fun <T> start(msg: IMessage, lambda: () -> T, restrictions: Collection<ILogRestrictions> = emptyList()): T {
        start(msg)

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

    open fun start(msg: IMessage) {
        loggers.forEach {it.start(msg)}
        if(msg !is NullMessage) {
            log(msg)
        }
        curMessage.open(msg.bodyMessage)
        curMessage = msg.bodyMessage
    }

    open fun complete(msg: IMessage) {
        curMessage = msg.bodyMessage.base
        loggers.forEach {it.complete(msg)}
    }
}