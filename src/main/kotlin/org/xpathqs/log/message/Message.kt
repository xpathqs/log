package org.xpathqs.log.message

import java.util.*
import kotlin.collections.ArrayList

abstract class Message(
) : IMessage {
    var level: Int = 0
    var wasOutOnConsole = true
    val uuid = UUID.randomUUID()

    protected var parentImpl: IMessage = NullMessage()
    protected val massagesImpl: ArrayList<IMessage> = ArrayList()

    override val bodyMessage: Message
        get() = this

    override fun open(newMessageThread: IMessage) {
        //parentImpl = newMessageThread

        if(newMessageThread is Message) {
            newMessageThread.parentImpl = this
            newMessageThread.level = level + 1
        }
    }

    override val parent: IMessage
        get() = parentImpl

    override fun add(msg: IMessage) {
        massagesImpl.add(msg)
        if(msg is Message) {
            msg.parentImpl = this
            msg.level = level
        }
    }

    override val messages: Collection<IMessage>
        get() = massagesImpl
}