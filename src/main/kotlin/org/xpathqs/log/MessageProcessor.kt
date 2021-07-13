package org.xpathqs.log

import org.xpathqs.log.message.BaseMessage
import org.xpathqs.log.message.IMessage

open class MessageProcessor(
    protected val loggers: ArrayList<Logger> = arrayListOf(Logger())
) {

    val root = BaseMessage()
    protected var curMessage: IMessage = root

    protected open fun log(msg: IMessage) {
        curMessage.add(msg.bodyMessage)
        loggers.forEach {it.log(msg)}
    }

    open fun addAttachment(value: String, type: String, data: Any) {
    }

    protected fun <T> start(msg: IMessage, lambda: () -> T): T {
        start(msg)
        val res = lambda()
        complete(msg)
        return res
    }

    open fun start(msg: IMessage) {
        loggers.forEach {it.start(msg)}
        log(msg)
        curMessage.open(msg.bodyMessage)
        curMessage = msg.bodyMessage
    }

    open fun complete(msg: IMessage) {
        curMessage = msg.bodyMessage.base
        loggers.forEach {it.complete(msg)}
    }
}