package org.xpathqs.log.abstracts

import org.xpathqs.log.message.IMessage

interface ILogRestrictions {
    val name: String
        get() = ""

    fun canLog(msg: IMessage): Boolean

    var isEnabled: Boolean
}