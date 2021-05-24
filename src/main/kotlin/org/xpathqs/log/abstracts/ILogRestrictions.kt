package org.xpathqs.log.abstracts

import org.xpathqs.log.message.IMessage

interface ILogRestrictions {
    fun canLog(msg: IMessage): Boolean
}