package org.xpathqs.log.restrictions.value

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.tag
import org.xpathqs.log.restrictions.IRestrictionValue

class NoRestrictValues : IRestrictionValue {
    override fun isApplicable(msg: IMessage): Boolean {
       return true
    }
}