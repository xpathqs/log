package org.xpathqs.log.restrictions

import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.message.IMessage

class NoRestrictions(override var isEnabled: Boolean = true) : ILogRestrictions {
    override fun canLog(msg: IMessage) = true
}