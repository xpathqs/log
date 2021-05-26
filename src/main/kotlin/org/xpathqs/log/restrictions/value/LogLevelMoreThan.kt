package org.xpathqs.log.restrictions.value

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.restrictions.IRestrictionValue

class LogLevelMoreThan(
    private val level: Int
) : IRestrictionValue {
    override fun isApplicable(msg: IMessage): Boolean {
        return  msg.bodyMessage.level > level
    }
}